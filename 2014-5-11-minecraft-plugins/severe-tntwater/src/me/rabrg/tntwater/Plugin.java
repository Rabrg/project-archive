package me.rabrg.tntwater;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityExplodeEvent(final EntityExplodeEvent event) {
		if (event.getEntityType() == EntityType.PRIMED_TNT) {
			final Location location = event.getLocation();
			for (int x = location.getBlockX() - 3; x < location.getBlockX() + 3; x++) {
				for (int y = location.getBlockY() - 3; y < location.getBlockY() + 3; y++) {
					for (int z = location.getBlockZ() - 3; z < location.getBlockZ() + 3; z++) {
						final Block block = location.getWorld().getBlockAt(x, y, z);
						if (block.getType() == Material.CHEST) {
							block.breakNaturally();
						}
					}
				}
			}
		}
	}
}
