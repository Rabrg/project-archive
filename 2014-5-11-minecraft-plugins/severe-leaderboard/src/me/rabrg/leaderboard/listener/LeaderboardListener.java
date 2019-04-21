package me.rabrg.leaderboard.listener;

import me.rabrg.leaderboard.Plugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class LeaderboardListener implements Listener {

	private final Plugin plugin;

	public LeaderboardListener(final Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeathEvent(final PlayerDeathEvent event) {
		final Player player = event.getEntity();
		plugin.incrementDeaths(player.getName());
		if (event.getEntity().getKiller() != null) {
			plugin.incrementKills(player.getKiller().getName());
		}
	}
}
