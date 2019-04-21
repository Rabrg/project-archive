package me.rabrg.seerschopper;

import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(author = "Rabrg", info = "", logo = "", name = "Seers Village Regular Chopper", version = 0.1)
public final class SeersVillageRegularChopperScript extends Script {

	private static final Area CUT_AREA = new Area(2717, 3480, 2734, 3506);

	private static final String TREE_OBJECT_NAME = "Tree";
	private static final String CHOP_DOWN_ACTION_NAME = "Chop down";

	private static final String GET_LOGS_MESSAGE = "You get some logs.";

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
		if ((birdsNest = groundItems.closest(CUT_AREA, BIRDS_NEST_ITEN_NAME)) != null) {
			return State.BIRDS_NEST;
		} else if (!inventory.isFull() && !myPlayer().isMoving() && !myPlayer().isAnimating() && (currentTree = objects.closest(CUT_AREA, TREE_OBJECT_NAME)) != null && currentTree.exists()) {
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
