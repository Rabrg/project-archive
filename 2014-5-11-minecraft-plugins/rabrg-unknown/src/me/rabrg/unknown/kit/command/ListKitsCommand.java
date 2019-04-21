package me.rabrg.unknown.kit.command;

import me.rabrg.unknown.kit.KitManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class ListKitsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			KitManager.listKits((Player) sender);
		} else {
			sender.sendMessage("You must be a player to use this command."); // TODO: language
		}
		return true;
	}

}
