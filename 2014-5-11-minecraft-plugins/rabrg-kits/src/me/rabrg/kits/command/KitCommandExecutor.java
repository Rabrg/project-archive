package me.rabrg.kits.command;

import me.rabrg.kits.Kit;
import me.rabrg.kits.RabrgKitsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class KitCommandExecutor implements CommandExecutor {

	private final RabrgKitsPlugin plugin;

	public KitCommandExecutor(final RabrgKitsPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (args.length != 1) {
			return false;
		}
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "I can't give you a kit!");
			return true;
		}
		
		if (!plugin.getRabrgProtectionPlugin().isPlayerProtected((Player) sender)) {
			sender.sendMessage(ChatColor.RED + "You can't get a kit while outside a protection area!");
			return true;
		}
		
		final Kit kit = plugin.getKit(args[0]);
		if (kit == null) {
			sender.sendMessage(ChatColor.RED + "The kit doesn't exist!");
		} else {
			kit.giveKit((Player) sender);
		}
		return true;
	}

}
