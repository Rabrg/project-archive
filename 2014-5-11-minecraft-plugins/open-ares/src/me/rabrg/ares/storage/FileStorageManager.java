package me.rabrg.ares.storage;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.rabrg.ares.Plugin;
import me.rabrg.ares.kit.KitManager;
import me.rabrg.ares.player.AresPlayer;

public final class FileStorageManager {

	private static final File directory = new File(Plugin.get().getDataFolder(), "player_data");

	public static void initiate() {
		directory.mkdirs();
	}

	public static void loadAresPlayer(final AresPlayer aresPlayer) {
		try {
			final File file = new File(directory, aresPlayer + ".yml");
			if (!file.exists() && !file.createNewFile()) {
				System.out.println("Failed to load player " + aresPlayer + ": Could not create player file.");
				return;
			}

			final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			aresPlayer.setKit(KitManager.getKitByName(configuration.getString("kit", "Melee")));

		} catch (final IOException e) {
			System.out.println("Failed to load player " + aresPlayer + ": " + e.getMessage());
		}
	}

	public static void saveAresPlayer(final AresPlayer aresPlayer) {
		try {
			final File file = new File(directory, aresPlayer + ".yml");
			if (!file.exists() && !file.createNewFile()) {
				System.out.println("Failed to save player " + aresPlayer + ": Could not create player file.");
				return;
			}

			final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			configuration.set("kit", aresPlayer.getKit().getName());
			configuration.save(file);

		} catch (final IOException e) {
			System.out.println("Failed to save player " + aresPlayer + ": " + e.getMessage());
		}
	}
}
