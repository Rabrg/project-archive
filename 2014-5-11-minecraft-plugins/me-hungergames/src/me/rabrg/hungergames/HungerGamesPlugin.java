package me.rabrg.hungergames;

import me.rabrg.hungergames.game.HungerGames;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The core class of the hunger games plug-in.
 * @author Ryan Greene
 *
 */
public final class HungerGamesPlugin extends JavaPlugin {

	/**
	 * The hunger games.
	 */
	private HungerGames hungerGames;

	@Override
	public void onEnable() {
		hungerGames = new HungerGames(this);
	}

	public HungerGames getHungerGames() {
		return hungerGames;
	}
}
