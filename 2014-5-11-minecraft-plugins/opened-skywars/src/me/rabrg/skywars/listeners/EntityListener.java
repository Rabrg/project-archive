package me.rabrg.skywars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.controllers.PlayerController;
import me.rabrg.skywars.game.Game;
import me.rabrg.skywars.game.GameState;
import me.rabrg.skywars.player.GamePlayer;

public class EntityListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onEntityDamage(final EntityDamageEvent event) {
		if (event.getEntityType() != EntityType.PLAYER) {
			return;
		}

		final Player player = (Player) event.getEntity();
		final GamePlayer gamePlayer = PlayerController.get().get(player);

		if (!gamePlayer.isPlaying()) {
			return;
		}

		final Game game = gamePlayer.getGame();

		if (game.getState() == GameState.WAITING) {
			event.setCancelled(true);
		} else if (event.getCause() == EntityDamageEvent.DamageCause.FALL && gamePlayer.shouldSkipFallDamage()) {
			gamePlayer.setSkipFallDamage(false);
			event.setCancelled(true);
		} else if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
			event.setCancelled(true);
			gamePlayer.getGame().onPlayerDeath(gamePlayer, null);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerDeath(final PlayerDeathEvent event) {
		final Player player = event.getEntity();
		final GamePlayer gamePlayer = PlayerController.get().get(player);

		if (!gamePlayer.isPlaying()) {
			return;
		}

		if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			Bukkit.getScheduler().runTaskLater(SkyWars.get(), new Runnable() {
				@Override
				public void run() {
					gamePlayer.getGame().onPlayerDeath(gamePlayer, event);
				}
			}, 1L);
		} else {
			gamePlayer.getGame().onPlayerDeath(gamePlayer, event);
		}
	}
}
