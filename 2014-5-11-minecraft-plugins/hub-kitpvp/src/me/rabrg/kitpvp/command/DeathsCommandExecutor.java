package me.rabrg.kitpvp.command;

import me.rabrg.kitpvp.KitPvP;
import me.rabrg.kitpvp.util.Language;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class DeathsCommandExecutor implements CommandExecutor {

	private final KitPvP kitPvP;

	public DeathsCommandExecutor(final KitPvP kitPvP) {
		this.kitPvP = kitPvP;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if(args.length != 1) {
			return false;
		}
		final String player = args[0];
		final int deaths = kitPvP.getConfiguration().getPlayerDeaths(player);
		if(deaths == 0) {
			sender.sendMessage(Language.DEATHS_COMMAND_NONE.toString(player));
		} else if(deaths == 1){
			sender.sendMessage(Language.DEATHS_COMMAND_ONE.toString(player, deaths));
		} else {
			sender.sendMessage(Language.DEATHS_COMMAND_MULTIPLE.toString(player, deaths));
		}
		return true;
	}

}
