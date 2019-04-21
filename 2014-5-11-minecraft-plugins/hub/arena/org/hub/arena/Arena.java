package org.hub.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

/**
 * Represents a single arena.
 * @author Ryan Greene
 *
 */
public final class Arena {

	/**
	 * The arena manager which manages the arena.
	 */
	private final ArenaManager manager;

	/**
	 * Gets the arena manager which manages the arena.
	 * @return The arena manager which manages the arena.
	 */
	public ArenaManager getManager() {
		return manager;
	}

	/**
	 * The unique id of the arena.
	 */
	private final int id;

	/**
	 * Gets the unique id of the arena.
	 * @return The unique id of the arena.
	 */
	public int getUid() {
		return id;
	}

	/**
	 * The list of players inside of the arena.
	 */
	private final List<Player> players;

	/**
	 * Gets the list of players inside of the arena.
	 * @return The list of players inside of the arena.
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Constructs a new arena with the specified arena manager and unique id.
	 * @param manager The arena manager which manages the arena.
	 * @param uid The unique id of the arena.
	 */
	protected Arena(final ArenaManager manager, final int uid) {
		this.manager = manager;
		this.id = uid;
		this.players = new ArrayList<Player>();
	}

	/**
	 * Adds the specified player to the list of players inside of the arena.
	 * @param player The player being added to the list of players inside of the arena.
	 */
	public void addPlayer(final Player player) {
		if (!players.contains(player)) {
			players.add(player);
		}
	}

	/**
	 * Removes the specified player from the list of players inside of the arena.
	 * @param player The player being removed from the list of players inside of the arena.
	 */
	public void removePlayer(final Player player) {
		if (players.contains(player)) {
			players.remove(player);
		}
	}
}
