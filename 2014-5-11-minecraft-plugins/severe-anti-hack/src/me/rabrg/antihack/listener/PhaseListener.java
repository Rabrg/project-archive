package me.rabrg.antihack.listener;

import me.rabrg.antihack.Plugin;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public final class PhaseListener implements Listener {

	private final Plugin plugin;

	public PhaseListener(final Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMoveEvent(final PlayerMoveEvent event) {
		final Location dif = event.getFrom().subtract(event.getTo());
		if (!event.getPlayer().isFlying()  && (dif.getY() == 0.14999999999999858 || dif.getY() == -0.14999999999999858 || dif.getY() == 0.15000000000000568 || dif.getY() == -0.15000000000000568)) {
			plugin.takeAction(event.getPlayer(), "phase", "don't need any noob");
		}
	}
}
