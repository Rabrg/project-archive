package com.lol768.battlekits.utilities;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lol768.battlekits.BattleKits;

public class PM {

	public BattleKits plugin;

	public PM(final BattleKits instance) {
		plugin = instance;
	}

	/**
	 * Logger method which supports localisation
	 * 
	 * @param message
	 */
	public void trLogger(String message, final String type) {
		final String ld = plugin.global.getConfig().getString("language"); // User's
																			// selected
																			// language

		if (plugin.global.getConfig().contains("messages." + ld + "." + message)) {
			message = plugin.global.getConfig().getString("messages." + ld + "." + message);
		} else {
			try {
				throw new Exception("Gimme class name");
			} catch (final Exception e) {
				plugin.getLogger().severe("Not given a valid language in config at " + e.getStackTrace()[1].getClassName() + "." + e.getStackTrace()[1].getMethodName() + "!");
			}
			return;
		}
		if (type.equals("info")) {
			plugin.getLogger().info(message);
			return;

		}

		if (type.equals("warn") || type.equals("warning")) {
			plugin.getLogger().warning(message);

			return;

		}

		if (type.equals("error") || type.equals("severe")) {
			plugin.getLogger().severe(message);

			return;

		}
		try {
			throw new Exception("Gimme class name");
		} catch (final Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Not given a valid type in {0}.{1}!", new Object[] { e.getStackTrace()[1].getClassName(), e.getStackTrace()[1].getMethodName() });
		}

	}

	/**
	 * Method to keep messages consistent Sends a red coloured warning to the
	 * supplied player
	 * 
	 * @param player
	 *            - The player to send the warning to
	 * @param message
	 *            - The message to send them
	 */
	public void warn(final Player player, final String message) {
		player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.translateAlternateColorCodes('&', plugin.global.getConfig().getString("brand")) + ChatColor.GRAY + "]"
				+ ChatColor.RED + " " + message);
	}

	/**
	 * Method to keep messages consistent Sends a red coloured warning to the
	 * supplied sender Supports console & uncast player (CommandSender)
	 * 
	 * @param sender
	 *            - The CommandSender to send the warning to
	 * @param message
	 *            - The message to send them
	 */
	public void warn(final CommandSender sender, final String message) {
		sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.translateAlternateColorCodes('&', plugin.global.getConfig().getString("brand")) + ChatColor.GRAY + "]"
				+ ChatColor.RED + " " + message);
	}

	/**
	 * Method to keep messages consistent Sends a yellow notification to the
	 * supplied player
	 * 
	 * @param player
	 *            - The player to send the message to
	 * @param message
	 *            - The message to send them
	 */
	public void notify(final CommandSender player, final String message) {
		player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.translateAlternateColorCodes('&', plugin.global.getConfig().getString("brand")) + ChatColor.GRAY + "]"
				+ ChatColor.YELLOW + " " + message);
	}

	/**
	 * Method to keep messages consistent Sends a yellow notification to the
	 * supplied sender
	 * 
	 * @param player
	 *            - The player to send the message to
	 * @param message
	 *            - The message to send them
	 */
	public void notify(final Player player, final String message) {
		player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.translateAlternateColorCodes('&', plugin.global.getConfig().getString("brand")) + ChatColor.GRAY + "]"
				+ ChatColor.YELLOW + " " + message);
	}
}
