package me.rabrg.clans.cmd;

import java.util.ArrayList;

import me.rabrg.clans.P;
import me.rabrg.clans.zcore.CommandVisibility;
import me.rabrg.clans.zcore.MCommand;

public class CmdAutoHelp extends MCommand<P> {
	public CmdAutoHelp() {
		super(P.p);
		this.aliases.add("?");
		this.aliases.add("h");
		this.aliases.add("help");

		this.setHelpShort("");

		this.optionalArgs.put("page", "1");
	}

	@Override
	public void perform() {
		if (this.commandChain.size() == 0) {
			return;
		}
		final MCommand<?> pcmd = this.commandChain.get(this.commandChain.size() - 1);

		final ArrayList<String> lines = new ArrayList<String>();

		lines.addAll(pcmd.helpLong);

		for (final MCommand<?> scmd : pcmd.subCommands) {
			if (scmd.visibility == CommandVisibility.VISIBLE || (scmd.visibility == CommandVisibility.SECRET && scmd.validSenderPermissions(sender, false))) {
				lines.add(scmd.getUseageTemplate(this.commandChain, true));
			}
		}

		sendMessage(p.txt.getPage(lines, this.argAsInt(0, 1), "Help for command \"" + pcmd.aliases.get(0) + "\""));
	}
}
