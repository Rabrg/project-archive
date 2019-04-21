package me.rabrg.duel.listener;

import me.rabrg.duel.Plugin;
import me.rabrg.duel.player.DuelPlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class LobbyListener implements Listener {

	@SuppressWarnings("unused")
	private final Plugin plugin;

	public LobbyListener(final Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoinEvent(final PlayerJoinEvent event) {
		DuelPlayerManager.registerPlayer(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuitEvent(final PlayerQuitEvent event) {
		DuelPlayerManager.deregisterPlayer(event.getPlayer());
	}
}
