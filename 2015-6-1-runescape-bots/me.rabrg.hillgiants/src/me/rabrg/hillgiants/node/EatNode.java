package me.rabrg.hillgiants.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

public final class EatNode extends Node {

	private int nextEat;

	private Item food;

	public EatNode(final MethodContext ctx) {
		super(ctx);
		nextEat = Calculations.random(15, ctx.getSkills().getRealLevel(Skill.HITPOINTS) - 10);
	}

	@Override
	public boolean validate() {
		return nextEat >= ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) && (food = ctx.getInventory().get(FOOD_FILTER)) != null && !ctx.getBank().isOpen();
	}

	@Override
	public int execute() {
		if (food.interact("Eat")) {
			final int health = ctx.getSkills().getBoostedLevels(Skill.HITPOINTS);
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return health != ctx.getSkills().getBoostedLevels(Skill.HITPOINTS);
				}
			}, Calculations.random(600, 1200));
			nextEat = Calculations.random(15, ctx.getSkills().getRealLevel(Skill.HITPOINTS) - 10);
		}
		return Calculations.random(0, 300);
	}

	@Override
	public String toString() {
		return "Eating";
	}

}
