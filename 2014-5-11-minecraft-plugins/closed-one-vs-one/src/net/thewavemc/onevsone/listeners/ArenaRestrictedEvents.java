package net.thewavemc.onevsone.listeners;

import net.thewavemc.onevsone.OneVsOne;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ArenaRestrictedEvents implements Listener {
	OneVsOne plugin;

	public ArenaRestrictedEvents(final OneVsOne plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlaceEvent(final BlockPlaceEvent evt) {
		if (this.plugin.getDuelManager().isOneVsOne(evt.getPlayer())) {
			evt.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreakEvent(final BlockBreakEvent evt) {
		if (this.plugin.getDuelManager().isOneVsOne(evt.getPlayer())) {
			evt.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDropItemEvent(final PlayerDropItemEvent evt) {
		if (this.plugin.getDuelManager().isOneVsOne(evt.getPlayer())) {
			if (this.plugin.getDuelManager().isInDuel(evt.getPlayer())) {
				evt.getItemDrop().remove();
			} else {
				evt.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerPickupItemEvent(final PlayerPickupItemEvent evt) {
		if (this.plugin.getDuelManager().isOneVsOne(evt.getPlayer())) {
			evt.setCancelled(true);
		}
	}

	@EventHandler
	public void onFoodLevelChangeEvent(final FoodLevelChangeEvent evt) {
		if (evt.getEntity() instanceof Player) {
			final Player p1 = (Player) evt.getEntity();
			if (this.plugin.getDuelManager().isOneVsOne(p1)) {
				evt.setCancelled(true);
			}
		}
	}
}
