package me.rabrg.dupe;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityPortalExitEvent(final EntityPortalExitEvent  event) {
		if (event.getEntityType() != EntityType.PLAYER) {
			event.setCancelled(true);
		}
	}
}
