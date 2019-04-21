package me.rabrg.hardcore;

import me.rabrg.hardcore.util.Configuration;

import org.bukkit.plugin.java.JavaPlugin;

public final class Hardcore extends JavaPlugin {

	private final Configuration configuration;

	public Hardcore() {
		this.configuration = new Configuration(this);
	}

	public Configuration getConfiguration() {
		return configuration;
	}
}
