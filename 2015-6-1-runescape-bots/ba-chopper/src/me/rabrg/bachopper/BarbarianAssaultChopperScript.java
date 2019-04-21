package me.rabrg.bachopper;

import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "Rabrg", info = "", logo = "", name = "Barbarian Assault Chopper", version = 0.3)
public final class BarbarianAssaultChopperScript extends Script {

	private static final String WILLOW_OBJECT_NAME = "Willow";
	private static final String CHOP_DOWN_ACTION_NAME = "Chop down";

	private static final String BARBARIAN_DOOR_OBJECT_NAME = "Barbarian door";
	private static final String OPEN_DOOR_ACTION_NAME = "Open";

	private static final String BANK_DEPOSIT_BOX_NAME = "Bank deposit box";
	private static final String DEPOSIT_ACTION_NAME = "Deposit";

	private static final String GET_LOGS_MESSAGE = "You get some willow logs.";

	private static final String BIRDS_NEST_ITEN_NAME = "Bird's nests";
	private static final String TAKE_ACTION_NAME = "Take";

	private static final String DISMISS_NAME = "Dismiss";

	private RS2Object currentTree;
	private RS2Object door;
	private RS2Object depositBox;

	private GroundItem birdsNest;

	private int willowLogs;

	@Override
	public int onLoop() throws InterruptedException {
		for (final NPC npc : npcs.getAll()) {
			if (npc != null) {
				final String text = npc.getHeadMessage();
				if (text != null && text.contains(myPlayer().getName())) {
					sleep(random(450, 750));
					if (!npc.interact(DISMISS_NAME)) {
						stop();
					}
					sleep(random(750, 1450));
				}
			}
		}
		switch (getState()) {
		case BIRDS_NEST:
			birdsNest.interact(TAKE_ACTION_NAME);
			return random(256, 1024);
		case CHOP:
			currentTree.interact(CHOP_DOWN_ACTION_NAME);
			return random(256, 1024);
		case WAIT:
			break;
		case OPEN_DOOR:
			door.interact(OPEN_DOOR_ACTION_NAME);
			return random(256, 1024);
		case BANK:
			depositBox.interact(DEPOSIT_ACTION_NAME);
			getDepositBox().open();
			getDepositBox().depositAll();
			getDepositBox().close();
			log(willowLogs);
			break;
		}
		return random(125, 375);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().equals(GET_LOGS_MESSAGE)) {
			willowLogs++;
		}
	}

	private State getState() {
		if ((birdsNest = groundItems.closest(BIRDS_NEST_ITEN_NAME)) != null) {
			return State.BIRDS_NEST;
		} else if (!inventory.isFull() && !myPlayer().isMoving() && !myPlayer().isAnimating() && (currentTree = objects.closest(WILLOW_OBJECT_NAME)) != null && currentTree.exists()) {
			return State.CHOP;
		} else if (!inventory.isFull()) {
			return State.WAIT;
		} else if ((inventory.isFull() || inventory.isEmpty()) && (door = objects.closest(BARBARIAN_DOOR_OBJECT_NAME)) != null && door.hasAction(OPEN_DOOR_ACTION_NAME)) {
			return State.OPEN_DOOR;
		} else if (inventory.isFull() && (depositBox = objects.closest(BANK_DEPOSIT_BOX_NAME)) != null) {
			return State.BANK;
		}
		return null;
	}

	private static enum State {
		CHOP,
		WAIT,
		OPEN_DOOR,
		BANK,
		BIRDS_NEST
	}
}
