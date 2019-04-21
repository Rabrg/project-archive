package net.severepvp.loot;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class SeverePvPLoot extends JavaPlugin implements Listener {

	private Map<UUID, Player> items;

	@Override
	public void onEnable() {
		items = new ConcurrentHashMap<UUID, Player>();
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(final PlayerDeathEvent event) {
		final Player dead = event.getEntity();
		final Player killer = dead.getKiller();
		killer.sendMessage("§4[§aSevereLoot§4] §bYour loot from §c" + dead.getName() + " §c§l§ncan't§b be picked up for §c20 §bseconds!");
		if ((event.getDrops() != null && !event.getDrops().isEmpty()) && killer != null) {
			for (final ItemStack itemStack : event.getDrops()) {
				final Item item = dead.getWorld().dropItemNaturally(dead.getLocation(), itemStack);
				item.setPickupDelay(0); // XXX

				items.put(item.getUniqueId(), killer);
			}
		}

		event.getDrops().clear();
		new SeverePvPLootRunnable(this, dead, killer).runTaskLater(this, 400 /* 20 seconds */);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPickupEvent(final PlayerPickupItemEvent event) {
		final Item item = event.getItem();
		if (items.containsKey(item.getUniqueId())) {
			if (!items.get(item.getUniqueId()).equals(event.getPlayer())) {
				event.setCancelled(true);
			} else {
				items.remove(item.getUniqueId());
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(final PlayerQuitEvent event) {
		allowPickup(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemDespawn(final ItemDespawnEvent event) {
		if (items.containsKey(event.getEntity().getUniqueId())) {
			event.setCancelled(true);
		}
	}

	public void allowPickup(final Player player) {
		final Iterator<Entry<UUID, Player>> it = items.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<UUID, Player> pairs = it.next();
			if (pairs.getValue().equals(player)) {
				items.remove(pairs.getKey());
			}
			it.remove();
		}
	}
}
