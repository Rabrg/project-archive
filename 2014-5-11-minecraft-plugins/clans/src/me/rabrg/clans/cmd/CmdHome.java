package me.rabrg.clans.cmd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.rabrg.clans.Board;
import me.rabrg.clans.CLocation;
import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.integration.EssentialsFeatures;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Relation;
import me.rabrg.clans.struct.Role;
import me.rabrg.clans.zcore.util.SmokeUtil;

public class CmdHome extends FCommand {

	public CmdHome() {
		super();
		this.aliases.add("home");

		// this.requiredArgs.add("");
		// this.optionalArgs.put("", "");

		this.permission = Permission.HOME.node;
		this.disableOnLock = false;

		senderMustBePlayer = true;
		senderMustBeMember = true;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		// TODO: Hide this command on help also.
		if (!Conf.homesEnabled) {
			fme.msg("<b>Sorry, Clan homes are disabled on this server.");
			return;
		}

		if (!Conf.homesTeleportCommandEnabled) {
			fme.msg("<b>Sorry, the ability to teleport to Clan homes is disabled on this server.");
			return;
		}

		if (!myClan.hasHome()) {
			fme.msg("<b>Your clan does not have a home. " + (fme.getRole().value < Role.MODERATOR.value ? "<i> Ask your leader to:" : "<i>You should:"));
			fme.sendMessage(p.cmdBase.cmdSethome.getUseageTemplate());
			return;
		}

		if (!Conf.homesTeleportAllowedFromEnemyTerritory && fme.isInEnemyTerritory()) {
			fme.msg("<b>You cannot teleport to your clan home while in the territory of an enemy clan.");
			return;
		}

		if (!Conf.homesTeleportAllowedFromDifferentWorld && me.getWorld().getUID() != myClan.getHome().getWorld().getUID()) {
			fme.msg("<b>You cannot teleport to your clan home while in a different world.");
			return;
		}

		final Clan clan = Board.getClanAt(new CLocation(me.getLocation()));
		final Location loc = me.getLocation().clone();

		// if player is not in a safe zone or their own clan territory, only
		// allow teleport if no enemies are nearby
		if (Conf.homesTeleportAllowedEnemyDistance > 0 && !clan.isSafeZone()
				&& (!fme.isInOwnTerritory() || (fme.isInOwnTerritory() && !Conf.homesTeleportIgnoreEnemiesIfInOwnTerritory))) {
			final World w = loc.getWorld();
			final double x = loc.getX();
			final double y = loc.getY();
			final double z = loc.getZ();

			for (final Player p : me.getServer().getOnlinePlayers()) {
				if (p == null || !p.isOnline() || p.isDead() || p == me || p.getWorld() != w) {
					continue;
				}

				final CPlayer fp = CPlayers.i.get(p);
				if (fme.getRelationTo(fp) != Relation.ENEMY) {
					continue;
				}

				final Location l = p.getLocation();
				final double dx = Math.abs(x - l.getX());
				final double dy = Math.abs(y - l.getY());
				final double dz = Math.abs(z - l.getZ());
				final double max = Conf.homesTeleportAllowedEnemyDistance;

				// box-shaped distance check
				if (dx > max || dy > max || dz > max) {
					continue;
				}

				fme.msg("<b>You cannot teleport to your clan home while an enemy is within " + Conf.homesTeleportAllowedEnemyDistance + " blocks of you.");
				return;
			}
		}

		// if Essentials teleport handling is enabled and available, pass the
		// teleport off to it (for delay and cooldown)
		if (EssentialsFeatures.handleTeleport(me, myClan.getHome())) {
			return;
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(Conf.econCostHome, "to teleport to your clan home", "for teleporting to your clan home")) {
			return;
		}

		// Create a smoke effect
		if (Conf.homesTeleportCommandSmokeEffectEnabled) {
			final List<Location> smokeLocations = new ArrayList<Location>();
			smokeLocations.add(loc);
			smokeLocations.add(loc.add(0, 1, 0));
			smokeLocations.add(myClan.getHome());
			smokeLocations.add(myClan.getHome().clone().add(0, 1, 0));
			SmokeUtil.spawnCloudRandom(smokeLocations, Conf.homesTeleportCommandSmokeEffectThickness);
		}

		me.teleport(myClan.getHome());
	}

}
