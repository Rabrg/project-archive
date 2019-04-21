package me.rabrg.skywars.config;

import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.utilities.LocationUtil;

import com.google.common.collect.Lists;

public class PluginConfig {

	private static FileConfiguration storage;
	private static Location lobbySpawn;
	private static List<String> whitelistedCommands = Lists.newArrayList();

	static {
		storage = SkyWars.get().getConfig();

		lobbySpawn = LocationUtil.getLocation(Bukkit.getWorld(storage.getString("lobby.world")), storage.getString("lobby.spawn"));
		if (storage.contains("whitelisted-commands")) {
			whitelistedCommands = storage.getStringList("whitelisted-commands");
		}
	}

	public static Location getLobbySpawn() {
		return lobbySpawn;
	}

	public static void setLobbySpawn(final Location location) {
		lobbySpawn = location.clone();
		storage.set("lobby.world", lobbySpawn.getWorld().getName());
		storage.set("lobby.spawn", String.format(Locale.US, "%.2f %.2f %.2f %.2f %.2f", location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch()));
		SkyWars.get().saveConfig();
	}

	public static boolean isCommandWhitelisted(final String command) {
		return whitelistedCommands.contains(command.replace("/", ""));
	}

	public static int getIslandsPerWorld() {
		return storage.getInt("islands-per-row", 100);
	}

	public static int getIslandSize() {
		return storage.getInt("island-size", 100);
	}

	public static int getScorePerKill(final Player player) {
		final String group = SkyWars.getPermission().getPrimaryGroup(player);

		if (storage.contains("score.groups." + group + ".per-kill")) {
			return storage.getInt("score.groups." + group + ".per-kill");
		}

		return storage.getInt("score.per-kill", 3);
	}

	public static int getScorePerWin(final Player player) {
		final String group = SkyWars.getPermission().getPrimaryGroup(player);

		if (storage.contains("score.groups." + group + ".per-win")) {
			return storage.getInt("score.groups." + group + ".per-win");
		}

		return storage.getInt("score.per-win", 10);
	}

	public static int getScorePerDeath(final Player player) {
		final String group = SkyWars.getPermission().getPrimaryGroup(player);

		if (storage.contains("score.groups." + group + ".per-death")) {
			return storage.getInt("score.groups." + group + ".per-death");
		}

		return storage.getInt("score.per-death", -1);
	}

	public static int getScorePerLeave(final Player player) {
		final String group = SkyWars.getPermission().getPrimaryGroup(player);

		if (storage.contains("score.groups." + group + ".per-leave")) {
			return storage.getInt("score.groups." + group + ".per-leave");
		}

		return storage.getInt("score.per-leave", -1);
	}

	public static long getStatisticsUpdateInterval() {
		return storage.getInt("statistics.update-interval", 600) * 20L;
	}

	public static int getStatisticsTop() {
		return storage.getInt("statistics.top", 30);
	}

	public static boolean buildSchematic() {
		return storage.getBoolean("island-building.enabled", false);
	}

	public static int blocksPerTick() {
		return storage.getInt("island-building.blocks-per-tick", 20);
	}

	public static long buildInterval() {
		return storage.getLong("island-building.interval", 1);
	}

	public static boolean buildCages() {
		return storage.getBoolean("build-cages", true);
	}

	public static boolean ignoreAir() {
		return storage.getBoolean("ignore-air", false);
	}

	public static boolean fillChests() {
		return storage.getBoolean("fill-chests", true);
	}

	public static boolean useEconomy() {
		return storage.getBoolean("use-economy", false);
	}

	public static boolean chatHandledByOtherPlugin() {
		return storage.getBoolean("chat-handled-by-other-plugin", false);
	}

	public static boolean clearInventory() {
		return storage.getBoolean("clear-inventory-on-join", true);
	}

	public static boolean saveInventory() {
		return storage.getBoolean("save-inventory", false);
	}
}
