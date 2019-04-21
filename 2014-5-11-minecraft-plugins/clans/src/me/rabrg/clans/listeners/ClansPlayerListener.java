package me.rabrg.clans.listeners;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.util.NumberConversions;

import me.rabrg.clans.Board;
import me.rabrg.clans.CLocation;
import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Clans;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.integration.SpoutFeatures;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Relation;
import me.rabrg.clans.struct.Role;
import me.rabrg.clans.zcore.util.TextUtil;

public class ClansPlayerListener implements Listener {
	public P p;

	public ClansPlayerListener(final P p) {
		this.p = p;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		// Make sure that all online players do have a fplayer.
		final CPlayer me = CPlayers.i.get(event.getPlayer());

		// Update the lastLoginTime for this fplayer
		me.setLastLoginTime(System.currentTimeMillis());

		// Store player's current CLocation and notify them where they are
		me.setLastStoodAt(new CLocation(event.getPlayer().getLocation()));
		if (!SpoutFeatures.updateTerritoryDisplay(me)) {
			me.sendClanHereMessage();
		}

		SpoutFeatures.updateAppearancesShortly(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(final PlayerQuitEvent event) {
		final CPlayer me = CPlayers.i.get(event.getPlayer());

		// Make sure player's power is up to date when they log off.
		me.getPower();
		// and update their last login time to point to when the logged off, for
		// auto-remove routine
		me.setLastLoginTime(System.currentTimeMillis());

		final Clan myClan = me.getClan();
		if (myClan != null) {
			myClan.memberLoggedOff();
		}
		SpoutFeatures.playerDisconnect(me);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(final PlayerMoveEvent event) {
		if (event.isCancelled()) {
			return;
		}

		// quick check to make sure player is moving between chunks; good
		// performance boost
		if (event.getFrom().getBlockX() >> 4 == event.getTo().getBlockX() >> 4 && event.getFrom().getBlockZ() >> 4 == event.getTo().getBlockZ() >> 4
				&& event.getFrom().getWorld() == event.getTo().getWorld()) {
			return;
		}

		final Player player = event.getPlayer();
		final CPlayer me = CPlayers.i.get(player);

		// Did we change coord?
		final CLocation from = me.getLastStoodAt();
		final CLocation to = new CLocation(event.getTo());

		if (from.equals(to)) {
			return;
		}

		// Yes we did change coord (:

		me.setLastStoodAt(to);

		// Did we change "host"(clan)?
		final boolean spoutClient = SpoutFeatures.availableFor(player);
		final Clan clanFrom = Board.getClanAt(from);
		final Clan clanTo = Board.getClanAt(to);
		boolean changedClan = (clanFrom != clanTo);

		if (changedClan && SpoutFeatures.updateTerritoryDisplay(me)) {
			changedClan = false;
		}

		if (me.isMapAutoUpdating()) {
			me.sendMessage(Board.getMap(me.getClan(), to, player.getLocation().getYaw()));

			if (spoutClient && Conf.spoutTerritoryOwnersShow) {
				SpoutFeatures.updateOwnerList(me);
			}
		} else {
			final Clan myClan = me.getClan();
			final String ownersTo = myClan.getOwnerListString(to);

			if (changedClan) {
				me.sendClanHereMessage();
				if (Conf.ownedAreasEnabled && Conf.ownedMessageOnBorder && (!spoutClient || !Conf.spoutTerritoryOwnersShow) && myClan == clanTo
						&& !ownersTo.isEmpty()) {
					me.sendMessage(Conf.ownedLandMessage + ownersTo);
				}
			} else if (spoutClient && Conf.spoutTerritoryOwnersShow) {
				SpoutFeatures.updateOwnerList(me);
			} else if (Conf.ownedAreasEnabled && Conf.ownedMessageInsideTerritory && clanFrom == clanTo && myClan == clanTo) {
				final String ownersFrom = myClan.getOwnerListString(from);
				if (Conf.ownedMessageByChunk || !ownersFrom.equals(ownersTo)) {
					if (!ownersTo.isEmpty()) {
						me.sendMessage(Conf.ownedLandMessage + ownersTo);
					} else if (!Conf.publicLandMessage.isEmpty()) {
						me.sendMessage(Conf.publicLandMessage);
					}
				}
			}
		}

		if (me.getAutoClaimFor() != null) {
			me.attemptClaim(me.getAutoClaimFor(), event.getTo(), true);
		} else if (me.isAutoSafeClaimEnabled()) {
			if (!Permission.MANAGE_SAFE_ZONE.has(player)) {
				me.setIsAutoSafeClaimEnabled(false);
			} else {
				if (!Board.getClanAt(to).isSafeZone()) {
					Board.setClanAt(Clans.i.getSafeZone(), to);
					me.msg("<i>This land is now a safe zone.");
				}
			}
		} else if (me.isAutoWarClaimEnabled()) {
			if (!Permission.MANAGE_WAR_ZONE.has(player)) {
				me.setIsAutoWarClaimEnabled(false);
			} else {
				if (!Board.getClanAt(to).isWarZone()) {
					Board.setClanAt(Clans.i.getWarZone(), to);
					me.msg("<i>This land is now a war zone.");
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(final PlayerInteractEvent event) {
		if (event.isCancelled()) {
			return;
		}
		// only need to check right-clicks and physical as of MC 1.4+; good
		// performance boost
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.PHYSICAL) {
			return;
		}

		final Block block = event.getClickedBlock();
		final Player player = event.getPlayer();

		if (block == null) {
			return; // clicked in air, apparently
		}

		if (!canPlayerUseBlock(player, block, false)) {
			event.setCancelled(true);
			if (Conf.handleExploitInteractionSpam) {
				final String name = player.getName();
				InteractAttemptSpam attempt = interactSpammers.get(name);
				if (attempt == null) {
					attempt = new InteractAttemptSpam();
					interactSpammers.put(name, attempt);
				}
				final int count = attempt.increment();
				if (count >= 10) {
					final CPlayer me = CPlayers.i.get(name);
					me.msg("<b>Ouch, that is starting to hurt. You should give it a rest.");
					player.damage(NumberConversions.floor((double) count / 10));
				}
			}
			return;
		}

		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return; // only interested on right-clicks for below
		}

		if (!playerCanUseItemHere(player, block.getLocation(), event.getMaterial(), false)) {
			event.setCancelled(true);
			return;
		}
	}

	// for handling people who repeatedly spam attempts to open a door (or
	// similar) in another clan's territory
	private final Map<String, InteractAttemptSpam> interactSpammers = new HashMap<String, InteractAttemptSpam>();

	private static class InteractAttemptSpam {
		private int attempts = 0;
		private long lastAttempt = System.currentTimeMillis();

		// returns the current attempt count
		public int increment() {
			final long Now = System.currentTimeMillis();
			if (Now > lastAttempt + 2000) {
				attempts = 1;
			} else {
				attempts++;
			}
			lastAttempt = Now;
			return attempts;
		}
	}

	public static boolean playerCanUseItemHere(final Player player, final Location location, final Material material, final boolean justCheck) {
		final String name = player.getName();
		if (Conf.playersWhoBypassAllProtection.contains(name)) {
			return true;
		}

		final CPlayer me = CPlayers.i.get(name);
		if (me.isAdminBypassing()) {
			return true;
		}

		final CLocation loc = new CLocation(location);
		final Clan otherClan = Board.getClanAt(loc);

		if (otherClan.hasPlayersOnline()) {
			if (!Conf.territoryDenyUseageMaterials.contains(material)) {
				return true; // Item isn't one we're preventing for online
								// clans.
			}
		} else {
			if (!Conf.territoryDenyUseageMaterialsWhenOffline.contains(material)) {
				return true; // Item isn't one we're preventing for offline
								// clans.
			}
		}

		if (otherClan.isNone()) {
			if (!Conf.wildernessDenyUseage || Conf.worldsNoWildernessProtection.contains(location.getWorld().getName())) {
				return true; // This is not clan territory. Use whatever you
								// like here.
			}

			if (!justCheck) {
				me.msg("<b>You can't use <h>%s<b> in the wilderness.", TextUtil.getMaterialName(material));
			}

			return false;
		} else if (otherClan.isSafeZone()) {
			if (!Conf.safeZoneDenyUseage || Permission.MANAGE_SAFE_ZONE.has(player)) {
				return true;
			}

			if (!justCheck) {
				me.msg("<b>You can't use <h>%s<b> in a safe zone.", TextUtil.getMaterialName(material));
			}

			return false;
		} else if (otherClan.isWarZone()) {
			if (!Conf.warZoneDenyUseage || Permission.MANAGE_WAR_ZONE.has(player)) {
				return true;
			}

			if (!justCheck) {
				me.msg("<b>You can't use <h>%s<b> in a war zone.", TextUtil.getMaterialName(material));
			}

			return false;
		}

		final Clan myClan = me.getClan();
		final Relation rel = myClan.getRelationTo(otherClan);

		// Cancel if we are not in our own territory
		if (rel.confDenyUseage()) {
			if (!justCheck) {
				me.msg("<b>You can't use <h>%s<b> in the territory of <h>%s<b>.", TextUtil.getMaterialName(material), otherClan.getTag(myClan));
			}

			return false;
		}

		// Also cancel if player doesn't have ownership rights for this claim
		if (Conf.ownedAreasEnabled && Conf.ownedAreaDenyUseage && !otherClan.playerHasOwnershipRights(me, loc)) {
			if (!justCheck) {
				me.msg("<b>You can't use <h>%s<b> in this territory, it is owned by: %s<b>.", TextUtil.getMaterialName(material),
						otherClan.getOwnerListString(loc));
			}

			return false;
		}

		return true;
	}

	public static boolean canPlayerUseBlock(final Player player, final Block block, final boolean justCheck) {
		final String name = player.getName();
		if (Conf.playersWhoBypassAllProtection.contains(name)) {
			return true;
		}

		final CPlayer me = CPlayers.i.get(name);
		if (me.isAdminBypassing()) {
			return true;
		}

		final Material material = block.getType();
		final CLocation loc = new CLocation(block);
		final Clan otherClan = Board.getClanAt(loc);

		// no door/chest/whatever protection in wilderness, war zones, or safe
		// zones
		if (!otherClan.isNormal()) {
			return true;
		}

		// We only care about some material types.
		if (otherClan.hasPlayersOnline()) {
			if (!Conf.territoryProtectedMaterials.contains(material)) {
				return true;
			}
		} else {
			if (!Conf.territoryProtectedMaterialsWhenOffline.contains(material)) {
				return true;
			}
		}

		final Clan myClan = me.getClan();
		final Relation rel = myClan.getRelationTo(otherClan);

		// You may use any block unless it is another clan's territory...
		if (rel.isNeutral() || (rel.isEnemy() && Conf.territoryEnemyProtectMaterials) || (rel.isAlly() && Conf.territoryAllyProtectMaterials)) {
			if (!justCheck) {
				me.msg("<b>You can't %s <h>%s<b> in the territory of <h>%s<b>.", (material == Material.SOIL ? "trample" : "use"),
						TextUtil.getMaterialName(material), otherClan.getTag(myClan));
			}

			return false;
		}

		// Also cancel if player doesn't have ownership rights for this claim
		if (Conf.ownedAreasEnabled && Conf.ownedAreaProtectMaterials && !otherClan.playerHasOwnershipRights(me, loc)) {
			if (!justCheck) {
				me.msg("<b>You can't use <h>%s<b> in this territory, it is owned by: %s<b>.", TextUtil.getMaterialName(material),
						otherClan.getOwnerListString(loc));
			}

			return false;
		}

		return true;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerRespawn(final PlayerRespawnEvent event) {
		final CPlayer me = CPlayers.i.get(event.getPlayer());

		me.getPower(); // update power, so they won't have gained any while dead

		final Location home = me.getClan().getHome();
		if (Conf.homesEnabled && Conf.homesTeleportToOnDeath && home != null
				&& (Conf.homesRespawnFromNoPowerLossWorlds || !Conf.worldsNoPowerLoss.contains(event.getPlayer().getWorld().getName()))) {
			event.setRespawnLocation(home);
		}
	}

	// For some reason onPlayerInteract() sometimes misses bucket events
	// depending on distance (something like 2-3 blocks away isn't detected),
	// but these separate bucket events below always fire without fail
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBucketEmpty(final PlayerBucketEmptyEvent event) {
		if (event.isCancelled()) {
			return;
		}

		final Block block = event.getBlockClicked();
		final Player player = event.getPlayer();

		if (!playerCanUseItemHere(player, block.getLocation(), event.getBucket(), false)) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBucketFill(final PlayerBucketFillEvent event) {
		if (event.isCancelled()) {
			return;
		}

		final Block block = event.getBlockClicked();
		final Player player = event.getPlayer();

		if (!playerCanUseItemHere(player, block.getLocation(), event.getBucket(), false)) {
			event.setCancelled(true);
			return;
		}
	}

	public static boolean preventCommand(String fullCmd, final Player player) {
		if ((Conf.territoryNeutralDenyCommands.isEmpty() && Conf.territoryEnemyDenyCommands.isEmpty() && Conf.permanentClanMemberDenyCommands.isEmpty())) {
			return false;
		}

		fullCmd = fullCmd.toLowerCase();

		final CPlayer me = CPlayers.i.get(player);

		String shortCmd; // command without the slash at the beginning
		if (fullCmd.startsWith("/")) {
			shortCmd = fullCmd.substring(1);
		} else {
			shortCmd = fullCmd;
			fullCmd = "/" + fullCmd;
		}

		if (me.hasClan() && !me.isAdminBypassing() && !Conf.permanentClanMemberDenyCommands.isEmpty() && me.getClan().isPermanent()
				&& isCommandInList(fullCmd, shortCmd, Conf.permanentClanMemberDenyCommands.iterator())) {
			me.msg("<b>You can't use the command \"" + fullCmd + "\" because you are in a permanent clan.");
			return true;
		}

		if (!me.isInOthersTerritory()) {
			return false;
		}

		final Relation rel = me.getRelationToLocation();
		if (rel.isAtLeast(Relation.ALLY)) {
			return false;
		}

		if (rel.isNeutral() && !Conf.territoryNeutralDenyCommands.isEmpty() && !me.isAdminBypassing()
				&& isCommandInList(fullCmd, shortCmd, Conf.territoryNeutralDenyCommands.iterator())) {
			me.msg("<b>You can't use the command \"" + fullCmd + "\" in neutral territory.");
			return true;
		}

		if (rel.isEnemy() && !Conf.territoryEnemyDenyCommands.isEmpty() && !me.isAdminBypassing()
				&& isCommandInList(fullCmd, shortCmd, Conf.territoryEnemyDenyCommands.iterator())) {
			me.msg("<b>You can't use the command \"" + fullCmd + "\" in enemy territory.");
			return true;
		}

		return false;
	}

	private static boolean isCommandInList(final String fullCmd, final String shortCmd, final Iterator<String> iter) {
		String cmdCheck;
		while (iter.hasNext()) {
			cmdCheck = iter.next();
			if (cmdCheck == null) {
				iter.remove();
				continue;
			}

			cmdCheck = cmdCheck.toLowerCase();
			if (fullCmd.startsWith(cmdCheck) || shortCmd.startsWith(cmdCheck)) {
				return true;
			}
		}
		return false;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerKick(final PlayerKickEvent event) {
		if (event.isCancelled()) {
			return;
		}

		final CPlayer badGuy = CPlayers.i.get(event.getPlayer());
		if (badGuy == null) {
			return;
		}

		SpoutFeatures.playerDisconnect(badGuy);

		// if player was banned (not just kicked), get rid of their stored info
		if (Conf.removePlayerDataWhenBanned && event.getReason().equals("Banned by admin.")) {
			if (badGuy.getRole() == Role.ADMIN) {
				badGuy.getClan().promoteNewLeader();
			}

			badGuy.leave(false);
			badGuy.detach();
		}
	}
}
