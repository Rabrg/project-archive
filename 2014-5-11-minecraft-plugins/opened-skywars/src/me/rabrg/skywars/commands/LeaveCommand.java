package me.rabrg.skywars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rabrg.skywars.controllers.PlayerController;
import me.rabrg.skywars.player.GamePlayer;
import me.rabrg.skywars.utilities.Messaging;

@CommandDescription("Leaves a SkyWars game")
public class LeaveCommand implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final GamePlayer gamePlayer = PlayerController.get().get((Player) sender);

		if (!gamePlayer.isPlaying()) {
			sender.sendMessage(new Messaging.MessageFormatter().format("error.not-in-game"));
		} else {
			gamePlayer.getGame().onPlayerLeave(gamePlayer);
		}

		return true;
	}
}
