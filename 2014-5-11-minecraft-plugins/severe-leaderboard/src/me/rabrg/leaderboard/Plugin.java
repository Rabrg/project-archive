package me.rabrg.leaderboard;

import java.io.File;
import java.io.IOException;

import me.rabrg.leaderboard.command.DeathsCommandExecutor;
import me.rabrg.leaderboard.command.KillsCommandExecutor;
import me.rabrg.leaderboard.listener.LeaderboardListener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

	private File killConfigurationFile;

	private FileConfiguration killFileConfiguration;

	private File deathConfigurationFile;

	private FileConfiguration deathFileConfiguration;

	@Override
	public void onEnable() {
		setupConfiguration();
		getCommand("kills").setExecutor(new KillsCommandExecutor(this));
		getCommand("deaths").setExecutor(new DeathsCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new LeaderboardListener(this), this);
	}

	@Override
	public void onDisable() {
		saveConfiguration();
	}

	private void setupConfiguration() {
		killConfigurationFile = new File(getDataFolder(), "kills.yml");
		killFileConfiguration = YamlConfiguration.loadConfiguration(killConfigurationFile);
		deathConfigurationFile = new File(getDataFolder(), "deaths.yml");
		deathFileConfiguration = YamlConfiguration.loadConfiguration(deathConfigurationFile);
	}

	private void saveConfiguration() {
		try {
			killFileConfiguration.save(killConfigurationFile);
			deathFileConfiguration.save(deathConfigurationFile);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public int getKills(final String player) {
		return killFileConfiguration.getInt(player, 0);
	}

	public void incrementKills(final String player) {
		killFileConfiguration.set(player, (killFileConfiguration.getInt(player) + 1));
	}

	public int getDeaths(final String player) {
		return deathFileConfiguration.getInt(player, 0);
	}

	public void incrementDeaths(final String player) {
		deathFileConfiguration.set(player, (deathFileConfiguration.getInt(player) + 1));
	}
}
