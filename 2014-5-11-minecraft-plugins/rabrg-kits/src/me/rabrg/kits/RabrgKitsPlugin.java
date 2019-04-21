package me.rabrg.kits;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import me.rabrg.kits.command.KitCommandExecutor;
import me.rabrg.kits.command.SaveKitCommandExecutor;
import me.rabrg.protection.RabrgProtectionPlugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class RabrgKitsPlugin extends JavaPlugin {

	private File kitsConfigurationFile;
	private FileConfiguration kitsConfiguration;

	private RabrgProtectionPlugin rabrgProtectionPlugin;

	private Map<String, Kit> kits;

	@Override
	public void onEnable() {
		kitsConfigurationFile = new File(getDataFolder(), "kits.yml");
		try {
			getDataFolder().mkdir();
			kitsConfigurationFile.createNewFile();
		} catch (final IOException e) {
			getLogger().log(Level.SEVERE, "IOException thrown while loading kits, disabling plugin!", e);
			getServer().getPluginManager().disablePlugin(this);
		}
		
		kitsConfiguration = YamlConfiguration.loadConfiguration(kitsConfigurationFile);
		
		rabrgProtectionPlugin = (RabrgProtectionPlugin) getServer().getPluginManager().getPlugin("RabrgProtection");
		
		kits = new HashMap<String, Kit>();
		for (final String key : kitsConfiguration.getKeys(false)) {
			final Kit kit = new Kit(key);
			kit.deserialize(kitsConfiguration);
			kits.put(key, kit);
		}
		getCommand("savekit").setExecutor(new SaveKitCommandExecutor(this));
		getCommand("kit").setExecutor(new KitCommandExecutor(this));
	}

	@Override
	public void onDisable() {
		try {
			kitsConfiguration.save(kitsConfigurationFile);
		} catch (final IOException e) {
			getLogger().log(Level.SEVERE, "IOException thrown while saving kits!", e);
		}
	}

	public RabrgProtectionPlugin getRabrgProtectionPlugin() {
		return rabrgProtectionPlugin;
	}

	public void putKit(final String name, final Kit kit) {
		if (kits.containsKey(name)) {
			kits.remove(name);
		}
		kits.put(name, kit);
		kit.serialize(kitsConfiguration);
	}

	public Kit getKit(final String name) {
		return kits.get(name);
	}
}
