package me.rabrg.clans.cmd;

import org.bukkit.Bukkit;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.event.CPlayerJoinEvent;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Role;

public class CmdAdmin extends FCommand {
	public CmdAdmin() {
		super();
		this.aliases.add("admin");

		this.requiredArgs.add("player name");
		// this.optionalArgs.put("", "");

		this.permission = Permission.ADMIN.node;
		this.disableOnLock = true;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final CPlayer fyou = this.argAsBestCPlayerMatch(0);
		if (fyou == null) {
			return;
		}

		final boolean permAny = Permission.ADMIN_ANY.has(sender, false);
		final Clan targetClan = fyou.getClan();

		if (targetClan != myClan && !permAny) {
			msg("%s<i> is not a member in your clan.", fyou.describeTo(fme, true));
			return;
		}

		if (fme != null && fme.getRole() != Role.ADMIN && !permAny) {
			msg("<b>You are not the clan admin.");
			return;
		}

		if (fyou == fme && !permAny) {
			msg("<b>The target player musn't be yourself.");
			return;
		}

		// only perform a CPlayerJoinEvent when newLeader isn't actually in the
		// clan
		if (fyou.getClan() != targetClan) {
			final CPlayerJoinEvent event = new CPlayerJoinEvent(CPlayers.i.get(me), targetClan, CPlayerJoinEvent.PlayerJoinReason.LEADER);
			Bukkit.getServer().getPluginManager().callEvent(event);
			if (event.isCancelled()) {
				return;
			}
		}

		final CPlayer admin = targetClan.getCPlayerAdmin();

		// if target player is currently admin, demote and replace him
		if (fyou == admin) {
			targetClan.promoteNewLeader();
			msg("<i>You have demoted %s<i> from the position of clan admin.", fyou.describeTo(fme, true));
			fyou.msg("<i>You have been demoted from the position of clan admin by %s<i>.", senderIsConsole ? "a server admin" : fme.describeTo(fyou, true));
			return;
		}

		// promote target player, and demote existing admin if one exists
		if (admin != null) {
			admin.setRole(Role.MODERATOR);
		}
		fyou.setRole(Role.ADMIN);
		msg("<i>You have promoted %s<i> to the position of clan admin.", fyou.describeTo(fme, true));

		// Inform all players
		for (final CPlayer fplayer : CPlayers.i.getOnline()) {
			fplayer.msg("%s<i> gave %s<i> the leadership of %s<i>.", senderIsConsole ? "A server admin" : fme.describeTo(fplayer, true),
					fyou.describeTo(fplayer), targetClan.describeTo(fplayer));
		}
	}

}
