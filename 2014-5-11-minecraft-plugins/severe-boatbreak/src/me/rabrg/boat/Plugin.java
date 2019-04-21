package me.rabrg.boat;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (((Player) event.getDamager()).isInsideVehicle()) {
				event.setCancelled(true);
				((Player) event.getDamager()).sendMessage("You can't attack entities while inside of a vehicle.");
			}
		}
	}

	@EventHandler
	public void onPlayerInteractEvent(final PlayerInteractEvent event) {
		final Block block = event.getClickedBlock();
		if (block != null && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			final Location blockLocation = block.getLocation();
			final Block block2 = block.getWorld().getBlockAt(new Location(block.getWorld(), blockLocation.getBlockX(), blockLocation.getBlockY() + 1, blockLocation.getBlockZ()));
			if ((block2 == null || !block2.getType().equals(Material.STATIONARY_WATER) && !block.getType().equals(Material.STATIONARY_WATER)) && event.getMaterial().equals(Material.BOAT)) {
				event.setCancelled(true);

				event.setUseItemInHand(Event.Result.DENY);
			}
		}
	}
}
