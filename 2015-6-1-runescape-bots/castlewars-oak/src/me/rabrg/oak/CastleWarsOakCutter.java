package me.rabrg.oak;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(name = "CastleWarsOakCutter", author = "Rabrg", version = 0.1, info = "", logo = "")
public final class CastleWarsOakCutter extends Script {

	private final Position treesPosition = new Position(2454, 3089, 0);
	private final Position bankPosition = new Position(2443, 3083, 0);
	private final Area treeArea = new Area(2452, 3080, 2470, 3121);
	private final Area bankArea = new Area(3439, 3082, 2444, 3098);
	private final int oakTreeId = 11756;

	@Override
	public int onLoop() throws InterruptedException {
		if (!inventory.isFull()) {
			if (treeArea.contains(myPlayer())) {
				if (!myPlayer().isAnimating() && !myPlayer().isMoving()) {
					final RS2Object oakTree = objects.closest(treeArea, oakTreeId);
					if (oakTree != null) {
						oakTree.interact("Chop down");
						sleep(random(350, 750));
					}
				}
			} else {
				localWalker.walk(randomizePosition(treesPosition));
			}
		} else {
			if (bankArea.contains(myPlayer())) {
				bank.open();
				bank.depositAll();
				bank.close();
			} else {
				localWalker.walk(randomizePosition(treesPosition));
				localWalker.walk(randomizePosition(bankPosition));
			}
		}
		return random(100, 250);
	}

	private Position randomizePosition(final Position position) {
		final Position randomizedPosition = new Position(position.getX() + random(-1, 1), position.getY() + random(-1, 1), 0);
		log(randomizedPosition);
		return randomizedPosition;
	}
}
