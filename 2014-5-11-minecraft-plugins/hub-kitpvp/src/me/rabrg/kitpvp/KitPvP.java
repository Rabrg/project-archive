package me.rabrg.kitpvp;

import java.io.IOException;
import java.util.logging.Level;

import me.rabrg.kitpvp.command.DeathsCommandExecutor;
import me.rabrg.kitpvp.command.KillsCommandExecutor;
import me.rabrg.kitpvp.command.KillstreakCommandExecutor;
import me.rabrg.kitpvp.kit.KitManager;
import me.rabrg.kitpvp.listener.KillStatisticsListener;
import me.rabrg.kitpvp.listener.KitSignListener;
import me.rabrg.kitpvp.listener.MechanicListener;
import me.rabrg.kitpvp.util.Configuration;
import me.rabrg.kitpvp.util.Cuboid;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class KitPvP extends JavaPlugin {

	private Configuration configuration;

	private KitManager kitManager;

	private Cuboid spawn;

	@Override
	public void onEnable() {
		configuration = new Configuration(this);
		kitManager = new KitManager(this);
		
		final World world = getServer().getWorld("world");
		spawn = new Cuboid(new Location(world, 64, 0, 64), new Location(world, -64, 256, -64));
		
		registerCommandExecutors();
		registerEventListeners();
	}

	private void registerCommandExecutors() {
		getCommand("kills").setExecutor(new KillsCommandExecutor(this));
		getCommand("killstreak").setExecutor(new KillstreakCommandExecutor(this));
		getCommand("deaths").setExecutor(new DeathsCommandExecutor(this));
	}

	private void registerEventListeners() {
		getServer().getPluginManager().registerEvents(new KillStatisticsListener(this), this);
		getServer().getPluginManager().registerEvents(new KitSignListener(this), this);
		getServer().getPluginManager().registerEvents(new MechanicListener(this), this);
	}

	@Override
	public void onDisable() {
		try {
			configuration.save();
		} catch (final IOException e) {
			getLogger().log(Level.SEVERE, "Exception thrown while saving configuration", e);
		}
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public KitManager getKitManager() {
		return kitManager;
	}

	public Cuboid getSpawn() {
		return spawn;
	}
}
