package me.rabrg.unknown.kit.command;

import me.rabrg.unknown.kit.KitManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class KitCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			final Player player = (Player) sender;
			if (args.length == 0) {
				KitManager.openKitSelection(player);
			} else {
				KitManager.giveKit(player, args[0]);
			}
		} else {
			sender.sendMessage("You must be a player to use this command."); // TODO: language
		}
		return true;
	}

}
