package me.rabrg.hungergames.listener;

import me.rabrg.hungergames.HungerGamesPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * A listener which handles lobby related events.
 * @author Ryan Greene
 *
 */
public final class HungerGamesLobbyListener implements Listener {

	/**
	 * The hunger games plug-in.
	 */
	private final HungerGamesPlugin hungerGamesPlugin;

	/**
	 * Constructs a new hunger games lobby listener for the specified hunger games plug-in.
	 * @param hungerGamesPlugin The hunger games plug-in.
	 */
	public HungerGamesLobbyListener(final HungerGamesPlugin hungerGamesPlugin) {
		this.hungerGamesPlugin = hungerGamesPlugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onAsyncPlayerPreLoginEvent(final AsyncPlayerPreLoginEvent event) {
		event.setLoginResult(hungerGamesPlugin.getHungerGames().getCurrentLoginResult());
	}
}
