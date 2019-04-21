package me.rabrg.mechanics;

import me.rabrg.mechanics.listener.BlockEventListener;
import me.rabrg.mechanics.listener.EntityEventListener;
import me.rabrg.mechanics.listener.WeatherEventListener;

import org.bukkit.plugin.java.JavaPlugin;

public final class RabrgMechanicsPlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new WeatherEventListener(this), this);
		getServer().getPluginManager().registerEvents(new BlockEventListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityEventListener(this), this);
	}
}
