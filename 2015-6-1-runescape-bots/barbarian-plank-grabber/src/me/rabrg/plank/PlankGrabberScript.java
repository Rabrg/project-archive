package me.rabrg.plank;

import java.awt.Graphics2D;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(name = "Plank Grabber", author = "Rabrg", version = 1.0, info = "", logo = "")
public final class PlankGrabberScript extends Script {

	private static final Area PLANK_AREA = new Area(2548, 3571, 2558, 3578);
	private static final Area DOOR_AREA = new Area(2519, 3568, 2524, 3574);
	private static final Area DEPOSIT_BOX_AREA = new Area(2530, 3560, 2537, 3579);

	private static final Position[] PLANK_AREA_TO_DOOR = new Position[] { new Position(2543, 3575, 0), new Position(2531, 3581, 0), new Position(2520, 3572, 0)  };
	private static final Position[] DOOR_TO_PLANK_AREA = new Position[] { new Position(2530, 3581, 0), new Position(2542, 3577, 0), new Position(2550, 3575, 0)  };
	private static final Position DEPOSIT_BOX_POSITION = new Position(2535, 3574, 0);
	private static final Position DOOR_INSIDE_POSITION = new Position(2523, 3571, 0);

	private static final int[] DOOR_IDS = new int[] { 20196, 20195 };
	private static final int PLANK_ID = 960;
	private static final int DEPOSIT_BOX_ID = 20228;
	private static final int PLANK_PRICE = 150;

	private GroundItem currentPlank;
	private RS2Object door;

	private static final int START_WORLD = 365;
	private static final int STOP_WORLD = 370;

	private int world = START_WORLD;

	private final long timeBegan = System.currentTimeMillis();
	private long timeRan;

	private int seconds;
	private int minutes;
	private int hours;

	private int planks;

	@Override
	public int onLoop() throws InterruptedException {
		log("Loop");
		switch(getState()) {
		case TAKE_PLANK:
			log("Take plank");
			currentPlank.interact("Take");
			break;
		case HOP_WORLD:
			log("Hop world");
			world++;
			if (world > STOP_WORLD) {
				world = START_WORLD;
			}
			worldHopper.hop(world);
			break;
		case WALK_TO_DOOR_FROM_PLANKS:
			log("Walk to door from planks");
			localWalker.walkPath(PLANK_AREA_TO_DOOR);
			break;
		case OPEN_DOOR:
			log("Open door");
			door.interact("Open");
			break;
		case WALK_TO_DEPOSIT_BOX:
			log("Walk to deposit box");
			localWalker.walk(DEPOSIT_BOX_POSITION);
			planks += 28;
			break;
		case USE_DEPOSIT_BOX:
			log("Use deposit box");
			depositBox.open();
			depositBox.depositAll();
			depositBox.close();
			break;
		case WALK_TO_DOOR_FROM_DEPOSIT_BOX:
			log("Walk to door from deposit box");
			localWalker.walk(DOOR_INSIDE_POSITION);
			break;
		case WALK_TO_PLANKS:
			log("Walk to planks");
			localWalker.walkPath(DOOR_TO_PLANK_AREA);
			break;
		}
		return random(425, 775);
	}

	@Override
	public void onPaint(final Graphics2D g) {
		timeRan = System.currentTimeMillis() - timeBegan;
		seconds = (int) (timeRan / 1000) % 60 ;
		minutes = (int) ((timeRan / (1000*60)) % 60);
		hours   = (int) ((timeRan / (1000*60*60)) % 24);
		g.drawString("Time ran: " + hours + ':' + minutes + ':' + seconds, 5, 300);
		g.drawString("Planks banked: " + planks, 5, 315);
		g.drawString("Money made: " + planks * PLANK_PRICE, 5, 330);
	}

	private State getState() {
		if (PLANK_AREA.contains(myPlayer()) && !inventory.isFull() && (currentPlank = groundItems.closest(PLANK_AREA, PLANK_ID)) != null) {
			return State.TAKE_PLANK;
		} else if (PLANK_AREA.contains(myPlayer()) && !inventory.isFull() && currentPlank == null) {
			return State.HOP_WORLD;
		} else if (PLANK_AREA.contains(myPlayer()) && inventory.isFull()) {
			return State.WALK_TO_DOOR_FROM_PLANKS;
		} else if (DOOR_AREA.contains(myPlayer()) && (door = objects.closest(DOOR_IDS)) != null && door.hasAction("Open")) {
			return State.OPEN_DOOR;
		} else if (DOOR_AREA.contains(myPlayer()) && inventory.isFull() && door == null) {
			return State.WALK_TO_DEPOSIT_BOX;
		} else if (DEPOSIT_BOX_AREA.contains(myPlayer()) && inventory.isFull() && objects.closest(DEPOSIT_BOX_ID) != null) {
			return State.USE_DEPOSIT_BOX;
		} else if (DEPOSIT_BOX_AREA.contains(myPlayer()) && inventory.isEmpty()) {
			return State.WALK_TO_DOOR_FROM_DEPOSIT_BOX;
		} else if (inventory.isEmpty()) {
			return State.WALK_TO_PLANKS;
		}
		log("Null");
		return null;
	}

	private static enum State {
		TAKE_PLANK,
		HOP_WORLD,
		WALK_TO_DOOR_FROM_PLANKS,
		OPEN_DOOR,
		WALK_TO_DEPOSIT_BOX,
		USE_DEPOSIT_BOX,
		WALK_TO_DOOR_FROM_DEPOSIT_BOX,
		WALK_TO_PLANKS
	}

}
