package me.rabrg.watertnt;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent event) {
		if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PRIMED_TNT) {
			final Material type = event.getEntity().getWorld().getBlockAt(event.getDamager().getLocation()).getType();
			if (type == Material.WATER || type == Material.STATIONARY_WATER) {
				event.setCancelled(true);
			} else {
				System.out.println(event.getEntity().getWorld().getBlockAt(event.getDamager().getLocation()).getType());
			}
		}
	}
}
