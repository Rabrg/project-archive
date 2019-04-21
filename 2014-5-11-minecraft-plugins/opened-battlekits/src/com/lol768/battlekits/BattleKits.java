package com.lol768.battlekits;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.lol768.battlekits.commands.CommandBattleKits;
import com.lol768.battlekits.commands.CommandKitCreation;
import com.lol768.battlekits.commands.CommandRefillAll;
import com.lol768.battlekits.commands.CommandSoup;
import com.lol768.battlekits.listeners.DeathEvent;
import com.lol768.battlekits.listeners.InstaSoup;
import com.lol768.battlekits.listeners.PlayerReward;
import com.lol768.battlekits.listeners.RespawnKit;
import com.lol768.battlekits.listeners.SignHandler;
import com.lol768.battlekits.utilities.ConfigAccessor;
import com.lol768.battlekits.utilities.PM;

public class BattleKits extends JavaPlugin {

	public static Economy economy = null;
	public String html = "Error";
	public HashSet<String> death = new HashSet<>();
	public CommandBattleKits cbk = new CommandBattleKits(this);
	public boolean useTags = false;
	public PM PM = new PM(this);
	public ConfigAccessor global;
	public ConfigAccessor kits;
	public ConfigAccessor kitHistory;

	@Override
	public void onEnable() {
		if (!createDataDirectory()) {
			this.getLogger().severe("Couldn't create BattleKits data folder. Shutting down...");
			this.setEnabled(false);
		}
		final InputStream page = getResource("page.txt");
		html = convertStreamToString(page);
		makeConfigs();
	}

	@Override
	public void onDisable() {
		kitHistory.saveConfig();
	}

	public boolean setupEconomy() {
		final RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return economy != null;
	}

	/**
	 * Multi-world config accessor
	 * 
	 * @param String
	 *            path - The setting path to look for (e.g. settings.disable-xp)
	 * @param Player
	 *            p - Player to get world from
	 * @param Object
	 *            defaultValue - If empty, use this value
	 * @return Object - result
	 */
	public Object checkSetting(final String path, final Player p, final Object defaultValue) {
		if (global.getConfig().contains(p.getWorld().getName() + "." + path)) {
			return global.getConfig().get(p.getWorld().getName() + "." + path);
		} else {
			if (global.getConfig().contains(path)) {
				return global.getConfig().get(path);
			} else {
				return defaultValue;
			}
		}

	}

	/**
	 * Multi-world config accessor
	 * 
	 * @param String
	 *            path - The setting path to look for (e.g. settings.disable-xp)
	 * @param Player
	 *            p - Player to get world from
	 * @param Object
	 *            defaultValue - If empty, use this
	 * @return Object - resultant list
	 */
	public List<String> checkList(final String path, final Player p) {
		if (global.getConfig().contains(p.getWorld().getName() + "." + path)) {
			return global.getConfig().getStringList(p.getWorld().getName() + "." + path);
		} else {
			if (global.getConfig().contains(path)) {
				return global.getConfig().getStringList(path);
			} else {
				return null;
			}
		}

	}

	/**
	 * Multi-world config accessor -- accepts world name instead of Player
	 * 
	 * @param String
	 *            path - The setting path to look for (e.g. settings.disable-xp)
	 * @param String
	 *            world - World to check
	 * @param Object
	 *            defaultValue - If empty, use this
	 * @return Object - result
	 */
	public Object checkSetting(final String path, final String world, final Object defaultValue) {
		if (global.getConfig().contains(world + "." + path)) {
			// We have an override

			return global.getConfig().get(world + "." + path);
		} else {
			if (global.getConfig().contains(path)) {
				return global.getConfig().get(path);
			} else {
				return defaultValue;
			}
		}

	}

	public static String convertStreamToString(final InputStream is) {
		try (final Scanner s = new Scanner(is)) {
			s.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		}
	}

	public boolean buy(final double amount, final String name) {
		final Player p = Bukkit.getPlayer(name);
		final EconomyResponse r = economy.withdrawPlayer(name, amount);

		if (r.transactionSuccess()) {
			this.PM.notify(p, "Purchase successful! You spent " + amount + " and now have " + r.balance);
			return true;

		} else {
			this.PM.warn(p, "You don't have enough money! The kit costs " + amount + " and you have " + r.balance);
		}
		return false;
	}

	public boolean buyNeutral(final double amount, final String name) {
		final Player p = Bukkit.getPlayer(name);
		final EconomyResponse r = economy.withdrawPlayer(name, amount);

		if (r.transactionSuccess()) {
			this.PM.notify(p, "Purchase successful! You spent " + amount + " and now have " + r.balance);
			return true;

		} else {
			this.PM.warn(p, "You don't have enough money! This costs " + amount + " and you have " + r.balance);
		}
		return false;
	}

	private boolean createDataDirectory() {
		final File file = this.getDataFolder();
		if (!file.isDirectory()) {
			if (!file.mkdirs()) {
				// failed to create the non existent directory, so failed
				return false;
			}
		}
		return true;
	}

	public void makeConfigs() {
		kits = new ConfigAccessor(this, "kits.yml");
		global = new ConfigAccessor(this, "global.yml");
		kitHistory = new ConfigAccessor(this, "kitHistory.yml");
		kits.reloadConfig();
		global.reloadConfig();
		kitHistory.reloadConfig();
		postStartup();
	}

	public void postStartup() {
		getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
		if (global.getConfig().getBoolean("signs.enabled")) {
			getServer().getPluginManager().registerEvents(new SignHandler(this), this);
		}

		getServer().getPluginManager().registerEvents(new RespawnKit(this), this);
		getServer().getPluginManager().registerEvents(new PlayerReward(this), this);

		if (global.getConfig().getBoolean("settings.enable-restrictions")) {
			getLogger().info("Restrictions enabled. Use permissions to setup");

		} else {
			getLogger().info("Not enabling restrictions due to config setting");
		}
		getServer().getPluginManager().registerEvents(new InstaSoup(this), this);

		getCommand("soup").setExecutor(new CommandSoup(this));
		getCommand("toolkit").setExecutor(new CommandKitCreation(this));
		getCommand("fillall").setExecutor(new CommandRefillAll(this));

		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			this.getLogger().info("Vault found.");
			setupEconomy();

		} else {
			this.getLogger().info("Couldn't find Vault. Economy disabled for now.");
		}

		getCommand("battlekits").setExecutor(cbk);
	}

	public ItemStack setColor(final ItemStack item, final int color) {
		final LeatherArmorMeta im = (LeatherArmorMeta) item.getItemMeta();
		im.setColor(Color.fromRGB(color));
		item.setItemMeta(im);
		return item;
	}
}
