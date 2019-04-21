package me.rabrg.ares.listener;

import me.rabrg.ares.Plugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public final class SignListener implements Listener {

	private final Plugin plugin;

	public SignListener(final Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onSignChangeEvent(final SignChangeEvent event) {
		final Player player = event.getPlayer();
		final String[] lines = event.getLines();
		
		if (player.hasPermission("rabrg.ares.sign.create")) {
			if (lines[0].equals(plugin.getSignKitPreLabel())) {
				event.setLine(0, plugin.getSignKitPostLabel());
			} else if (lines[0].equals(plugin.getSignSoupPreLabel())) {
				event.setLine(0, plugin.getSignSoupPostLabel());
			}
		}
	}
}
