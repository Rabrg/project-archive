package me.rabrg.clans.cmd;

import me.rabrg.clans.Board;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clans;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.struct.Permission;

public class CmdReload extends FCommand {

	public CmdReload() {
		super();
		this.aliases.add("reload");

		// this.requiredArgs.add("");
		this.optionalArgs.put("file", "all");

		this.permission = Permission.RELOAD.node;
		this.disableOnLock = false;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final long timeInitStart = System.currentTimeMillis();
		final String file = this.argAsString(0, "all").toLowerCase();

		String fileName;

		if (file.startsWith("c")) {
			Conf.load();
			fileName = "conf.json";
		} else if (file.startsWith("b")) {
			Board.load();
			fileName = "board.json";
		} else if (file.startsWith("f")) {
			Clans.i.loadFromDisc();
			fileName = "clans.json";
		} else if (file.startsWith("p")) {
			CPlayers.i.loadFromDisc();
			fileName = "players.json";
		} else if (file.startsWith("a")) {
			fileName = "all";
			Conf.load();
			CPlayers.i.loadFromDisc();
			Clans.i.loadFromDisc();
			Board.load();
		} else {
			P.p.log("RELOAD CANCELLED - SPECIFIED FILE INVALID");
			msg("<b>Invalid file specified. <i>Valid files: all, conf, board, clans, players");
			return;
		}

		final long timeReload = (System.currentTimeMillis() - timeInitStart);

		msg("<i>Reloaded <h>%s <i>from disk, took <h>%dms<i>.", fileName, timeReload);
	}

}
