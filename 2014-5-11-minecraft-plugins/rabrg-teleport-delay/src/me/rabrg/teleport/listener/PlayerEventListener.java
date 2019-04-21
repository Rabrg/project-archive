package me.rabrg.teleport.listener;

import me.rabrg.teleport.RabrgTeleportDelayPlugin;
import me.rabrg.teleport.runnable.TeleportDelayBukkitRunnable;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public final class PlayerEventListener implements Listener {
	private final RabrgTeleportDelayPlugin plugin;

	public PlayerEventListener(final RabrgTeleportDelayPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerTeleportEvent(final PlayerTeleportEvent event) {
		if (!event.getPlayer().hasPermission("rabrg.teleport-delay-bypass") && event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
			plugin.putTeleportDelay(event.getPlayer(), event.getFrom(), event.getTo());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerMoveEvent(final PlayerMoveEvent event) {
		final TeleportDelayBukkitRunnable runnable = plugin.getTeleportDelay(event.getPlayer());
		if (runnable != null) {
			if (event.getTo().distance(runnable.getFromLocation()) > 1) {
				event.getPlayer().sendMessage(ChatColor.RED + "Your teleport has been cancled!");
				plugin.removeTeleportDelay(event.getPlayer());
			}
		}
	}
}
