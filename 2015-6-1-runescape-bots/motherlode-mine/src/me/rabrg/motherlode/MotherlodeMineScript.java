package me.rabrg.motherlode;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(author = "Rabrg", info = "", logo = "", name = "Motherlode Miner", version = 0.4)
public final class MotherlodeMineScript extends Script {

	private static final Area MINING_AREA = new Area(3725, 5677, 3743, 5693);
	private static final Area CENTER_AREA = new Area(3740, 5645, 3761, 5676);

	private static final Position[] MINE_TO_HOPPER = new Position[] { new Position(3737, 5676, 0), new Position(3748, 5674, 0) };
	private static final Position[] BANK_TO_MINE = new Position[] { new Position(3752, 5672, 0), new Position(3738, 5676, 0), new Position(3735, 5678, 0) };

	private static final String ORE_VEIN_OBJECT_NAME = "Ore vein";
	private static final String ROCKFALL_OBJECT_NAME = "Rockfall";
	private static final String MINE_ACTION_NAME = "Mine";

	private static final String HOPPER_OBJECT_NAME = "Hopper";
	private static final String HOPPER_DEPOSIT_ACTION_NAME = "Deposit";

	private static final String SACK_OBJECT_NAME = "Sack";
	private static final String SACK_SEARCH_ACTION_NAME = "Search";

	private static final String PAY_DIRT_ITEM_NAME = "Pay-dirt";

	private RS2Object currentOreVein;
	private RS2Object currentRockfall;

	private boolean hasRocksInHopper;

	@Override
	public int onLoop() throws InterruptedException {
		for (final NPC npc : npcs.getAll()) {
			if (npc != null && npc.getHeadMessage() != null) {
				final String headMessage = npc.getHeadMessage();
				if (headMessage.contains("Cyberus")) {
					sleep(random(125, 275));
					npc.interact("Dismiss");
					sleep(random(4125, 6715));
				}
			}
		}
		switch(getState()) {
		case MINE_ORE_VEIN:
			currentOreVein = objects.closest(MINING_AREA, ORE_VEIN_OBJECT_NAME);
			currentOreVein.interact(MINE_ACTION_NAME);
			break;
		case WAIT_TO_FINISH_MINING:
			break;
		case MINE_FALLEN_ROCKS:
			currentRockfall.interact(MINE_ACTION_NAME);
			break;
		case WALK_TO_HOPPER:
			localWalker.walkPath(MINE_TO_HOPPER);
			break;
		case USE_HOPPER:
			objects.closest(HOPPER_OBJECT_NAME).interact(HOPPER_DEPOSIT_ACTION_NAME);
			hasRocksInHopper = true;
			break;
		case USE_SACK:
			objects.closest(SACK_OBJECT_NAME).interact(SACK_SEARCH_ACTION_NAME);
			hasRocksInHopper = false;
			break;
		case USE_BANK:
			bank.open();
			bank.depositAll();
			bank.close();
			break;
		case WALK_TO_FALLEN_ROCKS_INVERSE:
			localWalker.walkPath(BANK_TO_MINE);
			break;
		}
		return random(125, 275);
	}

	private State getState() {
		if ((inventory.isFull() || inventory.isEmpty()) && MINING_AREA.contains(myPlayer()) && (currentRockfall = objects.closest(MINING_AREA, ROCKFALL_OBJECT_NAME)) != null) {
			log("mine fallen rocks");
			return State.MINE_FALLEN_ROCKS;
		} else if (!inventory.isFull() && MINING_AREA.contains(myPlayer()) && (currentOreVein == null || !currentOreVein.exists())) {
			log("mine ore vein");
			return State.MINE_ORE_VEIN;
		} else if (!inventory.isFull() && MINING_AREA.contains(myPlayer()) && (currentOreVein != null && currentOreVein.exists())) {
			log("wait to finish mining ore vein");
			return State.WAIT_TO_FINISH_MINING;
		} else if (inventory.isFull() && MINING_AREA.contains(myPlayer()) && (currentRockfall = objects.closest(MINING_AREA, ROCKFALL_OBJECT_NAME)) == null) {
			log("walk to hopper");
			return State.WALK_TO_HOPPER;
		} else if (inventory.contains(PAY_DIRT_ITEM_NAME) && CENTER_AREA.contains(myPlayer())) {
			log("use hopper");
			return State.USE_HOPPER;
		} else if (inventory.isEmpty() && hasRocksInHopper && CENTER_AREA.contains(myPlayer())) {
			log("use sack");
			return State.USE_SACK;
		} else if (!inventory.isEmpty() && !inventory.contains(PAY_DIRT_ITEM_NAME) && CENTER_AREA.contains(myPlayer())) {
			log("use bank");
			return State.USE_BANK;
		} else if (inventory.isEmpty() && !hasRocksInHopper && CENTER_AREA.contains(myPlayer())) {
			log("walk to fallen rocks");
			return State.WALK_TO_FALLEN_ROCKS_INVERSE;
		}
		return null;
	}

	private static enum State {
		MINE_ORE_VEIN,
		WAIT_TO_FINISH_MINING,
		MINE_FALLEN_ROCKS,
		WALK_TO_HOPPER,
		USE_HOPPER,
		USE_SACK,
		USE_BANK,
		WALK_TO_FALLEN_ROCKS_INVERSE
	}
}
