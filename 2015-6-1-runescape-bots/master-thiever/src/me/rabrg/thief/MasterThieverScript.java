package me.rabrg.thief;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(name = "Master Thiever", author = "Rabrg", version = 0.2, info = "", logo = "")
public final class MasterThieverScript extends Script {

	private static final Position CENTRAL_POSITION = new Position(3080, 3250, 0);

	private static final int MINIMUM_HP = 30;
	private static final int FOOD_AMOUNT = 6;

	private static final String FOOD_ITEM_NAME = "Lobster";
	private static final String EAT_ACTION_NAME = "Eat";

	private static final String[] MASTER_NPC_NAMES = new String[] { "Master Farmer", "Martin the Master Gardener" };
	private static final String PICKPOCKET_ACTION_NAME = "Pickpocket";

	private static final String STUNNED_MESSAGE = "You've been stunned!";

	private NPC currentMaster;

	@Override
	public int onLoop() throws InterruptedException {
		switch(getState()) {
		case WALK_TO_CENTRAL:
			localWalker.walk(randomizePosition(CENTRAL_POSITION, 2));
			break;
		case BANK:
			bank.depositAll();
			bank.withdraw(FOOD_ITEM_NAME, FOOD_AMOUNT);
			bank.close();
			break;
		case EAT:
			inventory.interact(EAT_ACTION_NAME, FOOD_ITEM_NAME);
			return random(2850, 4850);
		case PICKPOCKET:
			currentMaster.interact(PICKPOCKET_ACTION_NAME);
			break;
		}
		return random(75, 250);
	}

	@Override
	public void onMessage(final Message message) throws InterruptedException {
		if (message.getMessage().equals(STUNNED_MESSAGE)) {
			sleep(random(2850, 4850));
		}
	}

	private State getState() throws InterruptedException {
		log(myPlayer().getHealth());
		if ((inventory.isFull() || (!inventory.contains(FOOD_ITEM_NAME) && myPlayer().getHealth() < MINIMUM_HP)) && !bank.open()) {
			return State.WALK_TO_CENTRAL;
		} else if ((inventory.isFull() || (!inventory.contains(FOOD_ITEM_NAME) && myPlayer().getHealth() < MINIMUM_HP)) && bank.open()) {
			return State.BANK;
		} else if (myPlayer().getHealth() < MINIMUM_HP && inventory.contains(FOOD_ITEM_NAME)) {
			return State.EAT;
		} else if (myPlayer().getHealth() >= MINIMUM_HP && (currentMaster = npcs.closest(MASTER_NPC_NAMES)) == null) {
			return State.WALK_TO_CENTRAL;
		} else if (myPlayer().getHealth() >= MINIMUM_HP && currentMaster != null) {
			return State.PICKPOCKET;
		}
		return null;
	}

	private static Position randomizePosition(final Position position, int amount) {
		return new Position(position.getX() + random(-amount, amount), position.getY() + random(-amount, amount), 0);
	}

	private static enum State {
		WALK_TO_CENTRAL,
		BANK,
		EAT,
		PICKPOCKET;
	}
}
