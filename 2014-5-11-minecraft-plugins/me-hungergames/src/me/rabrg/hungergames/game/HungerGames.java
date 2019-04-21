package me.rabrg.hungergames.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import me.rabrg.hungergames.HungerGamesPlugin;
import me.rabrg.hungergames.game.player.HungerGamesPlayer;
import me.rabrg.hungergames.listener.HungerGamesLobbyListener;

/**
 * Represents a single hunger game.
 * @author Ryan Greene
 *
 */
public final class HungerGames {

	/**
	 * The hunger games plug-in.
	 */
	private final HungerGamesPlugin hungerGamesPlugin;

	/**
	 * The maximum amount of hunger games players.
	 */
	private final int hungerGamesMaximumPlayers;

	/**
	 * A list containing all hunger games players.
	 */
	private final List<HungerGamesPlayer> hungerGamesPlayers;

	/**
	 * The current state of the hunger games.
	 */
	private HungerGamesState hungerGamesState;

	public HungerGames(final HungerGamesPlugin hungerGamesPlugin) {
		this.hungerGamesPlugin = hungerGamesPlugin;
		this.hungerGamesMaximumPlayers = 250;
		this.hungerGamesPlayers = new ArrayList<HungerGamesPlayer>(hungerGamesMaximumPlayers);
		this.updateHungerGamesState(HungerGamesState.WAITING);
	}

	/**
	 * Gets the current login result.
	 * @return The current login result.
	 */
	public Result getCurrentLoginResult() {
		if (hungerGamesState != HungerGamesState.WAITING) {
			return Result.KICK_OTHER;
		}
		if (hungerGamesPlayers.size() >= hungerGamesMaximumPlayers) {
			return Result.KICK_FULL;
		}
		return Result.ALLOWED;
	}

	public void updateHungerGamesState(final HungerGamesState hungerGamesState) {
		HandlerList.unregisterAll(hungerGamesPlugin);
		if (hungerGamesState == HungerGamesState.WAITING) {
			hungerGamesPlugin.getServer().getPluginManager().registerEvents(new HungerGamesLobbyListener(hungerGamesPlugin), hungerGamesPlugin);
		} else if (hungerGamesState == HungerGamesState.INVINCIBILITY) {
			
		} else if (hungerGamesState == HungerGamesState.FIGHTING) {
			
		}
		this.hungerGamesState = hungerGamesState;
	}
}
