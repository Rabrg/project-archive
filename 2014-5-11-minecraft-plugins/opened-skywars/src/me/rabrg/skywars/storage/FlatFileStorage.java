package me.rabrg.skywars.storage;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nonnull;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.config.PluginConfig;
import me.rabrg.skywars.player.GamePlayer;

public class FlatFileStorage extends DataStorage {

	@Override
	public void loadPlayer(@Nonnull final GamePlayer player) {
		try {
			final File dataDirectory = SkyWars.get().getDataFolder();
			final File playerDataDirectory = new File(dataDirectory, "player_data");

			if (!playerDataDirectory.exists() && !playerDataDirectory.mkdirs()) {
				System.out.println("Failed to load player " + player + ": Could not create player_data directory.");
				return;
			}

			final File playerFile = new File(playerDataDirectory, player + ".yml");
			if (!playerFile.exists() && !playerFile.createNewFile()) {
				System.out.println("Failed to load player " + player + ": Could not create player file.");
				return;
			}

			final FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(playerFile);

			if (!PluginConfig.useEconomy()) {
				player.setScore(fileConfiguration.getInt("score", 0));
			}

		} catch (final IOException ioException) {
			System.out.println("Failed to load player " + player + ": " + ioException.getMessage());
		}
	}

	@Override
	public void savePlayer(@Nonnull final GamePlayer player) {
		try {
			final File dataDirectory = SkyWars.get().getDataFolder();
			final File playerDataDirectory = new File(dataDirectory, "player_data");

			if (!playerDataDirectory.exists() && !playerDataDirectory.mkdirs()) {
				System.out.println("Failed to save player " + player + ": Could not create player_data directory.");
				return;
			}

			final File playerFile = new File(playerDataDirectory, player + ".yml");
			if (!playerFile.exists() && !playerFile.createNewFile()) {
				System.out.println("Failed to save player " + player + ": Could not create player file.");
				return;
			}

			final FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(playerFile);
			fileConfiguration.set("score", player.getScore());
			fileConfiguration.save(playerFile);

		} catch (final IOException ioException) {
			System.out.println("Failed to save player " + player + ": " + ioException.getMessage());
		}
	}

}