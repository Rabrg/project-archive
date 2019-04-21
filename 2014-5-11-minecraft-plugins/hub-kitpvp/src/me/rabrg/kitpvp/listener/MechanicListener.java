package me.rabrg.kitpvp.listener;

import me.rabrg.kitpvp.KitPvP;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public final class MechanicListener implements Listener {

	private final KitPvP kitPvP;

	public MechanicListener(final KitPvP kitPvP) {
		this.kitPvP = kitPvP;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onFoodLevelChangeEvent(final FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityRegainHealthEvent(final EntityRegainHealthEvent event) {
		if(event.getEntityType() == EntityType.PLAYER) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageEvent(final EntityDamageEvent event) {
		if(event.getEntityType() == EntityType.PLAYER) { 
			if(kitPvP.getSpawn().isInside(event.getEntity().getLocation())) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeathEvent(final PlayerDeathEvent event) {
		event.setDeathMessage(null);
		event.getDrops().clear();
		event.setDroppedExp(0);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEvent(final PlayerInteractEvent event) {
		final Action action = event.getAction();
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			if(event.getMaterial() == Material.MUSHROOM_SOUP) {
				final Player player = event.getPlayer();
				final double health = player.getHealth();
				final double maxHealth = player.getMaxHealth();
				if (health + 10 > maxHealth) {
					player.setHealth(maxHealth);
				} else {
					event.getPlayer().setHealth(health + 5);
				}
				event.getItem().setType(Material.AIR);
				event.setCancelled(true);
			}
		}
	}
}
