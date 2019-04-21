package me.rabrg.nmz.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

public final class OverloadNode extends Node {

	private Item overload;

	public OverloadNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) > 50 && (overload = ctx.getInventory().get(new Filter<Item>() {
			@Override
			public boolean match(final Item item) {
				return item != null && item.getName() != null && item.getName().startsWith("Overload");
			}
		})) != null;
	}

	@Override
	public int execute() {
		if (!ctx.getTabs().isOpen(Tab.INVENTORY)) {
			ctx.getTabs().open(Tab.INVENTORY);
			MethodProvider.sleep(450, 600);
		}
		if (overload.interact("Drink")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) < 10;
				}
			}, Calculations.random(7500, 10000));
		}
		return Calculations.random(450, 800);
	}

	@Override
	public String getName() {
		return "Drinking overload potion";
	}
}
