package me.rabrg.skywars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rabrg.skywars.config.PluginConfig;
import me.rabrg.skywars.controllers.PlayerController;
import me.rabrg.skywars.game.GameState;
import me.rabrg.skywars.player.GamePlayer;
import me.rabrg.skywars.utilities.Messaging;

@CommandDescription("Starts a SkyWars game")
@CommandPermissions("skywars.command.start")
public class StartCommand implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final GamePlayer gamePlayer = PlayerController.get().get((Player) sender);

		if (!gamePlayer.isPlaying()) {
			sender.sendMessage(new Messaging.MessageFormatter().format("error.not-in-game"));
		} else if (gamePlayer.getGame().getState() != GameState.WAITING) {
			sender.sendMessage(new Messaging.MessageFormatter().format("error.already-started"));
		} else if (gamePlayer.getGame().getPlayerCount() < 2) {
			sender.sendMessage(new Messaging.MessageFormatter().format("error.not-enough-players"));
		} else if (PluginConfig.buildSchematic() && !gamePlayer.getGame().isBuilt()) {
			sender.sendMessage(new Messaging.MessageFormatter().format("error.arena-under-construction"));
		} else {
			gamePlayer.getGame().onGameStart();
		}

		return true;
	}
}
