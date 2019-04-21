/*
 * AntiCheat for Bukkit.
 * Copyright (C) 2012-2014 AntiCheat Team | http://gravitydevelopment.net
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package net.gravitydevelopment.anticheat.manage;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.gravitydevelopment.anticheat.AntiCheat;
import net.gravitydevelopment.anticheat.config.Configuration;
import net.gravitydevelopment.anticheat.util.FileFormatter;
import net.gravitydevelopment.anticheat.util.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LoggingManager {

	private final Logger fileLogger;
	private static Handler fileHandler;
	private static List<String> logs = new CopyOnWriteArrayList<String>();
	private final Configuration config;

	public LoggingManager(final AntiCheat plugin, final Logger logger, final Configuration config) {
		this.fileLogger = Logger.getLogger("net.gravitydevelopment.anticheat.AntiCheat");
		this.config = config;
		try {
			final File file = new File(plugin.getDataFolder(), "log");
			if (!file.exists()) {
				file.mkdir();
			}
			fileHandler = new FileHandler(plugin.getDataFolder() + "/log/anticheat.log", true);
			fileHandler.setFormatter(new FileFormatter());
		} catch (final Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		fileLogger.setUseParentHandlers(false);
		fileLogger.addHandler(fileHandler);
	}

	public void log(final String message) {
		if (config.getConfig().logToConsole.getValue()) {
			logToConsole(message);
		}
		if (config.getConfig().logToFile.getValue()) {
			logToFile(message);
		}
		logToLogs(message);
	}

	public void debugLog(final String message) {
		Bukkit.getConsoleSender().sendMessage("[AntiCheat] " + ChatColor.GRAY + message);
		logToLogs(message);
	}

	public void logToConsole(final String message) {
		Bukkit.getConsoleSender().sendMessage("[AntiCheat] " + ChatColor.RED + message);
	}

	public void logToFile(final String message) {
		fileLogger.info(ChatColor.stripColor(message));
	}

	public void logFineInfo(final String message) {
		logToFile(message);
		logToLogs(message);
	}

	public void logToPlayers(final String message) {
		for(final Player player : Bukkit.getOnlinePlayers()) {
			if (Permission.SYSTEM_NOTICE.get(player)) {
				player.sendMessage(message);
			}
		}
	}

	private void logToLogs(final String message) { // Yo dawg
		logs.add(ChatColor.stripColor(message));
	}

	public List<String> getLastLogs() {
		final List<String> log = new CopyOnWriteArrayList<String>();
		if (logs.size() < 30) {
			return logs;
		}
		for (int i = logs.size() - 1; i >= 0; i--) {
			log.add(logs.get(i));
		}
		logs.clear();
		return log;
	}

	public void closeHandler() {
		fileHandler.close();
	}
}
