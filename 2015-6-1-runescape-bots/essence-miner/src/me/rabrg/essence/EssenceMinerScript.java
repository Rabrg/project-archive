package me.rabrg.essence;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(name = "EssenceMiner", author = "Rabrg", version = 1.0, info = "", logo = "")
public final class EssenceMinerScript extends Script {

	private final Position bankPosition = new Position(3254, 3421, 0);
	private final Position halfwayPosition = new Position(3258, 3409, 0);
	private final Position auburyPosition = new Position(3253, 3398, 0);
	private final Position[] bankToAubury = new Position[] { halfwayPosition, auburyPosition };
	private final Position[] auburyToBank = new Position[] { halfwayPosition, bankPosition };
	private final Area auburyArea = new Area(3249, 3396, 3256, 3404);
	private final Area essenceArea = new Area(10000, 5000, 10500, 5500);
	private final Area centerEssenceArea = new Area(10362, 5307, 10372, 5313);
	private final Position essencePosition = new Position(9961, 5108, 0);
	private final int doorId = 11778;
	private final int aburyId = 637;
	private final int essenceObjectId = 14912;
	private final int portalId = 14918;
	@Override
	public int onLoop() throws InterruptedException {
		if (essenceArea.contains(myPlayer()))  {
			if (!inventory.isFull()) {
				if (!myPlayer().isAnimating() && !myPlayer().isMoving()) {
					if (centerEssenceArea.contains(myPlayer())) {
						localWalker.walk(essencePosition);
					} else {
						final RS2Object essenceObject = objects.closest(essenceObjectId);
						if (essenceObject != null) {
							essenceObject.interact("Mine");
						}
					}
				}
			} else {
				final RS2Object portalObject = objects.closest(portalId);
				if (portalObject != null) {
					portalObject.interact("Use");
				}
			}
		} else if (inventory.isEmpty()) {
			if (!auburyArea.contains(myPlayer())) {
				localWalker.walkPath(bankToAubury);
			} else {
				final RS2Object door = objects.closest(doorId);
				if (door.hasAction("Open")) {
					door.interact("Open");
				} else {
					final NPC aubury = npcs.closest(aburyId);
					if (aubury != null) {
						aubury.interact("Teleport");
					}
				}
			}
		} else if (inventory.isFull()){
			if (auburyArea.contains(myPlayer())) {
				final RS2Object door = objects.closest(doorId);
				if (door.hasAction("Open")) {
					door.interact("Open");
				} else {
					localWalker.walkPath(auburyToBank);
					if (!bank.isOpen()) {
						bank.open();
					} else {
						bank.depositAll();
						bank.close();
					}
				}
			}
		}
		return random(83, 167);
	}

}
