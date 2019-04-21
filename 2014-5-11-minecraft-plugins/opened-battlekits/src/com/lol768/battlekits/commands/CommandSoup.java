package com.lol768.battlekits.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.lol768.battlekits.BattleKits;

public class CommandSoup implements CommandExecutor {

	public BattleKits plugin;

	public CommandSoup(final BattleKits p) {
		this.plugin = p;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("soup")) {
			if (!(sender instanceof Player)) {
				plugin.PM.warn(sender, "This is a player only command.");
				return true;
			}
			final Player p = (Player) sender;
			final ItemStack i = p.getItemInHand();

			if (sender.hasPermission("battlekits.use.soup")) {
				// TODO: @MapleFighter -- implement whole inventory
				if (i.getType() != Material.BOWL) {
					plugin.PM.warn(p, "You must have an empty bowl in your hand");
					return true;

				} else {
					i.setType(Material.MUSHROOM_SOUP);
					return true;
				}

			} else {
				plugin.PM.warn(sender, "You don't have permission for this command.");
				return true;
			}

		}
		return false;
	}
}