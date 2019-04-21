package com.github.pfacheris.BukkitDuel;

import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.pfacheris.BukkitDuel.Handlers.ArenaMgr;
import com.github.pfacheris.BukkitDuel.Handlers.DuelMgr;

//in next version: multiple people in duels, faction fights

public class BukkitDuel extends JavaPlugin {
	public static final Logger log = Logger.getLogger("Minecraft");
	public static Economy economy = null;
	public static ArenaMgr arenaManager;
	public static DuelMgr duelManager;

	@Override
	public void onEnable() {
		loadConfiguration();
		setupEconomy();
		arenaManager = new ArenaMgr(this);
		duelManager = new DuelMgr(this);
		final DuelCommandExecutor myCommandExecutor = new DuelCommandExecutor(this);
		getCommand("duel").setExecutor(myCommandExecutor);
	}

	@Override
	public void onDisable() {
		BukkitDuel.log.info("All duels concluding, BukkitDuel shutting down.");
	}

	public void registerEvents(final Listener listener) {
		final PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(listener, this);
	}

	private boolean setupEconomy() {
		final RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return economy != null;
	}

	public void loadConfiguration() {
		final String path = "Arenas";
		getConfig().addDefault(path, null);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}