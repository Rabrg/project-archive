package me.rabrg.hungergames.game;

/**
 * Represents a single state of a hunger game.
 * @author Ryan Greene
 *
 */
public enum HungerGamesState {

	/**
	 * A state where the game is waiting for more players before it starts.
	 */
	WAITING,
	
	/**
	 * A state where players are invincible to damage and hunger loss.
	 */
	INVINCIBILITY,
	
	/**
	 * A state where players fight.
	 */
	FIGHTING;
}
