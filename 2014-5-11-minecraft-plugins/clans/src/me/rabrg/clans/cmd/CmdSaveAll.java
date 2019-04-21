package me.rabrg.clans.cmd;

import me.rabrg.clans.Board;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clans;
import me.rabrg.clans.Conf;
import me.rabrg.clans.struct.Permission;

public class CmdSaveAll extends FCommand {

	public CmdSaveAll() {
		super();
		this.aliases.add("saveall");
		this.aliases.add("save");

		// this.requiredArgs.add("");
		// this.optionalArgs.put("", "");

		this.permission = Permission.SAVE.node;
		this.disableOnLock = false;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		CPlayers.i.saveToDisc();
		Clans.i.saveToDisc();
		Board.save();
		Conf.save();
		msg("<i>Clans saved to disk!");
	}

}