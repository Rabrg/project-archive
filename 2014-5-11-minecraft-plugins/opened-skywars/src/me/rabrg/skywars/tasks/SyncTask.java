package me.rabrg.skywars.tasks;

import me.rabrg.skywars.controllers.GameController;
import me.rabrg.skywars.game.Game;

public class SyncTask implements Runnable {

	@SuppressWarnings("unused")
	private int tickCounter;

	@Override
	public void run() {
		for (final Game game : GameController.get().getAll()) {
			game.onTick();
		}

		// @TODO
		/*
		 * if (tickCounter++ == 10) { if (SkyWars.getDB() != null) {
		 * SkyWars.getDB().checkConnection(); } tickCounter = 0; }
		 */
	}
}
