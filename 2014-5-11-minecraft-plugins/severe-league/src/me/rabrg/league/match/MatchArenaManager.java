package me.rabrg.league.match;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.rabrg.league.League;

public final class MatchArenaManager {

	@SuppressWarnings("unused")
	private final League league;

	private final List<MatchArena> arenas;

	public MatchArenaManager(final League league) {
		this.league = league;
		this.arenas = new ArrayList<MatchArena>();
	}

	public void createMatch(final Player red, final Player blue) {
		for (final MatchArena arena : arenas) {
			if (arena.isOccupied()) {
				continue;
			}
			arena.startMatch(red, blue);
			return;
		}
		red.sendMessage("Sorry, no arenas were left opened!");
		blue.sendMessage("Sorry, no arenas were left opened!");
	}
}
