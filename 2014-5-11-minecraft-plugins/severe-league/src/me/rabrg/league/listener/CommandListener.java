package me.rabrg.league.listener;

import me.rabrg.league.League;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class CommandListener implements Listener {

	@SuppressWarnings("unused")
	private final League league;

	public CommandListener(final League league) {
		this.league = league;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(final PlayerCommandPreprocessEvent event) {
		if (/* is in arena && */ !event.getMessage().equals("leave")) {
			// event.setCancelled(true);
		}
	}
}
