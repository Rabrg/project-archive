package me.rabrg.moss.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.wrappers.items.Item;

public final class EatNode extends Node {

	public EatNode(final MethodContext ctx) {
		super(ctx);
	}

	public static int nextHeal = -1;

	@Override
	public boolean validate() {
		return ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) <= nextHeal;
	}

	@Override
	public int execute() {
		final Item food = ctx.getInventory().get("Lobster");
		if (food != null) {
			food.interact("Eat");
			nextHeal = Calculations.random(15, (ctx.getSkills().getRealLevel(Skill.HITPOINTS) - 12));
		} else {
			ctx.getTabs().logout();
		}
		return Calculations.random(900, 1200);
	}

	@Override
	public String getName() {
		return "Eating food";
	}

}

