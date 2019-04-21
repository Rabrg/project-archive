package org.hub.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.hub.arena.runnable.ArenaStartRunnable;

import com.sk89q.worldedit.bukkit.selections.Selection;

/**
 * A class which manages all the {@link Arena}s.
 * @author Ryan Greene
 *
 */
public final class ArenaManager {

	/**
	 * The list of arenas being managed.
	 */
	private final List<Arena> arenas;

	/**
	 * The selection which contains the waiting room.
	 */
	private final Selection waitingRoom;

	/**
	 * The delay between the waiting room selecting players for a new arena.
	 */
	private final long selectDelay;

	/**
	 * The minimum amount of players required in the waiting room for an arena to start.
	 */
	private final int minimumPlayers;

	/**
	 * The runnable which gets executed when a arena manager trys to create a new game with the players in its waiting room.
	 */
	private final ArenaStartRunnable arenaStartRunnable;

	/**
	 * Constructs a new arena manager for the specified plug-in.
	 * @param plugin The plug-in the arena manager is for.
	 */
	public ArenaManager(JavaPlugin plugin) {
		arenas = new ArrayList<Arena>();
		
		final FileConfiguration config = plugin.getConfig();
		waitingRoom = (Selection) config.get("waitingroom.selection", null);
		selectDelay = config.getLong("waitingroom.selectdelay");
		minimumPlayers = config.getInt("waitingroom.minimumplayers");
		
		arenaStartRunnable = new ArenaStartRunnable(this);
		arenaStartRunnable.runTaskTimer(plugin, 60, selectDelay);
	}

	/**
	 * Creates a new arena with the specified players.
	 * @param players The players in the new arena.
	 */
	public void createArena(final List<Player> players) {
		// for (final Player player : players) {
			Bukkit.getServer().broadcastMessage("Arena has been created with: " + players);
		// }
	}

	/**
	 * Gets the list of arenas being managed.
	 * @return The list of arenas being managed.
	 */
	public List<Arena> getArenas() {
		return arenas;
	}

	/**
	 * Gets the waiting room.
	 * @return The waiting room.
	 */
	public Selection getWaitingRoom() {
		return waitingRoom;
	}

	/**
	 * Gets the delay between the waiting room selecting players for a new arena.
	 * @return The delay between the waiting room selecting players for a new arena.
	 */
	public long getSelectDelay() {
		return selectDelay;
	}

	/**
	 * Gets the minimum amount of players required in the waiting room for an arena to start.
	 * @return The minimum amount of players required in the waiting room for an arena to start.
	 */
	public int getMinimumPlayers() {
		return minimumPlayers;
	}
}
