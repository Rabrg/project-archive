package me.rabrg.furnance;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(name = "Furnace", author = "Rabrg", version = 0.1, info = "", logo = "")
public final class FurnaceScript extends Script {

	private static final Area BANKING_AREA = new Area(3269, 3161, 3272, 3173);
	private static final Area FURNACE_AREA = new Area(3272, 3184, 3279, 3188);

	private static final Position[] BANK_TO_FURNACE_PATH = new Position[] { new Position(3276, 3173, 0), new Position(3275, 3185, 0) };
	private static final Position[] FURNACE_TO_BANK_PATH = new Position[] { new Position(3277, 3175, 0), new Position(3270, 3167, 0) };

	private static final int FURNACE_OBJECT_ID = 24009;
	private static final String FURNACE_OBJECT_INTERACTION_NAME = "Smelt";

	private static final int FURNACE_PARENT_INTERFACE = 311;
	private static final int FURNACE_CHILD_STEEL_INTERFACE = 8;
	private static final String FURNACE_INTERFACE_INTERACTION_NAME = "Smelt 10 Steel";

	private static final String IRON_ORE_NAME = "Iron ore";
	private static final String COAL_NAME = "Coal";

	private static final int IRON_ORE_WITHDRAW_AMOUNT = 9;
	private static final int COAL_ORE_WITHDRAW_AMOUNT = 18;

	@Override
	public int onLoop() throws InterruptedException {
		switch(getCurrentState()) {
		case RUN_TO_FURNACE:
			localWalker.walkPath(BANK_TO_FURNACE_PATH);
			break;
		case USE_FURNACE:
			objects.closest(FURNACE_AREA, FURNACE_OBJECT_ID).interact(FURNACE_OBJECT_INTERACTION_NAME);
			sleep(random(550, 750));
			interfaces.getChild(FURNACE_PARENT_INTERFACE, FURNACE_CHILD_STEEL_INTERFACE).interact(FURNACE_INTERFACE_INTERACTION_NAME);
			sleep(random(27000, 28000));
			break;
		case WAIT:
			break;
		case RUN_TO_BANK:
			localWalker.walkPath(FURNACE_TO_BANK_PATH);
			break;
		case USE_BANK:
			bank.open();
			bank.depositAll();
			bank.withdraw(IRON_ORE_NAME, IRON_ORE_WITHDRAW_AMOUNT);
			sleep(random(275, 325));
			bank.withdraw(COAL_NAME, COAL_ORE_WITHDRAW_AMOUNT);
			bank.close();
			break;
		}
		return random(175, 325);
	}

	private State getCurrentState() {
		if (BANKING_AREA.contains(myPlayer()) && !inventory.contains(IRON_ORE_NAME)) {
			return State.USE_BANK;
		} else if (!FURNACE_AREA.contains(myPlayer()) && inventory.contains(IRON_ORE_NAME)) {
			return State.RUN_TO_FURNACE;
		} else if (FURNACE_AREA.contains(myPlayer()) && !myPlayer().isAnimating() && inventory.contains(IRON_ORE_NAME)) {
			return State.USE_FURNACE;
		} else if (FURNACE_AREA.contains(myPlayer()) && myPlayer().isAnimating()){
			return State.WAIT;
		} else if (!inventory.contains(IRON_ORE_NAME) && !BANKING_AREA.contains(myPlayer())) {
			return State.RUN_TO_BANK;
		}
		return null;
	}

	private static enum State {
		RUN_TO_FURNACE,
		USE_FURNACE,
		WAIT,
		RUN_TO_BANK,
		USE_BANK
	}
}
