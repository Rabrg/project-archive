package me.rabrg.ares;

import me.rabrg.ares.listener.SignListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

	private boolean signsEnabled;

	private String signKitPreLabel;

	private String signSoupPreLabel;

	private String signKitPostLabel;

	private String signSoupPostLabel;

	@Override
	public void onEnable() {
		loadConfiguration();
		registerListeners();
	}

	private void loadConfiguration() {
		signsEnabled = getConfig().getBoolean("sign.enabled", true);
		signKitPreLabel = getConfig().getString("sign.label.pre.kit", "[Kit]");
		signSoupPreLabel = getConfig().getString("sign.label.pre.soup", "[Soup]");
		signKitPostLabel = getConfig().getString("sign.label.post.kit", "§4[§1Kit§4]");
		signSoupPostLabel = getConfig().getString("sign.label.post.soup", "§4[§1Soup§4]");
	}

	private void registerListeners() {
		final PluginManager pluginManager = Bukkit.getPluginManager();
		if (signsEnabled) {
			pluginManager.registerEvents(new SignListener(this), this);
		}
	}
	public boolean areSignsEnabled() {
		return signsEnabled;
	}

	public String getSignKitPreLabel() {
		return signKitPreLabel;
	}

	public String getSignSoupPreLabel() {
		return signSoupPreLabel;
	}

	public String getSignKitPostLabel() {
		return signKitPostLabel;
	}

	public String getSignSoupPostLabel() {
		return signSoupPostLabel;
	}
}
