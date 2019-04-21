package me.rabrg.cow.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.wrappers.items.Item;

public final class EatNode extends Node {

	public EatNode(final MethodContext ctx) {
		super(ctx);
	}

	private int nextHeal = Calculations.random(5, 15);

	@Override
	public boolean validate() {
		return ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) <= nextHeal;
	}

	@Override
	public int execute() {
		final Item food = ctx.getInventory().get(new Filter<Item>() {
			@Override
			public boolean match(final Item item) {
				return item != null && item.getName() != null && item.getName().toLowerCase().contains("pizza");
			}
		});
		if (food != null) {
			food.interact("Eat");
			nextHeal = Calculations.random(12, 43);
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

