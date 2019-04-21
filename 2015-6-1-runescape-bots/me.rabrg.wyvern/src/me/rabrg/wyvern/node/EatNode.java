package me.rabrg.wyvern.node;

import org.tbot.methods.Bank;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Item;

public final class EatNode extends Node {

	private boolean eatTrain = false;
	
	@Override
	public boolean validate() {
		return Inventory.contains("Monkfish") && (Skills.getCurrentLevel(Skill.Hitpoints) + 15 <= Skills.getRealLevel(Skill.Hitpoints)) && (((Players.getLocal().getHealthPercent() <= 35 && (eatTrain = true)) || (eatTrain && (eatTrain = Players.getLocal().getHealthPercent() <= 80))));
	}

	@Override
	public int execute() {
		final Item item = Inventory.getFirst("Monkfish", "Lobster", "Shark");
		if (item != null) {
			if (Bank.isOpen())
				Bank.close();
			else
				item.interact("Eat");
		} else
			eatTrain = false;
		return Random.nextInt(250,  600);
	}

	@Override
	public int priority() {
		return 8;
	}

	@Override
	public String getName() {
		return "Eating";
	}

}
