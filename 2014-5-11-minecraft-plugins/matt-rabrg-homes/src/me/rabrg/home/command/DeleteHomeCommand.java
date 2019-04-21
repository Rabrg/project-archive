package me.rabrg.home.command;

import me.rabrg.home.Home;
import me.rabrg.home.RabrgHomePlugin;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteHomeCommand implements CommandExecutor {
	private final RabrgHomePlugin plugin;

	public DeleteHomeCommand(final RabrgHomePlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final OfflinePlayer player = RabrgHomePlugin.getPlayer(sender, args, 1);
		if (player == null) {
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.AQUA + "I don't know who you are!");
			return true;
		}
		if (args.length < 1) {
			return false;
		}
		final String name = args[0];

		final Home home = plugin.getDatabase().find(Home.class).where().ieq("name", name).ieq("playerName", player.getName()).findUnique();
		if (home != null) {
			plugin.getDatabase().delete(home);
			sender.sendMessage(ChatColor.AQUA + "You have deleted your home " + ChatColor.RED + home.getName() + ChatColor.AQUA + "!");
		} else {
			sender.sendMessage(ChatColor.AQUA + "That home doesn't exist!");
		}
		return true;
	}
}
