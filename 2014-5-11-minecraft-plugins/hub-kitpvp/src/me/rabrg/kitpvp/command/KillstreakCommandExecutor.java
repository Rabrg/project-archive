package me.rabrg.kitpvp.command;

import me.rabrg.kitpvp.KitPvP;
import me.rabrg.kitpvp.util.Language;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class KillstreakCommandExecutor implements CommandExecutor {

	private final KitPvP kitPvP;

	public KillstreakCommandExecutor(final KitPvP kitPvP) {
		this.kitPvP = kitPvP;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if(args.length != 1) {
			return false;
		}
		final String player = args[0];
		final int killstreak = kitPvP.getConfiguration().getPlayerKillstreak(player);
		if(killstreak == 0) {
			sender.sendMessage(Language.KILLSTREAK_COMMAND_NONE.toString(player));
		} else if(killstreak == 1){
			sender.sendMessage(Language.KILLSTREAK_COMMAND_ONE.toString(player, killstreak));
		} else {
			sender.sendMessage(Language.KILLSTREAK_COMMAND_MULTIPLE.toString(player, killstreak));
		}
		return true;
	}

}
