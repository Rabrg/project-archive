package me.rabrg.coal;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(name = "CoalTruckMiner", author = "Rabrg", version = 1.0, info = "", logo = "")
public final class CoalTruckMinerScript extends Script {

	private final Area miningArea = new Area(2571, 3469, 2596, 3492);
	private final int[] coalRocksId = new int[] { 13453, 13454 };
	private final int coalTruckId = 7453;
	private final int coalId = 453;
	private final int logId = 23274;
	private final Position[] mineToLogPath = new Position[] { new Position(2584, 2487, 0), new Position(2592, 3480, 0), new Position(2597, 3478, 0)  };
	private final Position[] logToBankPath = new Position[] {  new Position (2612, 3470, 0),  new Position (2623, 3468, 0),  new Position (2631, 3463, 0),  new Position (2640, 3461, 0),  new Position (2650, 3460, 0),  new Position (2662, 3459, 0),  new Position (2672, 3465, 0),  new Position (2679, 3475, 0),  new Position (2688, 3483, 0),  new Position (2700, 3483, 0),  new Position (2712, 3485, 0),  new Position (2722, 3485, 0),  new Position (2725, 3492, 0) };
	private final Position[] bankToCart = new Position[] { new Position(2717, 3498, 0), new Position(2707, 3500, 0), new Position(2696, 3506, 0)  };
	private final Position[] cartToBank = new Position[] {  };
	private boolean mineCartFull = false;

	private int currentCountDeposited = 0;

	@Override
	public int onLoop() throws InterruptedException {
		if (myPlayer().isUnderAttack()) {
			return random(750, 2250);
		}
		if (mineCartFull) {
			localWalker.walkPath(mineToLogPath);
			objects.closest(logId).interact("Walk-across");
			sleep(random(4000, 5000));
			localWalker.walkPath(logToBankPath);
			bank();
			while (currentCountDeposited <= 120) {
				localWalker.walkPath(bankToCart);
				final RS2Object coalTruck = objects.closest(miningArea, coalTruckId);
				if (coalTruck != null) {
					coalTruck.interact("Remove-coal");
				}
				if (inventory.isFull()) {
					localWalker.walkPath(cartToBank);
					bank();
					currentCountDeposited += 28;
				}
			}
			mineCartFull = false;
		}
		if (!inventory.isFull() && miningArea.contains(myPlayer()) && !myPlayer().isAnimating() && !myPlayer().isMoving()) {
			final RS2Object coalRocks = objects.closest(miningArea, coalRocksId);
			if (coalRocks != null) {
				coalRocks.interact("Mine");
			} else {
				log("Couldn't find rocks");
				stop(false);
			}
		} else if (inventory.isFull() && miningArea.contains(myPlayer())) {
			final RS2Object coalTruck = objects.closest(miningArea, coalTruckId);
			if (coalTruck != null) {
				localWalker.walk(coalTruck.getPosition());
				inventory.getItem(coalId).interact("Use");
				coalTruck.interact("Use");
			} else {
				log("Couldn't find coal truck");
				stop(false);
			}
		}
		return random(350, 1250);
	}

	private void bank() throws InterruptedException {
		bank.open();
		bank.depositAll();
		bank.close();
	}
	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().equals("The coal truck is full.")) {
			mineCartFull = true;
		}
	}
}
