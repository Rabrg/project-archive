package me.rabrg.skywars.listeners;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

import me.rabrg.skywars.config.PluginConfig;
import me.rabrg.skywars.controllers.ChestController;
import me.rabrg.skywars.controllers.PlayerController;
import me.rabrg.skywars.player.GamePlayer;

public class InventoryListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onInventoryOpen(final InventoryOpenEvent event) {
		if (!PluginConfig.fillChests()) {
			return;
		}

		if (!(event.getInventory().getHolder() instanceof Chest)) {
			return;
		}

		final Player player = (Player) event.getPlayer();
		final GamePlayer gamePlayer = PlayerController.get().get(player);

		if (!gamePlayer.isPlaying()) {
			return;
		}

		final Chest chest = (Chest) event.getInventory().getHolder();
		final Location location = chest.getLocation();

		if (!gamePlayer.getGame().isChest(location)) {
			return;
		}

		gamePlayer.getGame().removeChest(location);
		ChestController.get().populateChest(chest);
	}
}
