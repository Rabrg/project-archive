package me.rabrg.teleport;

import java.util.HashMap;
import java.util.Map;

import me.rabrg.teleport.listener.EntityEventListener;
import me.rabrg.teleport.listener.PlayerEventListener;
import me.rabrg.teleport.runnable.TeleportDelayBukkitRunnable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class RabrgTeleportDelayPlugin extends JavaPlugin {

	private Map<String, TeleportDelayBukkitRunnable> teleportDelayBukkitRunnables;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		teleportDelayBukkitRunnables = new HashMap<String, TeleportDelayBukkitRunnable>();
		
		getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityEventListener(this), this);
	}

	public void putTeleportDelay(final Player player, final Location fromLocation, final Location toLocation) {
		final TeleportDelayBukkitRunnable runnable = new TeleportDelayBukkitRunnable(this, player, fromLocation, toLocation);
		runnable.runTaskTimer(this, 0L, 20L);
		teleportDelayBukkitRunnables.put(player.getName(), runnable);
	}

	public TeleportDelayBukkitRunnable getTeleportDelay(final Player player) {
		return teleportDelayBukkitRunnables.get(player.getName());
	}

	public void removeTeleportDelay(final Player player) {
		teleportDelayBukkitRunnables.remove(player.getName()).cancel();
	}
}
