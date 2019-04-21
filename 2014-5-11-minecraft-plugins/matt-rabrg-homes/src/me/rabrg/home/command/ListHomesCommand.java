package me.rabrg.home.command;

import java.util.List;

import me.rabrg.home.Home;
import me.rabrg.home.RabrgHomePlugin;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListHomesCommand implements CommandExecutor {
	private final RabrgHomePlugin plugin;

	public ListHomesCommand(final RabrgHomePlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final OfflinePlayer player = RabrgHomePlugin.getPlayer(sender, args, 0);
		if (player == null) {
			return true;
		}
		final List<Home> homes = plugin.getDatabase().find(Home.class).where().ieq("playerName", player.getName()).findList();
		if (homes.isEmpty()) {
			if (sender == player) {
				sender.sendMessage(ChatColor.AQUA + "You have no homes!");
			} else {
				sender.sendMessage(ChatColor.AQUA + "That player has no homes!");
			}
		} else {
			String result = "";
			for (final Home home : homes) {
				if (result.length() > 0) {
					result = result + ChatColor.AQUA + ", ";
				}
				result = result + ChatColor.RED + home.getName();
			}
			sender.sendMessage(ChatColor.AQUA + "All home(s): " + result);
		}
		return true;
	}
}
