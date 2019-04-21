package me.rabrg.league.listener;

import me.rabrg.league.League;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class RatingListener implements Listener {

	private final League league;

	public RatingListener(final League league) {
		this.league = league;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeathEvent(final PlayerDeathEvent event) {
		final Player loser = event.getEntity();
		if (loser.getKiller() != null) {
			final Player winner = loser.getKiller();
			league.getRatings().adjustRatings(winner, loser);
		}
	}
}
