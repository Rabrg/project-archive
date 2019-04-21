package me.rabrg.fire;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(name = "Fire rune crafter", author = "Rabrg", version = 0.3, info = "", logo = "")
public final class FireRunecraftScript extends Script {

	private static final Position[] BANK_TO_MYSTERIOUS_RUINS_PATH = new Position[] { new Position(3382, 3268, 0), new Position(3370, 3266, 0), new Position(3358, 3264, 0), new Position(3346, 3266, 0), new Position(3334, 3265, 0), new Position(3326, 3263, 0), new Position(3325, 3250, 0), new Position(3320, 3238, 0), new Position(3308, 3238, 0), new Position(3310, 3251, 0) };
	private static final Position[] MYSTERIOUS_RUINS_TO_BANK_PATH = new Position[] { new Position(3310, 3252, 0), new Position(3308, 3239, 0), new Position(3321, 3239, 0), new Position(3325, 3251, 0), new Position(3327, 3264, 0), new Position(3339, 3266, 0), new Position(3350, 3266, 0), new Position(3361, 3264, 0), new Position(3373, 3265, 0), new Position(3383, 3268, 0) };

	private static final String PURE_ESSENCE_ITEM_NAME = "Pure essence";

	private static final String MYSTERIOUS_RUINS_OBJECT_NAME = "Mysterious ruins";
	private static final String ENTER_ACTION_NAME = "Enter";

	private static final String ALTAR_OBJECT_NAME = "Altar";
	private static final String CRAFT_RUNE_ACTION_NAME = "Craft-rune";

	private static final String PORTAL_OBJECT_NAME = "Portal";
	private static final String USE_ACTION_NAME = "Use";

	private static final String FADLI_NPC_NAME = "Fadli";
	private static final String BANK_ACTION_NAME = "Bank";

	private RS2Object mysteriousRuins;
	private RS2Object altar;
	private RS2Object portal;

	private NPC fadli;

	@Override
	public int onLoop() throws InterruptedException {
		final State state = getState();
		log(state);
		switch(state) {
		case OPEN_BANK:
			fadli.interact(BANK_ACTION_NAME);
			return random(350, 875);
		case BANK:
			bank.open();
			bank.depositAll();
			bank.withdrawAll(PURE_ESSENCE_ITEM_NAME);
			bank.close();
			return random(275, 475);
		case CRAFT_RUNES:
			altar.interact(CRAFT_RUNE_ACTION_NAME);
			return random(350, 675);
		case WALK_TO_MYSTERIOUS_RUINS:
			localWalker.walkPath(randomizePath(BANK_TO_MYSTERIOUS_RUINS_PATH));
			break;
		case ENTER_MYSTERIOUS_RUINS:
			mysteriousRuins.interact(ENTER_ACTION_NAME);
			return random(350, 875);
		case USE_PORTAL:
			portal.interact(USE_ACTION_NAME);
			return random(350, 675);
		case WALK_TO_BANK:
			localWalker.walkPath(randomizePath(MYSTERIOUS_RUINS_TO_BANK_PATH));
			break;
		}
		return random(50, 175);
	}

	private State getState() throws InterruptedException {
		if (inventory.isFull() && (altar = objects.closest(ALTAR_OBJECT_NAME)) != null && altar.hasAction(CRAFT_RUNE_ACTION_NAME)) {
			return State.CRAFT_RUNES;
		} else if (inventory.isFull() && (mysteriousRuins = objects.closest(MYSTERIOUS_RUINS_OBJECT_NAME)) == null) {
			return State.WALK_TO_MYSTERIOUS_RUINS;
		} else if (inventory.isFull() && mysteriousRuins != null) {
			return State.ENTER_MYSTERIOUS_RUINS;
		} else if (!inventory.isFull() && (portal = objects.closest(PORTAL_OBJECT_NAME)) != null) {
			return State.USE_PORTAL;
		} else if (!inventory.isFull() && (fadli = npcs.closest(FADLI_NPC_NAME)) != null && !bank.isOpen()) {
			return State.OPEN_BANK;
		} else if (!inventory.isFull() && (fadli = npcs.closest(FADLI_NPC_NAME)) != null && bank.isOpen()) {
			return State.BANK;
		} else if (!inventory.isFull() && !bank.open()) {
			return State.WALK_TO_BANK;
		}
		return null;
	}

	private static enum State {
		OPEN_BANK,
		BANK,
		WALK_TO_MYSTERIOUS_RUINS,
		ENTER_MYSTERIOUS_RUINS,
		CRAFT_RUNES,
		USE_PORTAL,
		WALK_TO_BANK
	}

	private static Position[] randomizePath(final Position[] path) {
		final Position[] randomizedPath = new Position[path.length];
		for (int i = 0; i < path.length; i++) {
			randomizedPath[i] = new Position(random(path[i].getX() - 1, path[i].getX() + 1), random(path[i].getY() - 1, path[i].getY() + 1), path[i].getZ());
		}
		return randomizedPath;
	}
}
