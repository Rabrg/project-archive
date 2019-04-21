package me.rabrg.clans.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import me.rabrg.clans.P;
import me.rabrg.clans.integration.SpoutFeatures;

public class ClansServerListener implements Listener {
	public P p;

	public ClansServerListener(final P p) {
		this.p = p;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginDisable(final PluginDisableEvent event) {
		final String name = event.getPlugin().getDescription().getName();
		if (name.equals("Spout")) {
			SpoutFeatures.setAvailable(false, "");
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(final PluginEnableEvent event) {
		final Plugin plug = event.getPlugin();
		final String name = plug.getDescription().getName();
		if (name.equals("Spout")) {
			SpoutFeatures.setAvailable(true, plug.getDescription().getFullName());
		}
	}
}