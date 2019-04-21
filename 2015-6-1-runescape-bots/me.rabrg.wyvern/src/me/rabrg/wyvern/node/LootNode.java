package me.rabrg.wyvern.node;

import org.tbot.methods.Camera;
import org.tbot.methods.GroundItems;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.GroundItem;

import static me.rabrg.wyvern.WyvernScript.*;

public final class LootNode extends Node {

	private GroundItem currentGroundItem;
	
	@Override
	public boolean validate() {
		return (currentGroundItem = GroundItems.getNearest(LOOT)) != null && currentGroundItem.distance() <= 10;
	}

	@Override
	public int execute() {
		Camera.turnTo(currentGroundItem);
		if (Inventory.isFull()) {
			if (Inventory.contains("Vial")) {
				Inventory.getFirst("Vial").interact("Drop");
				return Random.nextInt(425, 825);
			} else if (Inventory.contains("Monkfish") && (Skills.getCurrentLevel(Skill.Hitpoints) + 15 <= Skills.getRealLevel(Skill.Hitpoints))) {
				Inventory.getFirst("Monkfish").interact("Eat");
				return Random.nextInt(425, 825);
			} else if (Inventory.contains("Wyvern bones")) {
				Inventory.getFirst("Wyvern bones").interact("Bury");
				return Random.nextInt(425, 825);
			} else if (Inventory.contains("Monkfish")) {
				Inventory.getFirst("Monkfish").interact("Eat");
				return Random.nextInt(425, 825);
			}
		}
		currentGroundItem.pickUp();
		return Random.nextInt(475, 725);
	}

	@Override
	public int priority() {
		return 7;
	}

	@Override
	public String getName() {
		return "Looting";
	}

}
