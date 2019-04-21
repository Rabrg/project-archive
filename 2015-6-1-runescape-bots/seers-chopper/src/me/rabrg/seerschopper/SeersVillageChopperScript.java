package me.rabrg.seerschopper;

import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "Rabrg", info = "", logo = "", name = "Seers Village Chopper", version = 0.1)
public final class SeersVillageChopperScript extends Script {

	private static final String MAPLE_TREE_OBJECT_NAME = "Maple tree";
	private static final String CHOP_DOWN_ACTION_NAME = "Chop down";

	private static final String GET_LOGS_MESSAGE = "You get some maple logs.";

	private static final String BIRDS_NEST_ITEN_NAME = "Bird's nests";
	private static final String TAKE_ACTION_NAME = "Take";

	private static final String DISMISS_NAME = "Dismiss";

	private RS2Object currentTree;
	private GroundItem birdsNest;

	private int mappleLogs;

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
		log(getState());
		switch (getState()) {
		case BIRDS_NEST:
			birdsNest.interact(TAKE_ACTION_NAME);
			return random(256, 1024);
		case CHOP:
			currentTree.interact(CHOP_DOWN_ACTION_NAME);
			return random(256, 1024);
		case WAIT:
			break;
		case BANK:
			bank.open();
			bank.depositAll();
			bank.close();
			log(mappleLogs);
			break;
		}
		return random(125, 375);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().equals(GET_LOGS_MESSAGE)) {
			mappleLogs++;
		}
	}

	private State getState() {
		if ((birdsNest = groundItems.closest(BIRDS_NEST_ITEN_NAME)) != null) {
			return State.BIRDS_NEST;
		} else if (!inventory.isFull() && !myPlayer().isMoving() && !myPlayer().isAnimating() && (currentTree = objects.closest(MAPLE_TREE_OBJECT_NAME)) != null && currentTree.exists()) {
			return State.CHOP;
		} else if (!inventory.isFull()) {
			return State.WAIT;
		} else if (inventory.isFull()) {
			return State.BANK;
		}
		return null;
	}

	private static enum State {
		CHOP,
		WAIT,
		BANK,
		BIRDS_NEST
	}
}
