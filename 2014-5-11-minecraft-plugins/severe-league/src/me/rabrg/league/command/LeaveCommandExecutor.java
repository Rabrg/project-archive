package me.rabrg.league.command;

import me.rabrg.league.League;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class LeaveCommandExecutor implements CommandExecutor {

	@SuppressWarnings("unused")
	private final League league;

	public LeaveCommandExecutor(final League league) {
		this.league = league;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player) {
			final Player player = (Player) sender;
			player.getInventory().setArmorContents(null);
			player.getInventory().clear();
			player.performCommand("warp spawn");
		} else {
			sender.sendMessage("You must be a player to use this command.");
		}
		return true;
	}

}
