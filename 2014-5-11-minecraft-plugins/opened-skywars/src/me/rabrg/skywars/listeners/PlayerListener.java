package me.rabrg.skywars.listeners;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.config.PluginConfig;
import me.rabrg.skywars.controllers.GameController;
import me.rabrg.skywars.controllers.PlayerController;
import me.rabrg.skywars.controllers.SchematicController;
import me.rabrg.skywars.game.Game;
import me.rabrg.skywars.game.GameState;
import me.rabrg.skywars.player.GamePlayer;
import me.rabrg.skywars.utilities.Messaging;
import me.rabrg.skywars.utilities.StringUtils;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		PlayerController.get().register(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		final GamePlayer gamePlayer = PlayerController.get().get(player);

		if (gamePlayer.isPlaying()) {
			gamePlayer.getGame().onPlayerLeave(gamePlayer);
		}

		gamePlayer.save();
		PlayerController.get().unregister(player);
	}

	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		final GamePlayer gamePlayer = PlayerController.get().get(player);

		if (gamePlayer.isPlaying()) {
			event.setRespawnLocation(PluginConfig.getLobbySpawn());

			if (PluginConfig.saveInventory()) {
				Bukkit.getScheduler().runTaskLater(SkyWars.get(), new Runnable() {
					@Override
					public void run() {
						gamePlayer.restoreState();
					}
				}, 1L);
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		final GamePlayer gamePlayer = PlayerController.get().get(player);

		if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.STONE_PLATE) {
			if (!gamePlayer.isPlaying() && player.getLocation().getWorld().equals(PluginConfig.getLobbySpawn().getWorld())) {
				if (SchematicController.get().size() == 0) {
					player.sendMessage(new Messaging.MessageFormatter().format("error.no-schematics"));
					return;
				}

				final Game game = GameController.get().findEmpty();
				game.onPlayerJoin(gamePlayer);
			}

			return;
		}

		if (gamePlayer.isPlaying() && gamePlayer.getGame().getState() != GameState.PLAYING) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		final GamePlayer gamePlayer = PlayerController.get().get(player);

		if (PluginConfig.chatHandledByOtherPlugin()) {
			event.setFormat(event.getFormat().replace("[score]", String.valueOf(gamePlayer.getScore())));

			if (gamePlayer.isPlaying()) {
				for (final Iterator<Player> iterator = event.getRecipients().iterator(); iterator.hasNext();) {
					final GamePlayer gp = PlayerController.get().get(iterator.next());

					if (!gp.isPlaying() || !gp.getGame().equals(gamePlayer.getGame())) {
						iterator.remove();
					}
				}

			} else {
				for (final Iterator<Player> iterator = event.getRecipients().iterator(); iterator.hasNext();) {
					final GamePlayer gp = PlayerController.get().get(iterator.next());

					if (gp.isPlaying()) {
						iterator.remove();
					}
				}
			}

			return;
		}

		final String message = new Messaging.MessageFormatter().setVariable("score", StringUtils.formatScore(gamePlayer.getScore())).setVariable("player", player.getDisplayName())
				.setVariable("message", Messaging.stripColor(event.getMessage())).setVariable("prefix", SkyWars.getChat().getPlayerPrefix(player)).format("chat.local");

		event.setCancelled(true);

		if (gamePlayer.isPlaying()) {
			gamePlayer.getGame().sendMessage(message);

		} else {
			for (final GamePlayer gp : PlayerController.get().getAll()) {
				if (!gp.isPlaying()) {
					gp.getBukkitPlayer().sendMessage(message);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();
		final GamePlayer gamePlayer = PlayerController.get().get(player);

		if (gamePlayer.isPlaying()) {
			final String command = event.getMessage().split(" ")[0].toLowerCase();

			if (!command.equals("/sw") && !PluginConfig.isCommandWhitelisted(command)) {
				event.setCancelled(true);
				player.sendMessage(new Messaging.MessageFormatter().withPrefix().format("error.cmd-disabled"));
			}
		}
	}
}
