package me.rabrg.clans.cmd;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;
import me.rabrg.clans.P;
import me.rabrg.clans.struct.Permission;

public class CmdPowerBoost extends FCommand {
	public CmdPowerBoost() {
		super();
		this.aliases.add("powerboost");

		this.requiredArgs.add("p|f|player|clan");
		this.requiredArgs.add("name");
		this.requiredArgs.add("#");

		this.permission = Permission.POWERBOOST.node;
		this.disableOnLock = true;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final String type = this.argAsString(0).toLowerCase();
		boolean doPlayer = true;
		if (type.equals("f") || type.equals("clan")) {
			doPlayer = false;
		} else if (!type.equals("p") && !type.equals("player")) {
			msg("<b>You must specify \"p\" or \"player\" to target a player or \"f\" or \"clan\" to target a clan.");
			msg("<b>ex. /c powerboost p SomePlayer 0.5  -or-  /c powerboost f SomeClan -5");
			return;
		}

		final Double targetPower = this.argAsDouble(2);
		if (targetPower == null) {
			msg("<b>You must specify a valid numeric value for the power bonus/penalty amount.");
			return;
		}

		String target;

		if (doPlayer) {
			final CPlayer targetPlayer = this.argAsBestCPlayerMatch(1);
			if (targetPlayer == null) {
				return;
			}
			targetPlayer.setPowerBoost(targetPower);
			target = "Player \"" + targetPlayer.getName() + "\"";
		} else {
			final Clan targetClan = this.argAsClan(1);
			if (targetClan == null) {
				return;
			}
			targetClan.setPowerBoost(targetPower);
			target = "Clan \"" + targetClan.getTag() + "\"";
		}

		msg("<i>" + target + " now has a power bonus/penalty of " + targetPower + " to min and max power levels.");
		if (!senderIsConsole) {
			P.p.log(fme.getName() + " has set the power bonus/penalty for " + target + " to " + targetPower + ".");
		}
	}
}
