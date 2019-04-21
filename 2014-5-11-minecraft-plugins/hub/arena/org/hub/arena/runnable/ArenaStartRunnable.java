package org.hub.arena.runnable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.hub.arena.ArenaManager;

/**
 * Represents a single runnable which gets executed when a arena manager trys to create a new game with the players in its waiting room.
 * @author Ryan Greene
 *
 */
public final class ArenaStartRunnable extends BukkitRunnable {

	/**
	 * The arena manager which is creating new arenas.
	 */
	private final ArenaManager manager;

	/**
	 * Constructs a new arena start runnable with the specified arena manager.
	 * @param manager The arena manager creating new arenas.
	 */
	public ArenaStartRunnable(final ArenaManager manager) {
		this.manager = manager;
	}

	@Override
	public void run() {
		final List<Player> players = new ArrayList<Player>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player != null) {
				if (manager.getWaitingRoom().contains(player.getLocation())) {
					players.add(player);
				}
			}
		}
		
		if (players.size() >= manager.getMinimumPlayers()) {
			manager.createArena(players);
		}
	}

}
