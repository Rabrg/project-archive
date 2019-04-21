package me.rabrg.clans.cmd;

import me.rabrg.clans.Board;
import me.rabrg.clans.Clans;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.struct.Permission;

public class CmdSafeunclaimall extends FCommand {

	public CmdSafeunclaimall() {
		this.aliases.add("safeunclaimall");
		this.aliases.add("safedeclaimall");

		// this.requiredArgs.add("");
		// this.optionalArgs.put("radius", "0");

		this.permission = Permission.MANAGE_SAFE_ZONE.node;
		this.disableOnLock = true;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;

		this.setHelpShort("Unclaim all safezone land");
	}

	@Override
	public void perform() {
		Board.unclaimAll(Clans.i.getSafeZone().getId());
		msg("<i>You unclaimed ALL safe zone land.");

		if (Conf.logLandUnclaims) {
			P.p.log(fme.getName() + " unclaimed all safe zones.");
		}
	}

}
