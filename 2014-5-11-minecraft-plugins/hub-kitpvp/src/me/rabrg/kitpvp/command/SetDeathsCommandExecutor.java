package me.rabrg.kitpvp.command;

import me.rabrg.kitpvp.KitPvP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class SetDeathsCommandExecutor implements CommandExecutor {

	private final KitPvP kitPvP;

	public SetDeathsCommandExecutor(final KitPvP kitPvP) {
		this.kitPvP = kitPvP;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if(args.length != 2) {
			return false;
		}
		final String player = args[0];
		final int killstreak = Integer.parseInt(args[1]);
		kitPvP.getConfiguration().setPlayerKillstreak(player, killstreak);
		return true;
	}

}
