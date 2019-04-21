package me.rabrg.clans.cmd;

import me.rabrg.clans.struct.Permission;

public class CmdLeave extends FCommand {

	public CmdLeave() {
		super();
		this.aliases.add("leave");

		// this.requiredArgs.add("");
		// this.optionalArgs.put("", "");

		this.permission = Permission.LEAVE.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = true;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		fme.leave(true);
	}

}
