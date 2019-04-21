package me.rabrg.mechanics.listener;

import me.rabrg.mechanics.RabrgMechanicsPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public final class WeatherEventListener implements Listener {
	private final RabrgMechanicsPlugin plugin;

	public WeatherEventListener(final RabrgMechanicsPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onThunderChangeEvent(final ThunderChangeEvent event) {
		if (plugin.getConfig().getBoolean("disable-thunder", true)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onWeatherChangeEvent(final WeatherChangeEvent event) {
		if (plugin.getConfig().getBoolean("disable-weather", true)) {
			event.setCancelled(true);
		}
	}
}
