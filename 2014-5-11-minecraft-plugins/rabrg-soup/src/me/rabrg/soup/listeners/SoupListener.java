package me.rabrg.soup.listeners;

import me.rabrg.soup.Soup;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class SoupListener implements Listener {

	private final Soup plugin;

	public SoupListener(final Soup plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEvent(final PlayerInteractEvent event) {
		final ItemStack item = event.getItem();
		final Action action = event.getAction();
		if (item != null && item.getType() == Material.MUSHROOM_SOUP && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
			final Player player = event.getPlayer();
			final double health = player.getHealth();
			final double max_health =player.getMaxHealth();
			if (health != max_health) {
				if (health + plugin.getSoupHeal() > max_health) {
					player.setHealth(max_health);
				} else {
					player.setHealth(health + plugin.getSoupHeal());
				}
				item.setType(Material.BOWL);
			}
		}
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onFoodLevelChangeEvent(final FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityRegainHealthEvent (final EntityRegainHealthEvent event) {
		if (event.getRegainReason() == RegainReason.SATIATED) {
			event.setCancelled(true);
		}
	}
}
