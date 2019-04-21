package me.rabrg.skywars.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.config.PluginConfig;
import me.rabrg.skywars.controllers.StatisticsController;
import me.rabrg.skywars.database.Database;

public class StatisticsUpdater extends BukkitRunnable {

	public StatisticsUpdater() {
		runTaskTimerAsynchronously(SkyWars.get(), 20L, PluginConfig.getStatisticsUpdateInterval());
	}

	@Override
	public void run() {
		final StatisticsController statisticsController = StatisticsController.get();

		try {
			final Database database = new Database(SkyWars.get().getConfig().getConfigurationSection("database"));
			statisticsController.setTopList(database.getTopScore(PluginConfig.getStatisticsTop()));
			database.close();

		} catch (final Exception ignored) {
		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(SkyWars.get(), new Runnable() {
			@Override
			public void run() {
				statisticsController.update();
			}
		});
	}
}
