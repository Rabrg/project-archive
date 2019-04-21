package me.rabrg.pickpocket.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.wrappers.items.Item;

public final class EatNode extends Node {

	static int nextEat = Calculations.random(40, 61);

	private Item food;

	public EatNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) <= nextEat && (food = ctx.getInventory().get("Lobster")) != null;
	}

	@Override
	public int execute() {
		if (food.interact("Eat"))
			 nextEat = Calculations.random(25, 55);
		return Calculations.random(300, 1200);
	}

	@Override
	public String getName() {
		return "Eating";
	}

}
