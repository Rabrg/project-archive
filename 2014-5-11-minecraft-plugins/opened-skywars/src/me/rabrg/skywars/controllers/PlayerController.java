package me.rabrg.skywars.controllers;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.rabrg.skywars.player.GamePlayer;
import me.rabrg.skywars.storage.DataStorage;

import com.google.common.collect.Maps;

public class PlayerController {

	private final Map<Player, GamePlayer> playerRegistry = Maps.newHashMap();

	private PlayerController() {
		for (final Player player : Bukkit.getOnlinePlayers()) {
			register(player);
		}
	}

	public GamePlayer register(@Nonnull final Player bukkitPlayer) {
		GamePlayer gamePlayer = null;

		if (!this.playerRegistry.containsKey(bukkitPlayer)) {
			gamePlayer = new GamePlayer(bukkitPlayer);
			this.playerRegistry.put(bukkitPlayer, gamePlayer);
		}

		return gamePlayer;
	}

	public GamePlayer unregister(@Nonnull final Player bukkitPlayer) {
		return this.playerRegistry.remove(bukkitPlayer);
	}

	public GamePlayer get(@Nonnull final Player bukkitPlayer) {
		return this.playerRegistry.get(bukkitPlayer);
	}

	public Collection<GamePlayer> getAll() {
		return playerRegistry.values();
	}

	public void shutdown() {
		for (final GamePlayer gamePlayer : playerRegistry.values()) {
			DataStorage.get().savePlayer(gamePlayer);
		}

		playerRegistry.clear();
	}

	private static PlayerController instance;

	public static PlayerController get() {
		if (instance == null) {
			instance = new PlayerController();
		}

		return instance;
	}
}
