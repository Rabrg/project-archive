package me.rabrg.thief;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(name = "FarmThiever", author = "Rabrg", version = 1.0, info = "", logo = "")
public final class FarmThiefScript extends Script {

	private final Position preBankPosition = new Position(3080, 3250, 0);
	private final Position bankPosition = new Position(3093, 3244, 0);

	private final int masterFarmer = 5832;

	private final int foodId = 379;
	private final int foodAmount = 7;
	private final int healthMin = 50;

	@Override
	public int onLoop() throws InterruptedException {
		if (inventory.isFull() || !inventory.contains(foodId)) {
			localWalker.walk(randomizePosition(preBankPosition));
			localWalker.walk(randomizePosition(bankPosition));
			bank.open();
			bank.depositAll();
			bank.withdraw(foodId, foodAmount);
			localWalker.walk(randomizePosition(preBankPosition));
		} else if (skills.getDynamic(Skill.HITPOINTS) < healthMin) {
			inventory.interact("Eat", foodId);
		} else {
			final NPC npc = npcs.closest(masterFarmer);
			if (npc != null) {
				npc.interact("Pickpocket");
			}
		}
		return random(125, 270);
	}

	private Position randomizePosition(final Position position) {
		final Position position_ = new Position(position.getX() + random(-1, 1), position.getY() + random(-1, 1), 0);
		log(position_);
		return position_;
	}
}
