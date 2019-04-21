package me.rabrg.robe;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(name = "Monk's Robe Grabber", author = "Rabrg", version = 1.3, info = "Grabs monk's robe from the monastery next to Edgeville.", logo = "http://i.imgur.com/6YiQNAf.png")
public final class MonkRobeScript extends Script {

	private static final Position[] BANK_TO_MONASTERY_PATH = new Position[] { new Position(3084, 3504, 0), new Position(3073, 3511, 0), new Position(3059, 3514, 0), new Position(3054, 3501, 0), new Position(3054, 3491, 0), new Position(3058, 3490, 0), new Position(3058, 3484, 0) };
	private static final Position[] MONESTARY_TO_BANK_PATH = new Position[] { new Position(3052, 3496, 0), new Position(3052, 3508, 0), new Position(3061, 3517, 0), new Position(3072, 3512, 0), new Position(3083, 3505, 0), new Position(3096, 3496, 0) };

	private static final Area MONASTERY_LADDER_AREA_LOWER = new Area(3053, 3482, 3059, 3490);
	private static final Area MONASTERY_LADDER_AREA_UPPER = new Area(3054, 3482, 3059, 3485);
	private static final Area BANK_AREA = new Area(3091, 3488, 3098, 3499);

	private static final Position MONK_ROBE_POSITION = new Position(3058, 3487, 1);

	private static final String LADDER_OBJECT_NAME = "Ladder";
	private static final String LADDER_INTERACTION_UP_NAME = "Climb-up";
	private static final String LADDER_INTERACTION_DOWN_NAME = "Climb-down";
	private static final String DOOR_OBJECT_NAME = "Door";
	private static final String DOOR_INTERACTION_NAME = "Open";
	private static final String MONK_ROBES_ITEM_NAME = "Monk's robe";
	private static final String MONK_ROBES_ITNERACTION_NAME = "Take";

	private static final int DOWNSTAIRS_HEIGHT = 0;
	private static final int UPSTAIRS_HEIGHT = 1;

	private static final int STARTING_WORLD = 302;
	private static final int ENDING_WORLD = 340;

	private final int MONKS_ROBE_PRICE = 2800;

	private static final List<Integer> INVALID_WORLDS = new ArrayList<Integer>(Arrays.asList(new Integer[] { 307, 315, 323, 324, 325, 331, 332, 337, 339, 340, 347, 348, 363, 364, 371, 372, 379, 380, 385, 386, 387, 388, 389, 390, 391, 392 }));

	private RS2Object door;
	private RS2Object ladder;

	private GroundItem monksRobe;

	private final long timeBegan = System.currentTimeMillis();
	private long timeRan;

	private int seconds;
	private int minutes;
	private int hours;

	private int world = STARTING_WORLD;
	private int monksRobes = 0;

	@Override
	public int onLoop() throws InterruptedException {
		switch (getState()) {
		case BANK_TO_MONASTERY:
			localWalker.walkPath(randomizePath(BANK_TO_MONASTERY_PATH));
			break;
		case CLIMB_UP_LADDER:
			ladder.interact(LADDER_INTERACTION_UP_NAME);
			break;
		case OPEN_DOOR:
			if (door.hasAction(DOOR_INTERACTION_NAME)) {
				door.interact(DOOR_INTERACTION_NAME);
			} else {
				if (!inventory.isFull()) {
					localWalker.walk(MONK_ROBE_POSITION);
				}
			}
			break;
		case TAKE_MONKS_ROBE:
			monksRobe.interact(MONK_ROBES_ITNERACTION_NAME);
			break;
		case HOP_WORLD:
			while (INVALID_WORLDS.contains(++world)) {
				
			}
			if (world > ENDING_WORLD) {
				world = STARTING_WORLD;
			}
			worldHopper.hop(world);
			break;
		case CLIMB_DOWN_LADDER:
			ladder.interact(LADDER_INTERACTION_DOWN_NAME);
			break;
		case BACK_TO_BANK:
			localWalker.walkPath(randomizePath(MONESTARY_TO_BANK_PATH));
			break;
		case BANK:
			bank.open();
			bank.depositAll();
			monksRobes += 28;
			bank.close();
			break;
		}
		return random(350, 700);
	}

	@Override
	public void onPaint(final Graphics2D g) {
		timeRan = System.currentTimeMillis() - timeBegan;
		seconds = (int) (timeRan / 1000) % 60 ;
		minutes = (int) ((timeRan / (1000*60)) % 60);
		hours   = (int) ((timeRan / (1000*60*60)) % 24);
		g.drawString("Time ran: " + hours + ':' + minutes + ':' + seconds, 5, 300);
		g.drawString("Monk's robes banked: " + monksRobes, 5, 315);
		g.drawString("Money made: " + monksRobes * MONKS_ROBE_PRICE, 5, 330);
	}

	private State getState() {
		if (inventory.isEmpty() && !MONASTERY_LADDER_AREA_LOWER.contains(myPlayer()) && myPlayer().getPosition().getZ() == DOWNSTAIRS_HEIGHT) {
			return State.BANK_TO_MONASTERY;
		} else if (inventory.isEmpty() && MONASTERY_LADDER_AREA_LOWER.contains(myPlayer()) && myPlayer().getPosition().getZ() == DOWNSTAIRS_HEIGHT && (ladder = objects.closest(LADDER_OBJECT_NAME)) != null) {
			return State.CLIMB_UP_LADDER;
		} else if (((inventory.isEmpty() && MONASTERY_LADDER_AREA_UPPER.contains(myPlayer())) || inventory.isFull()) && myPlayer().getPosition().getZ() == UPSTAIRS_HEIGHT && (door = objects.closest(DOOR_OBJECT_NAME)) != null && door.hasAction("Open")) {
			return State.OPEN_DOOR;
		} else if (!inventory.isFull() && (monksRobe = groundItems.closest(MONK_ROBES_ITEM_NAME)) != null) {
			return State.TAKE_MONKS_ROBE;
		} else if (!inventory.isFull() && myPlayer().getPosition().getZ() == UPSTAIRS_HEIGHT && groundItems.closest(MONK_ROBES_ITEM_NAME) == null) {
			return State.HOP_WORLD;
		} else if (inventory.isFull() && myPlayer().getPosition().getZ() == UPSTAIRS_HEIGHT && (ladder = objects.closest(LADDER_OBJECT_NAME)) != null) {
			return State.CLIMB_DOWN_LADDER;
		} else if (inventory.isFull() && myPlayer().getPosition().getZ() == DOWNSTAIRS_HEIGHT && !BANK_AREA.contains(myPlayer())) {
			return State.BACK_TO_BANK;
		} else if (inventory.isFull() && BANK_AREA.contains(myPlayer())) {
			return State.BANK;
		}
		return null;
	}

	private static Position[] randomizePath(final Position[] path) {
		final Position[] randomizedPath = new Position[path.length];
		for (int step = 0 ; step < path.length; step++) {
			randomizedPath[step] = new Position(path[step].getX() + random(-1, 1), path[step].getY() + random(-1, 1), path[step].getZ());
		}
		return randomizedPath;
	}
	private static enum State {
		BANK_TO_MONASTERY,
		CLIMB_UP_LADDER,
		OPEN_DOOR,
		TAKE_MONKS_ROBE,
		HOP_WORLD,
		CLIMB_DOWN_LADDER,
		BACK_TO_BANK,
		BANK;
	}
}
