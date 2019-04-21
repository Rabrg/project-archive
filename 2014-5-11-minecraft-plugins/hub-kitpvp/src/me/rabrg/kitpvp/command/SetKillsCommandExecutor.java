package me.rabrg.kitpvp.command;

import me.rabrg.kitpvp.KitPvP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class SetKillsCommandExecutor implements CommandExecutor {

	private final KitPvP kitPvP;

	public SetKillsCommandExecutor(final KitPvP kitPvP) {
		this.kitPvP = kitPvP;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if(args.length != 2) {
			System.out.println(args.length);
			return false;
		}
		final String player = args[0];
		final int kills = Integer.parseInt(args[1]);
		kitPvP.getConfiguration().setPlayerKills(player, kills);
		return true;
	}

}
