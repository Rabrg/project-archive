package me.rabrg.attacker.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.wrappers.items.Item;

public final class EatNode extends Node {

	private int nextHeal = 10;

	public EatNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) <= nextHeal;
	}

	@Override
	public int execute() {
		final Item food = ctx.getInventory().get(new Filter<Item>() {
			@Override
			public boolean match(final Item item) {
				return item != null && item.getName() != null && (item.getName().toLowerCase().contains("swordfish") || item.getName().toLowerCase().contains("lobster") || item.getName().toLowerCase().contains("monkfish"));
			}
		});
		if (food != null) {
			food.interact("Eat");
			nextHeal = Calculations.random(10, ctx.getSkills().getRealLevel(Skill.HITPOINTS) - 16);
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

