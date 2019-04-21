package me.rabrg.soup.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SoupCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "I can't give you soup!");
			return true;
		}
		final Inventory inventory = ((Player) sender).getInventory();
		int count = 0;
		for (int slot = 0; slot < inventory.getSize(); slot++) {
			if (inventory.getItem(slot) == null) {
				inventory.setItem(slot, new ItemStack(Material.MUSHROOM_SOUP));
				count++;
			}
		}
		sender.sendMessage(ChatColor.AQUA + "You've been given " + ChatColor.RED + count + ChatColor.AQUA + " soup!");
		return true;
	}

}
