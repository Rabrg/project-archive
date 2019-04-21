package me.rabrg.skywars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rabrg.skywars.controllers.IconMenuController;
import me.rabrg.skywars.controllers.KitController;
import me.rabrg.skywars.controllers.PlayerController;
import me.rabrg.skywars.game.GameState;
import me.rabrg.skywars.player.GamePlayer;
import me.rabrg.skywars.utilities.Messaging;

@CommandDescription("Allows a player to pick kits")
public class KitCommand implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final Player player = (Player) sender;
		final GamePlayer gamePlayer = PlayerController.get().get(player);

		if (!gamePlayer.isPlaying()) {
			sender.sendMessage(new Messaging.MessageFormatter().format("error.not-in-game"));
		} else if (gamePlayer.hasChosenKit()) {
			sender.sendMessage(new Messaging.MessageFormatter().format("error.already-has-kit"));
		} else if (gamePlayer.getGame().getState() != GameState.WAITING) {
			sender.sendMessage(new Messaging.MessageFormatter().format("error.can-not-pick-kit"));
		} else if (!IconMenuController.get().has(player)) {
			KitController.get().openKitMenu(gamePlayer);
		}

		return true;
	}
}