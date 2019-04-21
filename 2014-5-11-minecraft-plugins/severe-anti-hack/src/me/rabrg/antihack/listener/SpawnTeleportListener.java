package me.rabrg.antihack.listener;

import me.rabrg.antihack.Plugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public final class SpawnTeleportListener implements Listener {

	private final Plugin plugin;

	public SpawnTeleportListener(final Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerKick(final PlayerKickEvent event){
        if(event.getLeaveMessage().equalsIgnoreCase("Nope!")||event.getReason().equalsIgnoreCase("Nope!")){
        	event.setCancelled(true);
            plugin.takeAction(event.getPlayer(), "stp", "don't need any noob");
        }
	}
}
