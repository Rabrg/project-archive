package me.rabrg.leaderboard.command;

import me.rabrg.leaderboard.Plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class DeathsCommandExecutor implements CommandExecutor {

	private final Plugin plugin;

	public DeathsCommandExecutor(final Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args){
		if (args == null || args.length == 0) {
			sender.sendMessage(ChatColor.AQUA + "You have " + ChatColor.RED + plugin.getDeaths(sender.getName()) + ChatColor.AQUA + " deaths.");
		} else {
			sender.sendMessage(ChatColor.RED + args[0] + ChatColor.AQUA + " has " + ChatColor.RED + plugin.getDeaths(args[0]) + ChatColor.AQUA + " deaths.");
		}
		return true;
	}

}
