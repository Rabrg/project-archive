package me.rabrg.kitpvp.command;

import me.rabrg.kitpvp.KitPvP;
import me.rabrg.kitpvp.util.Language;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class KillsCommandExecutor implements CommandExecutor {

	private final KitPvP kitPvP;

	public KillsCommandExecutor(final KitPvP kitPvP) {
		this.kitPvP = kitPvP;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if(args.length != 1) {
			return false;
		}
		final String player = args[0];
		final int kills = kitPvP.getConfiguration().getPlayerKills(player);
		if(kills == 0) {
			sender.sendMessage(Language.KILLS_COMMAND_NONE.toString(player));
		} else if(kills == 1){
			sender.sendMessage(Language.KILLS_COMMAND_ONE.toString(player, kills));
		} else {
			sender.sendMessage(Language.KILLS_COMMAND_MULTIPLE.toString(player, kills));
		}
		return true;
	}

}
