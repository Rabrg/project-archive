package com.lol768.battlekits.utilities;

/*
 * Copyright (C) 2012 SagaciousZed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class that sorts out the configs
 * 
 * @author SagaciousZed & lol768
 * 
 */
public class ConfigAccessor {

	private final String fileName;
	private final JavaPlugin plugin;
	private YamlConfiguration defConfig;
	private File configFile;
	private FileConfiguration fileConfiguration;

	@SuppressWarnings("deprecation")
	public ConfigAccessor(final JavaPlugin plugin, final String fileName) {
		if (plugin == null) {
			throw new IllegalArgumentException("plugin cannot be null");
		}
		if (!plugin.isInitialized()) {
			throw new IllegalArgumentException("plugin must be initiaized");
		}
		this.plugin = plugin;
		this.fileName = fileName;
	}

	public void reloadConfig() {
		if (configFile == null) {
			final File dataFolder = plugin.getDataFolder();
			if (dataFolder == null) {
				throw new IllegalStateException();
			}
			configFile = new File(dataFolder, fileName);
		}

		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

		// Look for defaults in the jar
		final InputStream defConfigStream = plugin.getResource(fileName);
		plugin.getLogger().info("length is " + configFile.length());

		if (defConfigStream != null && configFile.length() < 1) {
			plugin.getLogger().info("Generating config for " + fileName);
			defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			fileConfiguration.setDefaults(defConfig);
			fileConfiguration = defConfig;
			if (fileName == "kitHistory.yml") {
				fileConfiguration
						.options()
						.header("This file contains the kit history, i.e. the last kit the user requested for respawning kits on death\nAlso contains info on whether the player has used their kit in their life\nThere is no reason to ever modify this file.");
			}

			if (fileName == "global.yml") {
				fileConfiguration.options().header(
						"Global BattleKits settings\nAdd world-specific blocks by warapping everything in the name of the world.\nRestrictions are handled by permissions now.");
			}

			if (fileName == "kits.yml") {
				fileConfiguration.options().header("Kit definitions\nThis is where you can add your own kits and customise various details");
			}
			plugin.getLogger().info("Saving " + fileName + "...");
			this.saveConfig();
		}

	}

	public FileConfiguration getConfig() {
		if (fileConfiguration == null) {
			this.reloadConfig();
		}
		return fileConfiguration;
	}

	public void saveConfig() {
		if (fileConfiguration == null || configFile == null) {
			return;
		} else {
			try {
				getConfig().save(configFile);
			} catch (final IOException ex) {
				plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
			}
		}
	}

	public void saveDefaultConfig() {
		if (!configFile.exists()) {
			this.plugin.saveResource(fileName, false);
		}
	}

}
