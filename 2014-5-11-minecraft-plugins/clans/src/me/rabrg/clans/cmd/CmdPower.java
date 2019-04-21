package me.rabrg.clans.cmd;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Conf;
import me.rabrg.clans.struct.Permission;

public class CmdPower extends FCommand {

	public CmdPower() {
		super();
		this.aliases.add("power");
		this.aliases.add("pow");

		// this.requiredArgs.add("clan tag");
		this.optionalArgs.put("player name", "you");

		this.permission = Permission.POWER.node;
		this.disableOnLock = false;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final CPlayer target = this.argAsBestCPlayerMatch(0, fme);
		if (target == null) {
			return;
		}

		if (target != fme && !Permission.POWER_ANY.has(sender, true)) {
			return;
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(Conf.econCostPower, "to show player power info", "for showing player power info")) {
			return;
		}

		final double powerBoost = target.getPowerBoost();
		final String boost = (powerBoost == 0.0) ? "" : (powerBoost > 0.0 ? " (bonus: " : " (penalty: ") + powerBoost + ")";
		msg("%s<a> - Power / Maxpower: <i>%d / %d %s", target.describeTo(fme, true), target.getPowerRounded(), target.getPowerMaxRounded(), boost);
	}

}
