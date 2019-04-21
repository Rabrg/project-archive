package me.rabrg.fence;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityExplodeEvent(final EntityExplodeEvent event) {
		final Entity entity = event.getEntity();
		if(event.getEntityType() != null) {
			if(event.getEntityType() == EntityType.PRIMED_TNT) {
				final Block block = entity.getWorld().getBlockAt(entity.getLocation());
				entity.teleport(block.getLocation());
			}
		}
	}
}
