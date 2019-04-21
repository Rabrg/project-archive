package me.rabrg.mobspawner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R1.block.CraftCreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityExplodeEvent(final EntityExplodeEvent event) {
		if (event.getEntityType() == EntityType.PRIMED_TNT || event.getEntityType() == EntityType.CREEPER) {
			for (final Block block : event.blockList()) {
				if (block.getType() == Material.MOB_SPAWNER) {
					for(final Entity entity: event.getEntity().getNearbyEntities(50, 50, 50)) {
						if (entity instanceof Player) {
							if (((Player) entity).hasPermission("silkspawners.silkdrop.*")) {
								block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.MOB_SPAWNER, 1, (short) ((CraftCreatureSpawner) block.getState()).getSpawnedType().getTypeId()));
							}
						}
					}
				}
			}
		}
	}

}
