package me.rabrg.clans.listeners;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rabrg.clans.Board;
import me.rabrg.clans.CLocation;
import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.event.PowerLossEvent;
import me.rabrg.clans.struct.Relation;
import me.rabrg.clans.util.MiscUtil;

public class ClansEntityListener implements Listener {
	public P p;

	public ClansEntityListener(final P p) {
		this.p = p;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(final EntityDeathEvent event) {
		final Entity entity = event.getEntity();
		if (!(entity instanceof Player)) {
			return;
		}

		final Player player = (Player) entity;
		final CPlayer fplayer = CPlayers.i.get(player);
		final Clan clan = Board.getClanAt(new CLocation(player.getLocation()));

		final PowerLossEvent powerLossEvent = new PowerLossEvent(clan, fplayer);
		// Check for no power loss conditions
		if (clan.isWarZone()) {
			// war zones always override worldsNoPowerLoss either way, thus this
			// layout
			if (!Conf.warZonePowerLoss) {
				powerLossEvent.setMessage("<i>You didn't lose any power since you were in a war zone.");
				powerLossEvent.setCancelled(true);
			}
			if (Conf.worldsNoPowerLoss.contains(player.getWorld().getName())) {
				powerLossEvent
						.setMessage("<b>The world you are in has power loss normally disabled, but you still lost power since you were in a war zone.\n<i>Your power is now <h>%d / %d");
			}
		} else if (clan.isNone() && !Conf.wildernessPowerLoss && !Conf.worldsNoWildernessProtection.contains(player.getWorld().getName())) {
			powerLossEvent.setMessage("<i>You didn't lose any power since you were in the wilderness.");
			powerLossEvent.setCancelled(true);
		} else if (Conf.worldsNoPowerLoss.contains(player.getWorld().getName())) {
			powerLossEvent.setMessage("<i>You didn't lose any power due to the world you died in.");
			powerLossEvent.setCancelled(true);
		} else if (Conf.peacefulMembersDisablePowerLoss && fplayer.hasClan() && fplayer.getClan().isPeaceful()) {
			powerLossEvent.setMessage("<i>You didn't lose any power since you are in a peaceful clan.");
			powerLossEvent.setCancelled(true);
		} else {
			powerLossEvent.setMessage("<i>Your power is now <h>%d / %d");
		}

		// call Event
		Bukkit.getPluginManager().callEvent(powerLossEvent);

		// Call player onDeath if the event is not cancelled
		if (!powerLossEvent.isCancelled()) {
			fplayer.onDeath();
		}
		// Send the message from the powerLossEvent
		final String msg = powerLossEvent.getMessage();
		if (msg != null && !msg.isEmpty()) {
			fplayer.msg(msg, fplayer.getPowerRounded(), fplayer.getPowerMaxRounded());
		}
	}

	/**
	 * Who can I hurt? I can never hurt members or allies. I can always hurt
	 * enemies. I can hurt neutrals as long as they are outside their own
	 * territory.
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(final EntityDamageEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (event instanceof EntityDamageByEntityEvent) {
			final EntityDamageByEntityEvent sub = (EntityDamageByEntityEvent) event;
			if (!this.canDamagerHurtDamagee(sub, true)) {
				event.setCancelled(true);
			}
		} else if (Conf.safeZonePreventAllDamageToPlayers && isPlayerInSafeZone(event.getEntity())) {
			// Players can not take any damage in a Safe Zone
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityExplode(final EntityExplodeEvent event) {
		if (event.isCancelled()) {
			return;
		}

		final Location loc = event.getLocation();
		final Entity boomer = event.getEntity();
		final Clan clan = Board.getClanAt(new CLocation(loc));

		if (clan.noExplosionsInTerritory()) {
			// clan is peaceful and has explosions set to disabled
			event.setCancelled(true);
			return;
		}

		final boolean online = clan.hasPlayersOnline();

		if (boomer instanceof Creeper
				&& ((clan.isNone() && Conf.wildernessBlockCreepers && !Conf.worldsNoWildernessProtection.contains(loc.getWorld().getName()))
						|| (clan.isNormal() && (online ? Conf.territoryBlockCreepers : Conf.territoryBlockCreepersWhenOffline))
						|| (clan.isWarZone() && Conf.warZoneBlockCreepers) || clan.isSafeZone())) {
			// creeper which needs prevention
			event.setCancelled(true);
		} else if (
		// it's a bit crude just using fireball protection for Wither boss too,
		// but I'd rather not add in a whole new set of xxxBlockWitherExplosion
		// or whatever
		(boomer instanceof Fireball || boomer instanceof WitherSkull || boomer instanceof Wither)
				&& ((clan.isNone() && Conf.wildernessBlockFireballs && !Conf.worldsNoWildernessProtection.contains(loc.getWorld().getName()))
						|| (clan.isNormal() && (online ? Conf.territoryBlockFireballs : Conf.territoryBlockFireballsWhenOffline))
						|| (clan.isWarZone() && Conf.warZoneBlockFireballs) || clan.isSafeZone())) {
			// ghast fireball which needs prevention
			event.setCancelled(true);
		} else if ((boomer instanceof TNTPrimed || boomer instanceof ExplosiveMinecart)
				&& ((clan.isNone() && Conf.wildernessBlockTNT && !Conf.worldsNoWildernessProtection.contains(loc.getWorld().getName()))
						|| (clan.isNormal() && (online ? Conf.territoryBlockTNT : Conf.territoryBlockTNTWhenOffline))
						|| (clan.isWarZone() && Conf.warZoneBlockTNT) || (clan.isSafeZone() && Conf.safeZoneBlockTNT))) {
			// TNT which needs prevention
			event.setCancelled(true);
		} else if ((boomer instanceof TNTPrimed || boomer instanceof ExplosiveMinecart) && Conf.handleExploitTNTWaterlog) {
			// TNT in water/lava doesn't normally destroy any surrounding
			// blocks, which is usually desired behavior, but...
			// this change below provides workaround for waterwalling providing
			// perfect protection,
			// and makes cheap (non-obsidian) TNT cannons require minor
			// maintenance between shots
			final Block center = loc.getBlock();
			if (center.isLiquid()) {
				// a single surrounding block in all 6 directions is broken if
				// the material is weak enough
				final List<Block> targets = new ArrayList<Block>();
				targets.add(center.getRelative(0, 0, 1));
				targets.add(center.getRelative(0, 0, -1));
				targets.add(center.getRelative(0, 1, 0));
				targets.add(center.getRelative(0, -1, 0));
				targets.add(center.getRelative(1, 0, 0));
				targets.add(center.getRelative(-1, 0, 0));
				for (final Block target : targets) {
					final int id = target.getTypeId();
					// ignore air, bedrock, water, lava, obsidian, enchanting
					// table, etc.... too bad we can't get a blast resistance
					// value through Bukkit yet
					if (id != 0 && (id < 7 || id > 11) && id != 49 && id != 90 && id != 116 && id != 119 && id != 120 && id != 130) {
						target.breakNaturally();
					}
				}
			}
		}
	}

	// mainly for flaming arrows; don't want allies or people in safe zones to
	// be ignited even after damage event is cancelled
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityCombustByEntity(final EntityCombustByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}

		EntityDamageByEntityEvent sub = new EntityDamageByEntityEvent(event.getCombuster(), event.getEntity(), EntityDamageEvent.DamageCause.FIRE, 0);
		if (!this.canDamagerHurtDamagee(sub, false)) {
			event.setCancelled(true);
		}
		sub = null;
	}

	private static final Set<PotionEffectType> badPotionEffects = new LinkedHashSet<PotionEffectType>(Arrays.asList(PotionEffectType.BLINDNESS,
			PotionEffectType.CONFUSION, PotionEffectType.HARM, PotionEffectType.HUNGER, PotionEffectType.POISON, PotionEffectType.SLOW,
			PotionEffectType.SLOW_DIGGING, PotionEffectType.WEAKNESS, PotionEffectType.WITHER));

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPotionSplashEvent(final PotionSplashEvent event) {
		if (event.isCancelled()) {
			return;
		}

		// see if the potion has a harmful effect
		boolean badjuju = false;
		for (final PotionEffect effect : event.getPotion().getEffects()) {
			if (badPotionEffects.contains(effect.getType())) {
				badjuju = true;
				break;
			}
		}
		if (!badjuju) {
			return;
		}

		final Entity thrower = event.getPotion().getShooter();

		// scan through affected entities to make sure they're all valid targets
		final Iterator<LivingEntity> iter = event.getAffectedEntities().iterator();
		while (iter.hasNext()) {
			final LivingEntity target = iter.next();
			EntityDamageByEntityEvent sub = new EntityDamageByEntityEvent(thrower, target, EntityDamageEvent.DamageCause.CUSTOM, 0);
			if (!this.canDamagerHurtDamagee(sub, true)) {
				event.setIntensity(target, 0.0); // affected entity list doesn't
													// accept modification (so
													// no iter.remove()), but
													// this works
			}
			sub = null;
		}
	}

	public boolean isPlayerInSafeZone(final Entity damagee) {
		if (!(damagee instanceof Player)) {
			return false;
		}
		if (Board.getClanAt(new CLocation(damagee.getLocation())).isSafeZone()) {
			return true;
		}
		return false;
	}

	public boolean canDamagerHurtDamagee(final EntityDamageByEntityEvent sub) {
		return canDamagerHurtDamagee(sub, true);
	}

	public boolean canDamagerHurtDamagee(final EntityDamageByEntityEvent sub, final boolean notify) {
		Entity damager = sub.getDamager();
		final Entity damagee = sub.getEntity();
		final int damage = sub.getDamage();

		if (!(damagee instanceof Player)) {
			return true;
		}

		final CPlayer defender = CPlayers.i.get((Player) damagee);

		if (defender == null || defender.getPlayer() == null) {
			return true;
		}

		final Location defenderLoc = defender.getPlayer().getLocation();
		final Clan defLocClan = Board.getClanAt(new CLocation(defenderLoc));

		// for damage caused by projectiles, getDamager() returns the
		// projectile... what we need to know is the source
		if (damager instanceof Projectile) {
			damager = ((Projectile) damager).getShooter();
		}

		if (damager == damagee) {
			return true;
		}

		// Players can not take attack damage in a SafeZone, or possibly
		// peaceful territory
		if (defLocClan.noPvPInTerritory()) {
			if (damager instanceof Player) {
				if (notify) {
					final CPlayer attacker = CPlayers.i.get((Player) damager);
					attacker.msg("<i>You can't hurt other players in " + (defLocClan.isSafeZone() ? "a SafeZone." : "peaceful territory."));
				}
				return false;
			}
			return !defLocClan.noMonstersInTerritory();
		}

		if (!(damager instanceof Player)) {
			return true;
		}

		final CPlayer attacker = CPlayers.i.get((Player) damager);

		if (attacker == null || attacker.getPlayer() == null) {
			return true;
		}

		if (Conf.playersWhoBypassAllProtection.contains(attacker.getName())) {
			return true;
		}

		if (attacker.hasLoginPvpDisabled()) {
			if (notify) {
				attacker.msg("<i>You can't hurt other players for " + Conf.noPVPDamageToOthersForXSecondsAfterLogin + " seconds after logging in.");
			}
			return false;
		}

		final Clan locClan = Board.getClanAt(new CLocation(attacker));

		// so we know from above that the defender isn't in a safezone... what
		// about the attacker, sneaky dog that he might be?
		if (locClan.noPvPInTerritory()) {
			if (notify) {
				attacker.msg("<i>You can't hurt other players while you are in " + (locClan.isSafeZone() ? "a SafeZone." : "peaceful territory."));
			}
			return false;
		}

		if (locClan.isWarZone() && Conf.warZoneFriendlyFire) {
			return true;
		}

		if (Conf.worldsIgnorePvP.contains(defenderLoc.getWorld().getName())) {
			return true;
		}

		final Clan defendClan = defender.getClan();
		final Clan attackClan = attacker.getClan();

		if (attackClan.isNone() && Conf.disablePVPForClanlessPlayers) {
			if (notify) {
				attacker.msg("<i>You can't hurt other players until you join a clan.");
			}
			return false;
		} else if (defendClan.isNone()) {
			if (defLocClan == attackClan && Conf.enablePVPAgainstClanlessInAttackersLand) {
				// Allow PVP vs. Clanless in attacker's clan territory
				return true;
			} else if (Conf.disablePVPForClanlessPlayers) {
				if (notify) {
					attacker.msg("<i>You can't hurt players who are not currently in a clan.");
				}
				return false;
			}
		}

		if (defendClan.isPeaceful()) {
			if (notify) {
				attacker.msg("<i>You can't hurt players who are in a peaceful clan.");
			}
			return false;
		} else if (attackClan.isPeaceful()) {
			if (notify) {
				attacker.msg("<i>You can't hurt players while you are in a peaceful clan.");
			}
			return false;
		}

		final Relation relation = defendClan.getRelationTo(attackClan);

		// You can not hurt neutral clans
		if (Conf.disablePVPBetweenNeutralClans && relation.isNeutral()) {
			if (notify) {
				attacker.msg("<i>You can't hurt neutral clans. Declare them as an enemy.");
			}
			return false;
		}

		// Players without clan may be hurt anywhere
		if (!defender.hasClan()) {
			return true;
		}

		// You can never hurt clan members or allies
		if (relation.isMember() || relation.isAlly()) {
			if (notify) {
				attacker.msg("<i>You can't hurt %s<i>.", defender.describeTo(attacker));
			}
			return false;
		}

		final boolean ownTerritory = defender.isInOwnTerritory();

		// You can not hurt neutrals in their own territory.
		if (ownTerritory && relation.isNeutral()) {
			if (notify) {
				attacker.msg("<i>You can't hurt %s<i> in their own territory unless you declare them as an enemy.", defender.describeTo(attacker));
				defender.msg("%s<i> tried to hurt you.", attacker.describeTo(defender, true));
			}
			return false;
		}

		// Damage will be dealt. However check if the damage should be reduced.
		if (damage > 0.0 && ownTerritory && Conf.territoryShieldFactor > 0) {
			final int newDamage = (int) Math.ceil(damage * (1D - Conf.territoryShieldFactor));
			sub.setDamage(newDamage);

			// Send message
			if (notify) {
				final String perc = MessageFormat.format("{0,number,#%}", (Conf.territoryShieldFactor)); // TODO
																											// does
																											// this
																											// display
																											// correctly??
				defender.msg("<i>Enemy damage reduced by <rose>%s<i>.", perc);
			}
		}

		return true;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onCreatureSpawn(final CreatureSpawnEvent event) {
		if (event.isCancelled() || event.getLocation() == null) {
			return;
		}

		if (Conf.safeZoneNerfedCreatureTypes.contains(event.getEntityType()) && Board.getClanAt(new CLocation(event.getLocation())).noMonstersInTerritory()) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityTarget(final EntityTargetEvent event) {
		if (event.isCancelled()) {
			return;
		}

		// if there is a target
		final Entity target = event.getTarget();
		if (target == null) {
			return;
		}

		// We are interested in blocking targeting for certain mobs:
		if (!Conf.safeZoneNerfedCreatureTypes.contains(MiscUtil.creatureTypeFromEntity(event.getEntity()))) {
			return;
		}

		// in case the target is in a safe zone.
		if (Board.getClanAt(new CLocation(target.getLocation())).noMonstersInTerritory()) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPaintingBreak(final HangingBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getCause() == RemoveCause.EXPLOSION) {
			final Location loc = event.getEntity().getLocation();
			final Clan clan = Board.getClanAt(new CLocation(loc));
			if (clan.noExplosionsInTerritory()) {
				// clan is peaceful and has explosions set to disabled
				event.setCancelled(true);
				return;
			}

			final boolean online = clan.hasPlayersOnline();

			if ((clan.isNone() && !Conf.worldsNoWildernessProtection.contains(loc.getWorld().getName()) && (Conf.wildernessBlockCreepers
					|| Conf.wildernessBlockFireballs || Conf.wildernessBlockTNT))
					|| (clan.isNormal() && (online ? (Conf.territoryBlockCreepers || Conf.territoryBlockFireballs || Conf.territoryBlockTNT)
							: (Conf.territoryBlockCreepersWhenOffline || Conf.territoryBlockFireballsWhenOffline || Conf.territoryBlockTNTWhenOffline)))
					|| (clan.isWarZone() && (Conf.warZoneBlockCreepers || Conf.warZoneBlockFireballs || Conf.warZoneBlockTNT)) || clan.isSafeZone()) {
				// explosion which needs prevention
				event.setCancelled(true);
			}
		}

		if (!(event instanceof HangingBreakByEntityEvent)) {
			return;
		}

		final Entity breaker = ((HangingBreakByEntityEvent) event).getRemover();
		if (!(breaker instanceof Player)) {
			return;
		}

		if (!ClansBlockListener.playerCanBuildDestroyBlock((Player) breaker, event.getEntity().getLocation(), "remove paintings", false)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPaintingPlace(final HangingPlaceEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (!ClansBlockListener.playerCanBuildDestroyBlock(event.getPlayer(), event.getBlock().getLocation(), "place paintings", false)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityChangeBlock(final EntityChangeBlockEvent event) {
		if (event.isCancelled()) {
			return;
		}

		final Entity entity = event.getEntity();

		// for now, only interested in Enderman and Wither boss tomfoolery
		if (!(entity instanceof Enderman) && !(entity instanceof Wither)) {
			return;
		}

		final Location loc = event.getBlock().getLocation();

		if (entity instanceof Enderman) {
			if (stopEndermanBlockManipulation(loc)) {
				event.setCancelled(true);
			}
		} else if (entity instanceof Wither) {
			final Clan clan = Board.getClanAt(new CLocation(loc));
			// it's a bit crude just using fireball protection, but I'd rather
			// not add in a whole new set of xxxBlockWitherExplosion or whatever
			if ((clan.isNone() && Conf.wildernessBlockFireballs && !Conf.worldsNoWildernessProtection.contains(loc.getWorld().getName()))
					|| (clan.isNormal() && (clan.hasPlayersOnline() ? Conf.territoryBlockFireballs : Conf.territoryBlockFireballsWhenOffline))
					|| (clan.isWarZone() && Conf.warZoneBlockFireballs) || clan.isSafeZone()) {
				event.setCancelled(true);
			}
		}
	}

	private boolean stopEndermanBlockManipulation(final Location loc) {
		if (loc == null) {
			return false;
		}
		// quick check to see if all Enderman deny options are enabled; if so,
		// no need to check location
		if (Conf.wildernessDenyEndermanBlocks && Conf.territoryDenyEndermanBlocks && Conf.territoryDenyEndermanBlocksWhenOffline
				&& Conf.safeZoneDenyEndermanBlocks && Conf.warZoneDenyEndermanBlocks) {
			return true;
		}

		final CLocation fLoc = new CLocation(loc);
		final Clan claimClan = Board.getClanAt(fLoc);

		if (claimClan.isNone()) {
			return Conf.wildernessDenyEndermanBlocks;
		} else if (claimClan.isNormal()) {
			return claimClan.hasPlayersOnline() ? Conf.territoryDenyEndermanBlocks : Conf.territoryDenyEndermanBlocksWhenOffline;
		} else if (claimClan.isSafeZone()) {
			return Conf.safeZoneDenyEndermanBlocks;
		} else if (claimClan.isWarZone()) {
			return Conf.warZoneDenyEndermanBlocks;
		}

		return false;
	}
}
