package me.rabrg.soup;

import me.rabrg.soup.command.SoupCommandExecutor;
import me.rabrg.soup.listener.PlayerListener;

import org.bukkit.plugin.java.JavaPlugin;

public final class RabrgSoupPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getCommand("soup").setExecutor(new SoupCommandExecutor());
	}

	public double getSoupHeal() {
		return getConfig().getDouble("soup-heal", 10);
	}
}
