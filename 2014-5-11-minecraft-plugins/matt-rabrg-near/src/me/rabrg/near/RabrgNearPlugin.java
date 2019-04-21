package me.rabrg.near;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import me.rabrg.near.command.NearCommandExecutor;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class RabrgNearPlugin extends JavaPlugin {
	private File cooldownFile;
	private FileConfiguration cooldownFileConfiguration;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		reloadConfig();
		cooldownFile = new File(getDataFolder(), "cooldowns.yml");
		cooldownFileConfiguration = YamlConfiguration.loadConfiguration(cooldownFile);
		getCommand("near").setExecutor(new NearCommandExecutor(this));
	}

	@Override
	public void onDisable() {
		try {
			saveConfig();
			cooldownFileConfiguration.save(cooldownFile);
		} catch (final IOException e) {
			getLogger().log(Level.SEVERE, "IOException thrown while saving configurations!", e);
		}
	}

	public void setCooldown(final UUID uuid, final long cooldown) {
		cooldownFileConfiguration.set(uuid.toString(), Long.valueOf(cooldown));
	}

	public long getCooldown(final UUID uuid) {
		return cooldownFileConfiguration.getLong(uuid.toString());
	}
}
