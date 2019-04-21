package me.rabrg.ares.player;

import java.util.HashMap;
import java.util.Map;

import me.rabrg.ares.storage.FileStorageManager;

import org.bukkit.entity.Player;

public final class AresPlayerManager {

	private static final Map<Player, AresPlayer> aresPlayers = new HashMap<Player, AresPlayer>();

	public static void initiate() {
		
	}

	public static AresPlayer registerPlayer(final Player player) {
		AresPlayer aresPlayer = aresPlayers.put(player, new AresPlayer(player));
		FileStorageManager.loadAresPlayer(aresPlayer);
		return aresPlayer;
	}

	public static AresPlayer getAresPlayer(final Player player) {
		return aresPlayers.get(player);
	}

	public static AresPlayer deregisterPlayer(final Player player) {
		AresPlayer aresPlayer = aresPlayers.remove(player);
		FileStorageManager.saveAresPlayer(aresPlayer);
		return aresPlayer;
	}
}
