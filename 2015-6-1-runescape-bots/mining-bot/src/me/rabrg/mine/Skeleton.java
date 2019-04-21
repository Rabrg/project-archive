package me.rabrg.mine;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(name = "Miner", author = "Rabrg", version = 1.0, info = "", logo = "")
public class Skeleton extends Script {

	private final int miningAnimation = 628;
	private final int[] ironRocks = new int[] { 13710, 13711 };
	private final Area miningArea = new Area(3302, 3285, 3303, 3284);
	private final Position[] pathMineToBank = new Position[] {new Position(3302, 3273, 0), new Position(3303, 3263, 0), new Position(3297, 3252, 0), new Position(3292, 3240, 0), new Position(3282, 3233, 0), new Position(3278, 3222, 0), new Position(3279, 3210, 0), new Position(3282, 3198, 0), new Position(3281, 3186, 0), new Position(3277, 3174, 0), new Position(3269, 3167, 0)};
	private final Position[] pathBankToMine = new Position[] {new Position(3277, 3178, 0), new Position(3281, 3192, 0), new Position(3284, 3205, 0), new Position(3292, 3216, 0), new Position(3298, 3229, 0), new Position(3297, 3242, 0), new Position(3298, 3255, 0), new Position(3294, 3268, 0), new Position(3301, 3278, 0), new Position(3302, 3284, 0)};

	@Override
	public int onLoop() throws InterruptedException {
		if (!inventory.isFull()) {
			if (this.myPlayer().getAnimation() != miningAnimation) {
				final RS2Object object = objects.closest(miningArea, ironRocks);
				if (object != null) {
					object.interact("Mine");
					log("Mining rock");
				} else {
					log("No rocks to mine");
				}
			} else {
				log("Already mining");
			}
		} if (inventory.isFull()) {
			localWalker.walkPath(pathMineToBank);
			log("Inventory full, returning to bank");
			localWalker.waitUntilIdle();
			bank.open();
			bank.depositAll();
			bank.close();
		} else if (inventory.isEmpty()) {
			localWalker.walkPath(pathBankToMine);
			log("Inventory empty, returning to mine");
			localWalker.waitUntilIdle();
		}
		return random(10, 50);
	}

}