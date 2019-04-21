package me.rabrg.protection.listener;

import me.rabrg.protection.RabrgProtectionPlugin;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public final class EntityEventListener implements Listener {
	private final RabrgProtectionPlugin plugin;

	public EntityEventListener(final RabrgProtectionPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageEvent(final EntityDamageEvent event) {
		if (event.getEntityType() == EntityType.PLAYER && plugin.isPlayerProtected((Player) event.getEntity())) {
			event.setCancelled(true);
		}
	}
}
