package me.rabrg.fisher;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(name = "Lobster fisher", author = "Rabrg", version = 0.1, info = "", logo = "")
public final class LobsterFisherScript extends Script {

	private static final Position[] FISHING_SPOT_TO_BANK_PATH = new Position[] { new Position(2848, 3433, 0), new Position(2837, 3435, 0), new Position(2825, 3437, 0), new Position(2813, 3435, 0), new Position(2808, 3440, 0) };
	private static final Position[] BANK_TO_FISHING_SPOT_PATH = new Position[] { new Position(2820, 3439, 0), new Position(2833, 3438, 0), new Position(2845, 3434, 0), new Position(2851, 3430, 0) };

	private static final String FISHING_SPOT_NPC_NAME = "Fishing spot";
	private static final String CAGE_ACTION_NAME = "Cage";

	private static final String LOBSTER_POT_ITEM_NAME = "Lobster pot";

	private NPC currentFishingSpot;

	private Filter<NPC> fishingSpotFilter = new Filter<NPC>() {
		@Override
		public boolean match(final NPC npc) {
			return npc.getName().equals(FISHING_SPOT_NPC_NAME) && npc.hasAction(CAGE_ACTION_NAME);
		}
	};

	@Override
	public int onLoop() throws InterruptedException {
		final State state = getState();
		log(state);
		switch(state) {
		case FIND_NEW_FISHING_SPOT:
			currentFishingSpot.interact(CAGE_ACTION_NAME);
			return random(325, 875);
		case FISHING:
			return random(100, 350);
		case BANK:
			bank.depositAllExcept(LOBSTER_POT_ITEM_NAME);
			bank.close();
			break;
		case WALK_TO_BANK:
			localWalker.walkPath(FISHING_SPOT_TO_BANK_PATH);
			break;
		case WALK_TO_FISHING_SPOT:
			localWalker.walkPath(BANK_TO_FISHING_SPOT_PATH);
			break;
		}
		return random(50, 175);
	}

	@SuppressWarnings("unchecked")
	private State getState() throws InterruptedException {
		if (!inventory.isFull() && (currentFishingSpot == null || !currentFishingSpot.exists() || !myPlayer().isAnimating()) && (currentFishingSpot = npcs.closest(fishingSpotFilter)) != null) {
			return State.FIND_NEW_FISHING_SPOT;
		} else if (!inventory.isFull() && myPlayer().isAnimating() && currentFishingSpot != null && currentFishingSpot.exists()) {
			return State.FISHING;
		} else if (inventory.isFull() && bank.open()) {
			return State.BANK;
		} else if (inventory.isFull()) {
			return State.WALK_TO_BANK;
		} else {
			return State.WALK_TO_FISHING_SPOT;
		}
	}

	private static enum State {
		FIND_NEW_FISHING_SPOT,
		FISHING,
		BANK,
		WALK_TO_BANK,
		WALK_TO_FISHING_SPOT
	}

	public static Position[] randomizePath(final Position[] path) {
		final Position[] randomizedPath = new Position[path.length];
		for (int i = 0; i < path.length; i++) {
			randomizedPath[i] = new Position(random(path[i].getX() - 1, path[i].getX() + 1), random(path[i].getY() - 1, path[i].getY() + 1), path[i].getZ());
		}
		return randomizedPath;
	}
}
