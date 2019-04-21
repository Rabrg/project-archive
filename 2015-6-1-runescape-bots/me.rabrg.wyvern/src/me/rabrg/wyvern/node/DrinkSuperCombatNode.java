package me.rabrg.wyvern.node;

import static me.rabrg.wyvern.WyvernScript.*;

import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.tabs.Inventory;

public final class DrinkSuperCombatNode extends Node {

	@Override
	public boolean validate() {
		return Players.getLocal().getInteractingEntity() != null && (Inventory.containsOneOf("Super combat potion(4)") || Inventory.containsOneOf("Super combat potion(3)") || Inventory.containsOneOf("Super combat potion(2)") || Inventory.containsOneOf("Super combat potion(1)")) && Skills.getCurrentLevel(Skill.Attack) - Skills.getRealLevel(Skill.Attack) <= 6 && WYVERN_AREA.contains(Players.getLocal());
	}

	@Override
	public int execute() {
		Inventory.getFirst("Super combat potion(4)", "Super combat potion(3)", "Super combat potion(2)", "Super combat potion(1)").interact("Drink");
		return Random.nextInt(600, 1250);
	}

	@Override
	public int priority() {
		return 7;
	}

	@Override
	public String getName() {
		return "Drinking super combat";
	}

}
