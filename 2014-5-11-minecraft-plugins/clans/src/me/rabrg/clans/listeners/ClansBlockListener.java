package me.rabrg.clans.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.rabrg.clans.Board;
import me.rabrg.clans.CLocation;
import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.integration.Worldguard;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Relation;

public class ClansBlockListener implements Listener {
	public P p;

	public ClansBlockListener(final P p) {
		this.p = p;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(final BlockPlaceEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (!event.canBuild()) {
			return;
		}

		// special case for flint&steel, which should only be prevented by
		// DenyUsage list
		if (event.getBlockPlaced().getType() == Material.FIRE) {
			return;
		}

		if (!playerCanBuildDestroyBlock(event.getPlayer(), event.getBlock().getLocation(), "build", false)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(final BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (!playerCanBuildDestroyBlock(event.getPlayer(), event.getBlock().getLocation(), "destroy", false)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockDamage(final BlockDamageEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (event.getInstaBreak() && !playerCanBuildDestroyBlock(event.getPlayer(), event.getBlock().getLocation(), "destroy", false)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPistonExtend(final BlockPistonExtendEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (!Conf.pistonProtectionThroughDenyBuild) {
			return;
		}

		final Clan pistonClan = Board.getClanAt(new CLocation(event.getBlock()));

		// target end-of-the-line empty (air) block which is being pushed into,
		// including if piston itself would extend into air
		final Block targetBlock = event.getBlock().getRelative(event.getDirection(), event.getLength() + 1);

		// if potentially pushing into air/water/lava in another territory, we
		// need to check it out
		if ((targetBlock.isEmpty() || targetBlock.isLiquid()) && !canPistonMoveBlock(pistonClan, targetBlock.getLocation())) {
			event.setCancelled(true);
			return;
		}

		/*
		 * note that I originally was testing the territory of each affected
		 * block, but since I found that pistons can only push up to 12 blocks
		 * and the width of any territory is 16 blocks, it should be safe (and
		 * much more lightweight) to test only the final target block as done
		 * above
		 */
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPistonRetract(final BlockPistonRetractEvent event) {
		// if not a sticky piston, retraction should be fine
		if (event.isCancelled() || !event.isSticky() || !Conf.pistonProtectionThroughDenyBuild) {
			return;
		}

		final Location targetLoc = event.getRetractLocation();

		// if potentially retracted block is just air/water/lava, no worries
		if (targetLoc.getBlock().isEmpty() || targetLoc.getBlock().isLiquid()) {
			return;
		}

		final Clan pistonClan = Board.getClanAt(new CLocation(event.getBlock()));

		if (!canPistonMoveBlock(pistonClan, targetLoc)) {
			event.setCancelled(true);
			return;
		}
	}

	private boolean canPistonMoveBlock(final Clan pistonClan, final Location target) {

		final Clan otherClan = Board.getClanAt(new CLocation(target));

		if (pistonClan == otherClan) {
			return true;
		}

		if (otherClan.isNone()) {
			if (!Conf.wildernessDenyBuild || Conf.worldsNoWildernessProtection.contains(target.getWorld().getName())) {
				return true;
			}

			return false;
		} else if (otherClan.isSafeZone()) {
			if (!Conf.safeZoneDenyBuild) {
				return true;
			}

			return false;
		} else if (otherClan.isWarZone()) {
			if (!Conf.warZoneDenyBuild) {
				return true;
			}

			return false;
		}

		final Relation rel = pistonClan.getRelationTo(otherClan);

		if (rel.confDenyBuild(otherClan.hasPlayersOnline())) {
			return false;
		}

		return true;
	}

	public static boolean playerCanBuildDestroyBlock(final Player player, final Location location, final String action, final boolean justCheck) {
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

		if (otherClan.isNone()) {
			if (Conf.worldGuardBuildPriority && Worldguard.playerCanBuild(player, location)) {
				return true;
			}

			if (!Conf.wildernessDenyBuild || Conf.worldsNoWildernessProtection.contains(location.getWorld().getName())) {
				return true; // This is not clan territory. Use whatever you
								// like here.
			}

			if (!justCheck) {
				me.msg("<b>You can't " + action + " in the wilderness.");
			}

			return false;
		} else if (otherClan.isSafeZone()) {
			if (Conf.worldGuardBuildPriority && Worldguard.playerCanBuild(player, location)) {
				return true;
			}

			if (!Conf.safeZoneDenyBuild || Permission.MANAGE_SAFE_ZONE.has(player)) {
				return true;
			}

			if (!justCheck) {
				me.msg("<b>You can't " + action + " in a safe zone.");
			}

			return false;
		} else if (otherClan.isWarZone()) {
			if (Conf.worldGuardBuildPriority && Worldguard.playerCanBuild(player, location)) {
				return true;
			}

			if (!Conf.warZoneDenyBuild || Permission.MANAGE_WAR_ZONE.has(player)) {
				return true;
			}

			if (!justCheck) {
				me.msg("<b>You can't " + action + " in a war zone.");
			}

			return false;
		}

		final Clan myClan = me.getClan();
		final Relation rel = myClan.getRelationTo(otherClan);
		final boolean online = otherClan.hasPlayersOnline();
		final boolean pain = !justCheck && rel.confPainBuild(online);
		final boolean deny = rel.confDenyBuild(online);

		// hurt the player for building/destroying in other territory?
		if (pain) {
			player.damage(Conf.actionDeniedPainAmount);

			if (!deny) {
				me.msg("<b>It is painful to try to " + action + " in the territory of " + otherClan.getTag(myClan));
			}
		}

		// cancel building/destroying in other territory?
		if (deny) {
			if (!justCheck) {
				me.msg("<b>You can't " + action + " in the territory of " + otherClan.getTag(myClan));
			}

			return false;
		}

		// Also cancel and/or cause pain if player doesn't have ownership rights
		// for this claim
		if (Conf.ownedAreasEnabled && (Conf.ownedAreaDenyBuild || Conf.ownedAreaPainBuild) && !otherClan.playerHasOwnershipRights(me, loc)) {
			if (!pain && Conf.ownedAreaPainBuild && !justCheck) {
				player.damage(Conf.actionDeniedPainAmount);

				if (!Conf.ownedAreaDenyBuild) {
					me.msg("<b>It is painful to try to " + action + " in this territory, it is owned by: " + otherClan.getOwnerListString(loc));
				}
			}
			if (Conf.ownedAreaDenyBuild) {
				if (!justCheck) {
					me.msg("<b>You can't " + action + " in this territory, it is owned by: " + otherClan.getOwnerListString(loc));
				}

				return false;
			}
		}

		return true;
	}
}
