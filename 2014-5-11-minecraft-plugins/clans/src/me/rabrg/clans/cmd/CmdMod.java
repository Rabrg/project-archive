package me.rabrg.clans.cmd;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Role;

public class CmdMod extends FCommand {

	public CmdMod() {
		super();
		this.aliases.add("mod");

		this.requiredArgs.add("player name");
		// this.optionalArgs.put("", "");

		this.permission = Permission.MOD.node;
		this.disableOnLock = true;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final CPlayer you = this.argAsBestCPlayerMatch(0);
		if (you == null) {
			return;
		}

		final boolean permAny = Permission.MOD_ANY.has(sender, false);
		final Clan targetClan = you.getClan();

		if (targetClan != myClan && !permAny) {
			msg("%s<b> is not a member in your clan.", you.describeTo(fme, true));
			return;
		}

		if (fme != null && fme.getRole() != Role.ADMIN && !permAny) {
			msg("<b>You are not the clan admin.");
			return;
		}

		if (you == fme && !permAny) {
			msg("<b>The target player musn't be yourself.");
			return;
		}

		if (you.getRole() == Role.ADMIN) {
			msg("<b>The target player is a clan admin. Demote them first.");
			return;
		}

		if (you.getRole() == Role.MODERATOR) {
			// Revoke
			you.setRole(Role.NORMAL);
			targetClan.msg("%s<i> is no longer moderator in your clan.", you.describeTo(targetClan, true));
			msg("<i>You have removed moderator status from %s<i>.", you.describeTo(fme, true));
		} else {
			// Give
			you.setRole(Role.MODERATOR);
			targetClan.msg("%s<i> was promoted to moderator in your clan.", you.describeTo(targetClan, true));
			msg("<i>You have promoted %s<i> to moderator.", you.describeTo(fme, true));
		}
	}

}
