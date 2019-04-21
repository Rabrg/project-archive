package me.rabrg.teleport.listener;

import me.rabrg.teleport.RabrgTeleportDelayPlugin;
import me.rabrg.teleport.runnable.TeleportDelayBukkitRunnable;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public final class EntityEventListener implements Listener {
	private final RabrgTeleportDelayPlugin plugin;

	public EntityEventListener(final RabrgTeleportDelayPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDamageEvent(final EntityDamageEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			final TeleportDelayBukkitRunnable runnable = plugin.getTeleportDelay((Player) event.getEntity());
			if (runnable != null) {
				((Player) event.getEntity()).sendMessage(ChatColor.RED + "Your teleport has been cancled!");
				plugin.removeTeleportDelay((Player) event.getEntity());
			}
		}
	}
}
