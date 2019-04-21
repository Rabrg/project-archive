package me.rabrg.clans.cmd;

import me.rabrg.clans.Conf;
import me.rabrg.clans.struct.Permission;

public class CmdBoom extends FCommand {
	public CmdBoom() {
		super();
		this.aliases.add("noboom");

		// this.requiredArgs.add("");
		this.optionalArgs.put("on/off", "flip");

		this.permission = Permission.NO_BOOM.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = true;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		if (!myClan.isPeaceful()) {
			fme.msg("<b>This command is only usable by clans which are specially designated as peaceful.");
			return;
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(Conf.econCostNoBoom, "to toggle explosions", "for toggling explosions")) {
			return;
		}

		myClan.setPeacefulExplosionsEnabled(this.argAsBool(0, !myClan.getPeacefulExplosionsEnabled()));

		final String enabled = myClan.noExplosionsInTerritory() ? "disabled" : "enabled";

		// Inform
		myClan.msg("%s<i> has " + enabled + " explosions in your clan's territory.", fme.describeTo(myClan));
	}
}
