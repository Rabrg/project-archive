package me.rabrg.clans.cmd;

import me.rabrg.clans.Clan;
import me.rabrg.clans.Clans;
import me.rabrg.clans.Conf;
import me.rabrg.clans.struct.Permission;

public class CmdOpen extends FCommand {
	public CmdOpen() {
		super();
		this.aliases.add("open");

		// this.requiredArgs.add("");
		this.optionalArgs.put("yes/no", "flip");

		this.permission = Permission.OPEN.node;
		this.disableOnLock = false;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = true;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(Conf.econCostOpen, "to open or close the clan", "for opening or closing the clan")) {
			return;
		}

		myClan.setOpen(this.argAsBool(0, !myClan.getOpen()));

		final String open = myClan.getOpen() ? "open" : "closed";

		// Inform
		myClan.msg("%s<i> changed the clan to <h>%s<i>.", fme.describeTo(myClan, true), open);
		for (final Clan clan : Clans.i.get()) {
			if (clan == myClan) {
				continue;
			}
			clan.msg("<i>The clan %s<i> is now %s", myClan.getTag(clan), open);
		}
	}

}
