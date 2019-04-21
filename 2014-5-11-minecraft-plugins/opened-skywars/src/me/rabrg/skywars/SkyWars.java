package me.rabrg.skywars;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.rabrg.skywars.commands.MainCommand;
import me.rabrg.skywars.controllers.ChestController;
import me.rabrg.skywars.controllers.GameController;
import me.rabrg.skywars.controllers.IconMenuController;
import me.rabrg.skywars.controllers.KitController;
import me.rabrg.skywars.controllers.PlayerController;
import me.rabrg.skywars.controllers.SchematicController;
import me.rabrg.skywars.controllers.WorldController;
import me.rabrg.skywars.database.Database;
import me.rabrg.skywars.listeners.BlockListener;
import me.rabrg.skywars.listeners.EntityListener;
import me.rabrg.skywars.listeners.InventoryListener;
import me.rabrg.skywars.listeners.PlayerListener;
import me.rabrg.skywars.player.GamePlayer;
import me.rabrg.skywars.storage.DataStorage;
import me.rabrg.skywars.storage.SQLStorage;
import me.rabrg.skywars.tasks.SyncTask;
import me.rabrg.skywars.utilities.CraftBukkitUtil;
import me.rabrg.skywars.utilities.FileUtils;
import me.rabrg.skywars.utilities.Messaging;
import me.rabrg.skywars.utilities.StringUtils;

import com.earth2me.essentials.IEssentials;

public class SkyWars extends JavaPlugin {

	private static SkyWars instance;
	private static Permission permission;
	private static Economy economy;
	private static Chat chat;
	private Database database;

	@Override
	public void onEnable() {
		instance = this;

		deleteIslandWorlds();

		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		reloadConfig();

		new Messaging(this);

		getCommand("skywars").setExecutor(new MainCommand());
		getCommand("global").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
				if (!(sender instanceof Player)) {
					return false;
				}

				if (args.length == 0) {
					sender.sendMessage("\247cUsage: /" + label + " <message>");
					return true;
				}

				final StringBuilder messageBuilder = new StringBuilder();
				for (final String arg : args) {
					messageBuilder.append(arg);
					messageBuilder.append(" ");
				}

				final GamePlayer gamePlayer = PlayerController.get().get((Player) sender);
				final String score = StringUtils.formatScore(gamePlayer.getScore());

				Bukkit.broadcastMessage(new Messaging.MessageFormatter().setVariable("player", gamePlayer.getBukkitPlayer().getDisplayName()).setVariable("score", score)
						.setVariable("message", Messaging.stripColor(messageBuilder.toString()))
						.setVariable("prefix", SkyWars.getChat().getPlayerPrefix(gamePlayer.getBukkitPlayer())).format("chat.global"));

				return true;
			}
		});

		try {
			final DataStorage.DataStorageType dataStorageType = DataStorage.DataStorageType.valueOf(getConfig().getString("data-storage", "FILE"));
			if (dataStorageType == DataStorage.DataStorageType.SQL && !setupDatabase()) {
				getLogger().log(Level.INFO, "Couldn't setup database, now using file storage.");
				DataStorage.setInstance(DataStorage.DataStorageType.FILE);

			} else {
				DataStorage.setInstance(dataStorageType);
			}

		} catch (final NullPointerException npe) {
			DataStorage.setInstance(DataStorage.DataStorageType.FILE);
		}

		setupPermission();
		setupEconomy();
		setupChat();

		SchematicController.get();
		WorldController.get();
		GameController.get();
		PlayerController.get();
		ChestController.get();
		KitController.get();
		IconMenuController.get();

		/*
		 * if (getDB() != null) { StatisticsController.get(); new
		 * StatisticsUpdater(); }
		 */

		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new SyncTask(), 20L, 20L);
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);

		GameController.get().shutdown();
		PlayerController.get().shutdown();

		if (DataStorage.get() instanceof SQLStorage && !CraftBukkitUtil.isRunning()) {
			final SQLStorage sqlStorage = (SQLStorage) DataStorage.get();
			while (!sqlStorage.saveProcessor.isEmpty()) {
				;
			}
			final long currentTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - currentTime < 1000L) {
				;
			}
			sqlStorage.saveProcessor.stop();
		}

		if (database != null) {
			database.close();
		}

		deleteIslandWorlds();
	}

	private void deleteIslandWorlds() {
		// Worlds
		File workingDirectory = new File(".");
		File[] contents = workingDirectory.listFiles();

		if (contents != null) {
			for (final File file : contents) {
				if (!file.isDirectory() || !file.getName().matches("island-\\d+")) {
					continue;
				}

				FileUtils.deleteFolder(file);
			}
		}

		// WorldGuard
		workingDirectory = new File("./plugins/WorldGuard/worlds/");
		contents = workingDirectory.listFiles();

		if (contents != null) {
			for (final File file : contents) {
				if (!file.isDirectory() || !file.getName().matches("island-\\d+")) {
					continue;
				}

				FileUtils.deleteFolder(file);
			}
		}
	}

	private boolean setupDatabase() {
		try {
			database = new Database(getConfig().getConfigurationSection("database"));

		} catch (final ClassNotFoundException exception) {
			getLogger().log(Level.SEVERE, String.format("Unable to register JDCB driver: %s", exception.getMessage()));
			return false;

		} catch (final SQLException exception) {
			getLogger().log(Level.SEVERE, String.format("Unable to connect to SQL server: %s", exception.getMessage()));
			return false;
		}

		try {
			database.createTables();
		} catch (final Exception exception) {
			getLogger().log(Level.SEVERE, String.format("An exception was thrown while attempting to create tables: %s", exception.getMessage()));
			return false;
		}

		return true;
	}

	private void setupPermission() {
		final RegisteredServiceProvider<Permission> chatProvider = getServer().getServicesManager().getRegistration(Permission.class);
		if (chatProvider != null) {
			permission = chatProvider.getProvider();
		}
	}

	private void setupEconomy() {
		final RegisteredServiceProvider<Economy> chatProvider = getServer().getServicesManager().getRegistration(Economy.class);
		if (chatProvider != null) {
			economy = chatProvider.getProvider();
		}
	}

	private void setupChat() {
		final RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}
	}

	public static SkyWars get() {
		return instance;
	}

	public static IEssentials getEssentials() {
		return (IEssentials) Bukkit.getPluginManager().getPlugin("Essentials");
	}

	public static Permission getPermission() {
		return permission;
	}

	public static Economy getEconomy() {
		return economy;
	}

	public static Chat getChat() {
		return chat;
	}

	public static Database getDB() {
		return instance.database;
	}
}
