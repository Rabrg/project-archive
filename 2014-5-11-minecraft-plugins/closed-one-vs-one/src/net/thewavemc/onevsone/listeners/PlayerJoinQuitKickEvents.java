package net.thewavemc.onevsone.listeners;

import net.thewavemc.onevsone.OneVsOne;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitKickEvents implements Listener {
	OneVsOne plugin;

	public PlayerJoinQuitKickEvents(final OneVsOne plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoinEvent(final PlayerJoinEvent evt) {
		if (this.plugin.getPlayerRestoreManager().needsRestore(evt.getPlayer())) {
			try {
				this.plugin.getPlayerRestoreManager().restorePlayer(evt.getPlayer());
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	@EventHandler
	public void onPlayerQuitEvent(final PlayerQuitEvent evt) {
		this.plugin.getDuelManager().leave(evt.getPlayer(), false);
	}

	@EventHandler
	public void onPlayerKickEvent(final PlayerKickEvent evt) {
		this.plugin.getDuelManager().leave(evt.getPlayer(), false);
	}
}
