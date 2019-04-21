package me.rabrg.motherlode;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(author = "Rabrg", info = "", logo = "", name = "Motherlode Miner", version = 0.6)
public final class MotherlodeMineScript extends Script {

	private static final Area MINING_AREA = new Area(3729, 5687, 3744, 5694);
	private static final Area ROCKFALL_AREA = new Area(3728, 5677, 3733, 5686);
	private static final Area CENTER_AREA = new Area(3734, 5652, 3760, 5680);

	private static final Position[] BANK_TO_MINE = new Position[] { new Position(3750, 5673, 0), new Position(3734, 5680, 0) };

	private static final Position ROCKFALL_POSITION_MINE = new Position(3730, 5684, 0);
	private static final Position CENTER_OF_MINE = new Position(3735, 5689, 0);
	private static final Position CENTER = new Position(3739, 5675, 0);
	private static final Position BANK_POSITION = new Position(3759, 5666, 0);

	private static final String ORE_VEIN = "Ore vein";
	private static final String ROCKFALL = "Rockfall";
	private static final String MINE = "Mine";

	private static final String HOPPER = "Hopper";
	private static final String DEPOSIT = "Deposit";
	private static final String PAY_DIRT = "Pay-dirt";

	private static final String SACK = "Sack";
	private static final String SEARCH = "Search";

	private RS2Object currentOreVein;
	private RS2Object currentRockfall;
	private RS2Object sack;
	private RS2Object currentBrokenStrut;

	@Override
	public int onLoop() throws InterruptedException {
		for (final NPC npc : npcs.getAll()) {
			if (npc != null) {
				final String text = npc.getHeadMessage();
				if (text != null && text.contains("Cyberus")) {
					sleep(random(450, 750));
					if (!npc.interact("Dismiss")) {
						stop();
					}
					sleep(random(750, 1450));
				}
			}
		}
		State currentState = getCurrentState();
		log(currentState);
		switch (currentState) {
		case TOGGLE_RUN:
			settings.setRunning(true);
			return random(275, 425);
		case FIND_NEW_ORE_VEIN:
			currentOreVein = objects.closest(MINING_AREA, ORE_VEIN);
			if (currentOreVein != null) {
				currentOreVein.interact(MINE);
			}
			break;
		case MINING:
			// XXX
			break;
		case WALK_TO_FALLENROCK:
			localWalker.walk(ROCKFALL_POSITION_MINE);
			break;
		case MINE_ROCKFALLS:
			currentRockfall = objects.closest(ROCKFALL_AREA, ROCKFALL);
			if (currentRockfall == null) {
				localWalker.walk(inventory.isFull() ? CENTER : CENTER_OF_MINE);
			} else {
				currentRockfall.interact(MINE);
				return random(425, 729);
			}
			break;
		case HOPPER:
			objects.closest(CENTER_AREA, HOPPER).interact(DEPOSIT);
			return random(1250, 2125);
		case GRAB_HAMMER:
			objects.closest("Crate").interact("Search");
			return random(425, 729);
		case REPAIR:
			currentBrokenStrut.interact("Hammer");
			return random(2125, 4176);
		case DROP_HAMMER:
			inventory.drop("Hammer");
			break;
		case SACK:
			sack.interact(SEARCH);
			return random(1024, 2048);
		case BANK:
			localWalker.walk(BANK_POSITION);
			getBank().open();
			getBank().depositAll();
			getBank().close();
			break;
		case BACK_TO_MINE:
			currentRockfall = objects.closest(ROCKFALL_AREA, ROCKFALL);
			if (currentRockfall == null) {
				localWalker.walkPath(BANK_TO_MINE);
			} else {
				currentRockfall.interact(MINE);
			}
			break;
		}
		return random(125, 275);
	}

	private State getCurrentState() {
		if (settings.getRunEnergy() > 50 && !settings.isRunning()) {
			return State.TOGGLE_RUN;
		} else if (CENTER_AREA.contains(myPlayer()) && inventory.isEmpty() && (sack = objects.closest(CENTER_AREA, SACK)) != null) {
			return State.SACK;
		} else if (MINING_AREA.contains(myPlayer()) && !inventory.isFull() && (currentOreVein == null  || !currentOreVein.exists())) {
			return State.FIND_NEW_ORE_VEIN;
		} else if (MINING_AREA.contains(myPlayer()) && !inventory.isFull() && (currentOreVein != null   && currentOreVein.exists())) {
			return State.MINING;
		} else if (MINING_AREA.contains(myPlayer()) && inventory.isFull()) {
			return State.WALK_TO_FALLENROCK;
		} else if (ROCKFALL_AREA.contains(myPlayer()) && (inventory.isFull() || inventory.isEmpty())) {
			return State.MINE_ROCKFALLS;
		} else if (CENTER_AREA.contains(myPlayer()) && inventory.contains(PAY_DIRT)) {
			return State.HOPPER;
		} else if (CENTER_AREA.contains(myPlayer()) && !inventory.contains(PAY_DIRT) && (currentBrokenStrut = objects.closest(CENTER_AREA, "Broken strut")) != null && !inventory.contains("Hammer")) {
			return State.GRAB_HAMMER;
		} else if (CENTER_AREA.contains(myPlayer()) && !inventory.contains(PAY_DIRT) && (currentBrokenStrut = objects.closest(CENTER_AREA, "Broken strut")) != null && inventory.contains("Hammer")) {
			return State.REPAIR;
		} else if (CENTER_AREA.contains(myPlayer()) && !inventory.contains(PAY_DIRT) && (currentBrokenStrut = objects.closest(CENTER_AREA, "Broken strut")) == null && inventory.contains("Hammer")) {
			return State.DROP_HAMMER;
		} else if (CENTER_AREA.contains(myPlayer()) && !inventory.contains(PAY_DIRT) && !inventory.isEmpty()) {
			return State.BANK;
		} else if (CENTER_AREA.contains(myPlayer()) && !inventory.contains(PAY_DIRT) && inventory.isEmpty()) {
			return State.BACK_TO_MINE;
		}
		return null;
	}

	private enum State {
		TOGGLE_RUN,
		FIND_NEW_ORE_VEIN,
		MINING,
		WALK_TO_FALLENROCK,
		MINE_ROCKFALLS,
		HOPPER,
		GRAB_HAMMER,
		REPAIR,
		DROP_HAMMER,
		SACK,
		BANK,
		BACK_TO_MINE;
	}
}