package me.rabrg.clans.cmd;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.struct.Permission;

public class CmdDeinvite extends FCommand {

	public CmdDeinvite() {
		super();
		this.aliases.add("deinvite");
		this.aliases.add("deinv");

		this.requiredArgs.add("player name");
		// this.optionalArgs.put("", "");

		this.permission = Permission.DEINVITE.node;
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
			msg("<i>You might want to: %s", p.cmdBase.cmdKick.getUseageTemplate(false));
			return;
		}

		myClan.deinvite(you);

		you.msg("%s<i> revoked your invitation to <h>%s<i>.", fme.describeTo(you), myClan.describeTo(you));

		myClan.msg("%s<i> revoked %s's<i> invitation.", fme.describeTo(myClan), you.describeTo(myClan));
	}

}
