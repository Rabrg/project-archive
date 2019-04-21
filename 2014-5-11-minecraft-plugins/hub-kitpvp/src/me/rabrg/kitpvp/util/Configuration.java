package me.rabrg.kitpvp.util;

import java.io.File;
import java.io.IOException;

import me.rabrg.kitpvp.KitPvP;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public final class Configuration {

	private final File killStatisticsFile;

	private final FileConfiguration killStatisticsConfiguration;

	public Configuration(final KitPvP kitPvP) {
		this.killStatisticsFile = new File(kitPvP.getDataFolder(), "kill-statistics.yml");
		this.killStatisticsConfiguration = YamlConfiguration.loadConfiguration(killStatisticsFile);
	}

	public void save() throws IOException {
		killStatisticsConfiguration.save(killStatisticsFile);
	}

	public int getPlayerKills(final String player) {
		return killStatisticsConfiguration.getInt("kills." + player.toLowerCase());
	}

	public void setPlayerKills(final String player, final int kills) {
		killStatisticsConfiguration.set("kills." + player.toLowerCase(), kills);
	}

	public void incrementPlayerKills(final String player) {
		killStatisticsConfiguration.set("kills." + player.toLowerCase(), getPlayerKills(player) + 1);
	}

	public void decrementPlayerKills(final String player) {
		killStatisticsConfiguration.set("kills." + player.toLowerCase(), getPlayerKills(player) - 1);
	}

	public void resetPlayerKills(final String player) {
		killStatisticsConfiguration.set("kills." + player.toLowerCase(), 0);
	}

	public int getPlayerKillstreak(final String player) {
		return killStatisticsConfiguration.getInt("killstreak." + player.toLowerCase());
	}

	public void setPlayerKillstreak(final String player, final int killstreak) {
		killStatisticsConfiguration.set("killstreak." + player.toLowerCase(), killstreak);
	}

	public void incrementPlayerKillstreak(final String player) {
		killStatisticsConfiguration.set("killstreak." + player.toLowerCase(), getPlayerKills(player) + 1);
	}

	public void decrementPlayerKillstreak(final String player) {
		killStatisticsConfiguration.set("killstreak." + player.toLowerCase(), getPlayerKills(player) - 1);
	}

	public void resetPlayerKillstreak(final String player) {
		killStatisticsConfiguration.set("killstreak." + player.toLowerCase(), 0);
	}

	public int getPlayerDeaths(final String player) {
		return killStatisticsConfiguration.getInt("deaths." + player.toLowerCase());
	}

	public void setPlayerDeaths(final String player, final int deaths) {
		killStatisticsConfiguration.set("deaths." + player.toLowerCase(), deaths);
	}

	public void incrementPlayerDeaths(final String player) {
		killStatisticsConfiguration.set("deaths." + player.toLowerCase(), getPlayerDeaths(player) + 1);
	}

	public void decrementPlayerDeaths(final String player) {
		killStatisticsConfiguration.set("deaths." + player.toLowerCase(), getPlayerDeaths(player) - 1);
	}

	public void resetPlayerDeaths(final String player) {
		killStatisticsConfiguration.set("deaths." + player.toLowerCase(), 0);
	}
}
