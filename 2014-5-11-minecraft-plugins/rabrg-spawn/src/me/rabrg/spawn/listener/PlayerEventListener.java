package me.rabrg.spawn.listener;

import me.rabrg.spawn.RabrgSpawnPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public final class PlayerEventListener implements Listener {
	final RabrgSpawnPlugin plugin;

	public PlayerEventListener(final RabrgSpawnPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerRespawnEvent(final PlayerRespawnEvent event) {
		if (plugin.getConfig().getString("spawn-location.world") != null) {
			event.setRespawnLocation(plugin.getSpawnLocation());
		}
	}

	@EventHandler
	public void onPlayerLoginEvent(final PlayerLoginEvent event) {
		if (!event.getPlayer().hasPlayedBefore()) {
			event.getPlayer().sendMessage("WELCOME!");
			event.getPlayer().teleport(plugin.getSpawnLocation(), TeleportCause.PLUGIN);
		}
	}
}
