package com.lol768.battlekits.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.lol768.battlekits.BattleKits;

public class CommandRefillAll implements CommandExecutor {

	public BattleKits plugin;

	public CommandRefillAll(final BattleKits p) {
		this.plugin = p;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("fillall")) {
			if (!(sender instanceof Player)) {
				plugin.PM.warn(sender, "This is a player only command.");
				return true;
			}
			final Player p = (Player) sender;
			final ItemStack[] inv = p.getInventory().getContents();
			boolean gotBowl = false;
			if (sender.hasPermission("battlekits.use.fillall")) {
				// Get array of itemstack
				for (final ItemStack slot : inv) {
					if (slot != null && slot.getType() == Material.BOWL) { // Check
																			// for
																			// NPE
						gotBowl = true;
						slot.setType(Material.MUSHROOM_SOUP);
					}
				}

				if (!gotBowl) {
					plugin.PM.warn(p, "You have no empty bowls!");
					return true;
				}
				return true;

			} else {
				plugin.PM.warn(p, "You don't have permission for this command.");
				return true;
			}
		}

		return false;
	}
}