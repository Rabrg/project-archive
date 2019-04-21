package me.rabrg.duel.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.rabrg.duel.player.DuelPlayer;

public final class ArenaManager {

	public static final Location ARENA_SPAWN = new Location(Bukkit.getWorld("world"), 0, 64, 0);

	private static final List<Arena> arenas = new ArrayList<Arena>();

	public static void initiate() {
		arenas.add(new Arena(new Location(ARENA_SPAWN.getWorld(), -5.5, 64, -3.5), new Location(ARENA_SPAWN.getWorld(), -17.5, 64, 3.5)));
	}

	public static void startArena(final DuelPlayer redPlayer, final DuelPlayer bluePlayer) {
		for (final Arena arena : arenas) {
			if (arena.isVacant()) {
				arena.startDuel(redPlayer, bluePlayer);
				return;
			}
		}
		redPlayer.getPlayer().sendMessage("[Duel] There currently aren't any arenas available, sorry!");
		bluePlayer.getPlayer().sendMessage("[Duel] There currently aren't any arenas available, sorry!");
	}

	public static void endArena(final DuelPlayer winner, final DuelPlayer loser) {
		for (final Arena arena : arenas) {
			if (arena.getRedPlayer() == winner || arena.getBluePlayer() == winner) {
				arena.endDuel(winner, loser);
				return;
			}
		}
	}
}
