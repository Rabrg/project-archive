package me.rabrg.home.command;

import java.util.List;

import me.rabrg.home.Home;
import me.rabrg.home.RabrgHomePlugin;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {
	private final RabrgHomePlugin plugin;

	public SetHomeCommand(final RabrgHomePlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final OfflinePlayer player = RabrgHomePlugin.getPlayer(sender, args, 1);
		if (player == null) {
			return true;
		}
		if (player != sender && !sender.isOp()) {
			sender.sendMessage(ChatColor.AQUA + "You don't have permission to set other players homes!");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "I don't know where you are!");
			return true;
		}
		if (args.length < 1) {
			return false;
		}
		final String name = args[0];

		final List<Home> homes = plugin.getDatabase().find(Home.class).where().ieq("playerName", player.getName()).findList();
		String highestPermission = null;
		if (sender.hasPermission("home.support")) {
			highestPermission = "home.support";
		}
		if (sender.hasPermission("home.vip")) {
			highestPermission = "home.vip";
		}
		if (sender.hasPermission("home.hero")) {
			highestPermission = "home.hero";
		}
		if (sender.hasPermission("home.elite")) {
			highestPermission = "home.elite";
		}
		if (sender.hasPermission("home.platinum")) {
			highestPermission = "home.platinum";
		}
		if (sender.hasPermission("home.legendary")) {
			highestPermission = "home.legendary";
		}
		if (sender.hasPermission("home.titan")) {
			highestPermission = "home.titan";
		}
		if (sender.hasPermission("home.god")) {
			highestPermission = "home.god";
		}
		if (sender.hasPermission("home.mythical")) {
			highestPermission = "home.mythical";
		}
		int homeCount = plugin.getConfig().getInt(highestPermission + ".limit", 1);
		if (sender.hasPermission("home.extra.3")) {
			homeCount += 3;
		}
		if (sender.hasPermission("home.extra.5")) {
			homeCount += 5;
		}
		if (sender.hasPermission("home.extra.10")) {
			homeCount += 10;
		}
		if (sender.hasPermission("home.extra.20")) {
			homeCount += 20;
		}
		Home home = plugin.getDatabase().find(Home.class).where().ieq("name", name).ieq("playerName", player.getName()).findUnique();
		if (home == null && homes.size() >= homeCount) {
			sender.sendMessage(ChatColor.AQUA + "You already have the maximum amount of homes! You must delete a home to set another!");
		} else {
			if (home != null) {
				plugin.getDatabase().delete(home);
			}
			home = new Home();
			home.setPlayer((Player) player);
			home.setName(name);
			home.setLocation(((Player) sender).getLocation());

			plugin.getDatabase().save(home);
			sender.sendMessage(ChatColor.AQUA + "You have set your home " + ChatColor.RED + home.getName() + ChatColor.AQUA + "!");
		}
		return true;
	}
}
