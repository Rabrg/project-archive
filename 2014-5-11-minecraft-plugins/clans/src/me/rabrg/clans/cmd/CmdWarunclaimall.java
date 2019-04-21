package me.rabrg.clans.cmd;

import me.rabrg.clans.Board;
import me.rabrg.clans.Clans;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.struct.Permission;

public class CmdWarunclaimall extends FCommand {

	public CmdWarunclaimall() {
		this.aliases.add("warunclaimall");
		this.aliases.add("wardeclaimall");

		// this.requiredArgs.add("");
		// this.optionalArgs.put("", "");

		this.permission = Permission.MANAGE_WAR_ZONE.node;
		this.disableOnLock = true;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;

		this.setHelpShort("unclaim all warzone land");
	}

	@Override
	public void perform() {
		Board.unclaimAll(Clans.i.getWarZone().getId());
		msg("<i>You unclaimed ALL war zone land.");

		if (Conf.logLandUnclaims) {
			P.p.log(fme.getName() + " unclaimed all war zones.");
		}
	}

}
