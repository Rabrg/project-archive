package me.rabrg.protection.listener;

import me.rabrg.protection.RabrgProtectionPlugin;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public final class PlayerEventListener implements Listener {
	private final RabrgProtectionPlugin plugin;

	public PlayerEventListener(final RabrgProtectionPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerMoveEvent(final PlayerMoveEvent event) {
		final boolean isProtected = plugin.isPlayerProtected(event.getPlayer());
		final boolean isInsideProtection = plugin.isInsideProtection(event.getPlayer().getLocation().toVector());
		if (isProtected && !isInsideProtection) {
			event.getPlayer().sendMessage(ChatColor.AQUA + "You have left a protected area.");
			plugin.removeProtectedPlayer(event.getPlayer());
			plugin.pushEntityOutsideProtectedSphere(event.getPlayer());
		} else if (!isProtected && isInsideProtection) {
			event.getPlayer().sendMessage(ChatColor.RED + "You can't reenter a protected area after you leave!");
			plugin.pushEntityOutsideProtectedSphere(event.getPlayer());
		}
	}

	@EventHandler
	public void onPlayerTeleportEvent(final PlayerTeleportEvent event) {
		if (!event.isCancelled() && plugin.isInsideProtection(event.getTo().toVector())) {
			event.getPlayer().sendMessage(ChatColor.AQUA + "You have entered a protected area.");
			plugin.addProtectedPlayer(event.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerRespawnEvent(final PlayerRespawnEvent event) {
		if (plugin.isInsideProtection(event.getRespawnLocation().toVector())) {
			event.getPlayer().sendMessage(ChatColor.AQUA + "You have entered a protected area.");
			plugin.addProtectedPlayer(event.getPlayer());
		}
	}
}
