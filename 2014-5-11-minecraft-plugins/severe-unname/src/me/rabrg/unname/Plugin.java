package me.rabrg.unname;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			ItemMeta itemMeta = player.getItemInHand().getItemMeta();
			itemMeta.setDisplayName(null);
			itemMeta.setLore(null);
			player.getItemInHand().setItemMeta(itemMeta);
			player.sendMessage("You have removed the name from the item in your hand.");
		} else {
			sender.sendMessage("You must be a player to use this command.");
		}
		return true;
	}
}
