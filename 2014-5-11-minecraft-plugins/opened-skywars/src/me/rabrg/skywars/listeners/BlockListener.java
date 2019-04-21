package me.rabrg.skywars.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.rabrg.skywars.controllers.PlayerController;
import me.rabrg.skywars.game.GameState;
import me.rabrg.skywars.player.GamePlayer;

public class BlockListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(final BlockPlaceEvent event) {
		final Player player = event.getPlayer();
		final GamePlayer gamePlayer = PlayerController.get().get(player);

		if (gamePlayer.isPlaying() && gamePlayer.getGame().getState() == GameState.WAITING) {
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(final BlockBreakEvent event) {
		final Player player = event.getPlayer();
		final GamePlayer gamePlayer = PlayerController.get().get(player);

		if (gamePlayer.isPlaying() && gamePlayer.getGame().getState() == GameState.WAITING) {
			event.setCancelled(true);
		}
	}
}
