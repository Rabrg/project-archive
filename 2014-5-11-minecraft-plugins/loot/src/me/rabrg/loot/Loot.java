package me.rabrg.loot;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Loot extends JavaPlugin implements Listener {
 
	private LootRunnable lootRunnable;

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		lootRunnable = new LootRunnable(this);
		Bukkit.getScheduler().runTaskTimer(this, lootRunnable, 0, 20);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(final PlayerDeathEvent event) {
		final Player dead = event.getEntity();
		final Player killer = dead.getKiller();
		if ((event.getDrops() != null && !event.getDrops().isEmpty()) && killer != null) {
			killer.sendMessage("§4[§aSevereLoot§4] §bYour loot from §c" + dead.getName() + " §c§l§ncan't§b be picked up for §c20 §bseconds!");
			
			final long time = System.currentTimeMillis();
			lootRunnable.pending.put(time, killer.getName() + " " + dead.getName());
			for (final ItemStack itemStack : event.getDrops()) {
				final Item item = dead.getWorld().dropItemNaturally(dead.getLocation(), itemStack);
				item.setPickupDelay(0); // XXX
				item.setMetadata("loot", new FixedMetadataValue(this, killer.getName() + " " + String.valueOf(time)));
			}
		}
		event.getDrops().clear();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPickupEvent(final PlayerPickupItemEvent event) {
		final Player player = event.getPlayer();
		final Item item = event.getItem();
		if (item.hasMetadata("loot")) {
			final String[] metadata = item.getMetadata("loot").get(0).asString().split(" ");
			final String name = metadata[0];
			final long time = Long.parseLong(metadata[1]);
			
			if ((time + 20000) <= System.currentTimeMillis()) {
				item.removeMetadata("loot", this);
			} else if (player.getName().equals(name)) {
				item.removeMetadata("loot", this);
			} else {
				event.setCancelled(true);
			}
		}
	}

	class LootRunnable extends BukkitRunnable {

		final Map<Long, String> pending;

		final Loot loot;
	
		LootRunnable(final Loot loot) {
			this.pending = new ConcurrentHashMap<Long, String>();
			this.loot = loot;
		}

		@Override
		public void run() {
			final long time = System.currentTimeMillis();
			Iterator<Entry<Long, String>> it = pending.entrySet().iterator();
			while (it.hasNext()) {
				System.out.println("loop");
				Map.Entry<Long, String> entry = (Map.Entry<Long, String>) it.next();
				if ((entry.getKey() + 20000) <= time) {
					System.out.println("time passed");
					final String killerName = entry.getValue().split(" ")[0];
					final String deadName = entry.getValue().split(" ")[1];
					Bukkit.getServer().getPlayer(killerName).sendMessage("§4[§aSevereLoot§4] §bYour loot from §c" + deadName + " §c§l§nCAN§b now be picked up by others!");
					pending.remove(System.currentTimeMillis());
					it.remove();
				}
			}
		}
		
	}
}
