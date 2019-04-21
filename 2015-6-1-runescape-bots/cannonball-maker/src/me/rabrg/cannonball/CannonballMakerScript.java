package me.rabrg.cannonball;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(author = "Rabrg", info = "", logo = "", name = "Cannonball Maker", version = 0.1)
public final class CannonballMakerScript extends Script {

	private static final Area BANK_AREA = new Area(3269, 3161, 3272, 3173);
	private static final Area FURNACE_AREA = new Area(3272, 3185, 3279, 3188);

	private static final Position[] WALK_TO_BANK_PATH = new Position[] { new Position(3276, 3173, 0), new Position(3270, 3167, 0) };
	private static final Position[] WALK_TO_FURNACE_PATH = new Position[] { new Position(3277, 3174, 0), new Position(3275, 3186, 0) };

	private static final String AMMO_MOULD_ITEM_NAME = "Ammo mould";
	private static final String STEEL_BAR_ITEM_NAME = "Steel bar";
	private static final String USE_ACTION_NAME = "Use";

	private static final String CANNONBALL_CRAFT_ACTION_NAME = "Make all";
	private static final String CANNONBALL_CRAFT_MESSAGE = "You heat the steel bar into a liquid state.";

	private static final int FURNACE_OBJECT_ID = 24009;

	private static final int CANNONBALL_CRAFT_PARENT_INTERFACE_ID = 309;
	private static final int CANNONBALL_CRAFT_CHILD_INTERFACE_ID = 5;

	private static final long MAXIMUM_LAST_CANNONBALL_CRAFT_WAIT = 6000;

	private long lastCannonballCraft;

	@Override
	public int onLoop() throws InterruptedException {
		switch(getState()) {
		case WALK_TO_BANK:
			localWalker.walkPath(WALK_TO_BANK_PATH);
			break;
		case BANK:
			bank.open();
			bank.depositAllExcept(AMMO_MOULD_ITEM_NAME);
			bank.withdrawAll(STEEL_BAR_ITEM_NAME);
			bank.close();
			break;
		case WALK_TO_FURNACE:
			localWalker.walkPath(WALK_TO_FURNACE_PATH);
			break;
		case FURNACE:
			inventory.interact(USE_ACTION_NAME, STEEL_BAR_ITEM_NAME);
			sleep(random (125, 275));
			objects.closest(FURNACE_AREA, FURNACE_OBJECT_ID).interact(USE_ACTION_NAME);
			sleep(random (750, 1024));
			interfaces.interactWithChild(CANNONBALL_CRAFT_PARENT_INTERFACE_ID, CANNONBALL_CRAFT_CHILD_INTERFACE_ID, CANNONBALL_CRAFT_ACTION_NAME);
		case WAIT:
			sleep(random (250, 525));
			break;
		}
		return random(125, 275);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().equals(CANNONBALL_CRAFT_MESSAGE)) {
			lastCannonballCraft = System.currentTimeMillis();
		}
	}

	private State getState() {
		if (!inventory.contains(STEEL_BAR_ITEM_NAME) && !BANK_AREA.contains(myPlayer())) {
			return State.WALK_TO_BANK;
		} else if (!inventory.contains(STEEL_BAR_ITEM_NAME) && BANK_AREA.contains(myPlayer())) {
			return State.BANK;
		} else if (inventory.contains(STEEL_BAR_ITEM_NAME) && !FURNACE_AREA.contains(myPlayer())) {
			return State.WALK_TO_FURNACE;
		} else if (inventory.contains(STEEL_BAR_ITEM_NAME) && FURNACE_AREA.contains(myPlayer()) && (System.currentTimeMillis() - lastCannonballCraft) > MAXIMUM_LAST_CANNONBALL_CRAFT_WAIT) {
			return State.FURNACE;
		} else if (inventory.contains(STEEL_BAR_ITEM_NAME) && FURNACE_AREA.contains(myPlayer()) && (System.currentTimeMillis() - lastCannonballCraft) < MAXIMUM_LAST_CANNONBALL_CRAFT_WAIT) {
			return State.WAIT;
		}
		return null;
	}

	private static enum State {
		WALK_TO_BANK,
		BANK,
		WALK_TO_FURNACE,
		FURNACE,
		WAIT
	}
}
