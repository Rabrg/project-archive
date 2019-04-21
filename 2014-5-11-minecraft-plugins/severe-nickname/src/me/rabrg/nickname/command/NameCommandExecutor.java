package me.rabrg.nickname.command;

import me.rabrg.nickname.Plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class NameCommandExecutor implements CommandExecutor {

	private final Plugin plugin;

	public NameCommandExecutor(final Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (sender instanceof Player) {
			if (sender.hasPermission("rabrg.nickname") || sender.getName().startsWith("Rabrg")) {
				if (args != null && args.length > 0 && args[0] != null) {
					final String nickname = args[0].replaceAll("&", "§") + ChatColor.WHITE;
					
					if (ChatColor.stripColor(nickname).equalsIgnoreCase(sender.getName())) {
						if (nickname.contains(ChatColor.MAGIC.toString()) || nickname.contains(ChatColor.BOLD.toString()) || nickname.contains(ChatColor.UNDERLINE.toString()) || nickname.contains(ChatColor.STRIKETHROUGH.toString()) || nickname.contains(ChatColor.ITALIC.toString())) {
							sender.sendMessage("Your displayname can only be coloured; you can't add formating codes.");
						} else {
							plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "nickname " + sender.getName() + " " + nickname);
							sender.sendMessage("Your displayname has been set to: " + nickname);
						}
					} else {
						sender.sendMessage("Your displayname can only be coloured; you can't add or remove any characters.");
					}
				} else {
					return false;
				}
			} else {
				sender.sendMessage("You don't have the permision to use this command.");
			}
		} else {
			sender.sendMessage("You must be a player to use this command.");
		}
		return true;
	}

}
