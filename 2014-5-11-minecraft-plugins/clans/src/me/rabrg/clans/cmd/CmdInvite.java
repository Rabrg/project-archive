package me.rabrg.clans.cmd;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Conf;
import me.rabrg.clans.struct.Permission;

public class CmdInvite extends FCommand {
	public CmdInvite() {
		super();
		this.aliases.add("invite");
		this.aliases.add("inv");

		this.requiredArgs.add("player name");
		// this.optionalArgs.put("", "");

		this.permission = Permission.INVITE.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = true;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final CPlayer you = this.argAsBestCPlayerMatch(0);
		if (you == null) {
			return;
		}

		if (you.getClan() == myClan) {
			msg("%s<i> is already a member of %s", you.getName(), myClan.getTag());
			msg("<i>You might want to: " + p.cmdBase.cmdKick.getUseageTemplate(false));
			return;
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(Conf.econCostInvite, "to invite someone", "for inviting someone")) {
			return;
		}

		myClan.invite(you);

		you.msg("%s<i> invited you to %s", fme.describeTo(you, true), myClan.describeTo(you));
		myClan.msg("%s<i> invited %s<i> to your clan.", fme.describeTo(myClan, true), you.describeTo(myClan));
	}

}
