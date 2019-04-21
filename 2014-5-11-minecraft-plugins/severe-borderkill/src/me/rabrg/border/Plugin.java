package me.rabrg.border;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMoveEvent(final PlayerMoveEvent event) {
		final Location location = event.getTo();
		final Player player = event.getPlayer();
		switch(location.getWorld().getName()) {
		case "world":
			if(location.getX() > 10000 || location.getX() < -10000 || location.getZ() > 10000 || location.getZ() < -10000) {
				player.teleport(location.getWorld().getSpawnLocation());
				getServer().dispatchCommand(getServer().getConsoleSender(), "kick " + player.getName());
			}
			break;
		case "world_nether":
			if(location.getX() > 1250 || location.getX() < -1250 || location.getZ() > 1250 || location.getZ() < -1250) {
				player.teleport(location.getWorld().getSpawnLocation());
				getServer().dispatchCommand(getServer().getConsoleSender(), "kick " + player.getName());
			}
			break;
		case "world_the_end":
			if(location.getX() > 2000 || location.getX() < -2000 || location.getZ() > 2000 || location.getZ() < -2000) {
				player.teleport(location.getWorld().getSpawnLocation());
				getServer().dispatchCommand(getServer().getConsoleSender(), "kick " + player.getName());
			}
			break;
		}
	}
}
