package me.rabrg.league.command;

import me.rabrg.league.League;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class LeagueCommandExecutor implements CommandExecutor {

	@SuppressWarnings("unused")
	private final League league;

	public LeagueCommandExecutor(final League league) {
		this.league = league;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player) {
			final Player player = (Player) sender;
			boolean empty = true;
			for (final ItemStack item : player.getInventory().getContents()) {
				if (item != null) {
					empty = false;
				}
			}
			for (final ItemStack item : player.getInventory().getArmorContents()) {
				if (item != null) {
					empty = false;
				}
			}
			if (empty) {
				player.performCommand("warp league");
			} else {
				player.sendMessage("Your inventory must be empty to teleport to the League arena.");
			}
		} else {
			sender.sendMessage("You must be a player to use this command.");
		}
		return true;
	}

}
