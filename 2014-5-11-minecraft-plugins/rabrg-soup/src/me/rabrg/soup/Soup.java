package me.rabrg.soup;

import me.rabrg.soup.listeners.SoupListener;

import org.bukkit.plugin.java.JavaPlugin;

public final class Soup extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new SoupListener(this), this);
	}

	public double getSoupHeal() {
		return 5.0;
	}
}
