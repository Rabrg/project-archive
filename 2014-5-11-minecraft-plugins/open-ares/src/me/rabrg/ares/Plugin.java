package me.rabrg.ares;

import me.rabrg.ares.kit.KitManager;
import me.rabrg.ares.player.AresPlayerManager;
import me.rabrg.ares.storage.FileStorageManager;

import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

	private static Plugin instance;

	public static Plugin get() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		
		initiateManagers();
	}

	public void initiateManagers() {
		FileStorageManager.initiate();
		AresPlayerManager.initiate();
		KitManager.initiate();
	}
}
