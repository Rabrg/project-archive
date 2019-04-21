package me.rabrg.hungergames.game.player;

import org.bukkit.entity.Player;

/**
 * Represents a single player who is playing hunger games.
 * @author Ryan Greene
 *
 */
public final class HungerGamesPlayer {

	/**
	 * The player playing hunger games.
	 */
	private final Player player;

	/**
	 * Constructs a new hunger games player with the specified player playing hungergames.
	 * @param player The player playing hunger games.
	 */
	public HungerGamesPlayer(final Player player) {
		this.player = player;
	}

	/**
	 * Gets the player playing hunger games.
	 * @return The player playing hunger games.
	 */
	public Player getPlayer() {
		return player;
	}
}
