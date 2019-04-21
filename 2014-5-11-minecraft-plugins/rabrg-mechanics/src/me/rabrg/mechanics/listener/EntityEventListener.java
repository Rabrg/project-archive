package me.rabrg.mechanics.listener;

import me.rabrg.mechanics.RabrgMechanicsPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public final class EntityEventListener implements Listener {
	private final RabrgMechanicsPlugin plugin;

	public EntityEventListener(final RabrgMechanicsPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onFoodLevelChangeEvent(final FoodLevelChangeEvent event) {
		if (plugin.getConfig().getBoolean("disable-food-level-change", true)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityRegainHealthEvent(final EntityRegainHealthEvent event) {
		if (plugin.getConfig().getBoolean("disable-entity-regain-health-regen", true)
				&& (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED)) {
			event.setCancelled(true);
		}
	}
}
