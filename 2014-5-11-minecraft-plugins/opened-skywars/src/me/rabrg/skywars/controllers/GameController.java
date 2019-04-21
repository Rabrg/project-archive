package me.rabrg.skywars.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.game.Game;
import me.rabrg.skywars.game.GameState;

import com.google.common.collect.Lists;
import com.sk89q.worldedit.CuboidClipboard;

public class GameController {

	private static GameController instance;
	private final List<Game> gameList = Lists.newArrayList();

	public Game findEmpty() {
		for (final Game game : gameList) {
			if (game.getState() != GameState.PLAYING && !game.isFull()) {
				return game;
			}
		}

		return create();
	}

	public Game create() {
		CuboidClipboard schematic = SchematicController.get().getRandom();
		Game game = new Game(schematic);

		while (!game.isReady()) {
			final String schematicName = SchematicController.get().getName(schematic);
			SkyWars.get().getLogger().log(Level.SEVERE, String.format("Schematic '%s' does not have any spawns set!", schematicName));
			SchematicController.get().remove(schematicName);

			schematic = SchematicController.get().getRandom();
			game = new Game(schematic);
		}

		gameList.add(game);
		return game;
	}

	public void remove(@Nonnull final Game game) {
		gameList.remove(game);
	}

	public void shutdown() {
		for (final Game game : new ArrayList<Game>(gameList)) {
			game.onGameEnd();
		}
	}

	public Collection<Game> getAll() {
		return gameList;
	}

	public static GameController get() {
		if (instance == null) {
			return instance = new GameController();
		}

		return instance;
	}
}
