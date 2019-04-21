package me.rabrg.kitpvp.listener;

import me.rabrg.kitpvp.KitPvP;
import me.rabrg.kitpvp.util.Language;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class KillStatisticsListener implements Listener {

	private final KitPvP kitPvP;

	public KillStatisticsListener(final KitPvP kitPvP) {
		this.kitPvP = kitPvP;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeathEvent(final PlayerDeathEvent event) {
		final Player dead = event.getEntity();
		final Player killer = dead.getKiller();
		if(killer != null) {
			kitPvP.getConfiguration().incrementPlayerDeaths(dead.getName());
			kitPvP.getConfiguration().incrementPlayerKills(killer.getName());
			kitPvP.getConfiguration().incrementPlayerKillstreak(killer.getName());
			
			if(kitPvP.getConfiguration().getPlayerKills(killer.getName()) % 100 == 0) {
				kitPvP.getServer().broadcastMessage(Language.HUNDREDTH_KILL_BROADCAST.toString(killer.getName(), kitPvP.getConfiguration().getPlayerKills(killer.getName())));
			}
			if(kitPvP.getConfiguration().getPlayerKillstreak(killer.getName()) % 5 == 0) {
				kitPvP.getServer().broadcastMessage(Language.MULTIPLE_FIVE_KILLSTREAK_BROADCAST.toString(killer.getName(), kitPvP.getConfiguration().getPlayerKillstreak(killer.getName())));
			}
		}
	}
}
