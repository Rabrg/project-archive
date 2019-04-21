package me.rabrg.clans.zcore;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import me.rabrg.clans.zcore.persist.EM;
import me.rabrg.clans.zcore.persist.Entity;
import me.rabrg.clans.zcore.persist.EntityCollection;
import me.rabrg.clans.zcore.persist.PlayerEntityCollection;

public class MPluginSecretPlayerListener implements Listener {
	private final MPlugin p;

	public MPluginSecretPlayerListener(final MPlugin p) {
		this.p = p;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (p.handleCommand(event.getPlayer(), event.getMessage())) {
			if (p.logPlayerCommands()) {
				Bukkit.getLogger().info("[PLAYER_COMMAND] " + event.getPlayer().getName() + ": " + event.getMessage());
			}
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (p.handleCommand(event.getPlayer(), event.getMessage(), false, true)) {
			if (p.logPlayerCommands()) {
				Bukkit.getLogger().info("[PLAYER_COMMAND] " + event.getPlayer().getName() + ": " + event.getMessage());
			}
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPreLogin(final PlayerLoginEvent event) {
		for (final EntityCollection<? extends Entity> ecoll : EM.class2Entities.values()) {
			if (ecoll instanceof PlayerEntityCollection) {
				ecoll.get(event.getPlayer().getName());
			}
		}
	}
}
