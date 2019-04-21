package me.rabrg.karambwan.node.misc;

import org.tbot.bot.TBot;
import org.tbot.methods.Game;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Widgets;
import org.tbot.methods.tabs.Equipment;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.Tile;

import me.rabrg.karambwan.node.Node;

public final class FailSafeNode extends Node {

	private static final Tile DUEL_ARENA_BANK_TILE = new Tile(3383, 3269);
	private static final Area DUEL_ARENA_AREA = new Area(3300, 3200, 3400, 3300);
	private static final Area ROUTE_AREA = new Area(2739, 3053, 2794, 3192);
	private long lastFail = System.currentTimeMillis();
	private int fails = 0;

	@Override
	public boolean validate() {
		if (!CASTLE_WARS_AREA.contains(Players.getLocal()) && !DUEL_ARENA_BANK_AREA.contains(Players.getLocal())
				&& GameObjects.getNearest("Portal") == null) {
			if (!ROUTE_AREA.contains(Players.getLocal())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int execute() {
		if (System.currentTimeMillis() - this.lastFail > 60000L) {
			this.fails = 0;
		}
		this.fails += 1;
		if (this.fails >= 5) {
			if (Equipment.getItem(12) != null) {
				if (Widgets.getCurrentTab() != 4) {
					Widgets.openTab(4);
					return Random.nextInt(600, 1200);
				}
				Equipment.getItemInSlot(12).interact("Castle Wars");
				return Random.nextInt(3000, 3600);
			} else if (DUEL_ARENA_AREA.contains(Players.getLocal())) {
				if (!DUEL_ARENA_BANK_AREA.contains(Players.getLocal())) {
					final Path path = Walking.findLocalPath(DUEL_ARENA_BANK_TILE);
					if (path != null)
						path.traverse();
				}
			} else {
				Game.logout();
				TBot.getBot().getScriptHandler().stopScript();
			}
		}
		this.lastFail = System.currentTimeMillis();
		return Random.nextInt(3000, 3600);
	}

	@Override
	public String getName() {
		return "Fail safe";
	}
}
