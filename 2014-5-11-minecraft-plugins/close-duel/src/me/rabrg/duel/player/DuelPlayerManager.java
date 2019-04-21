package me.rabrg.duel.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public final class DuelPlayerManager {

	private static final Map<Player, DuelPlayer> players = new HashMap<Player, DuelPlayer>();

	public static DuelPlayer registerPlayer(final Player player) {
		return players.put(player, new DuelPlayer(player));
	}

	public static DuelPlayer getPlayer(final Player player) {
		return players.get(player);
	}

	public static DuelPlayer deregisterPlayer(final Player player) {
		return players.remove(player);
	}
}
