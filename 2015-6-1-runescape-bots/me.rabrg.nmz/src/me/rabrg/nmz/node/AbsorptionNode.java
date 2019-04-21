package me.rabrg.nmz.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.wrappers.items.Item;

import me.rabrg.nmz.RabrgNightmareZone;

public final class AbsorptionNode extends Node {

	private final Filter<Item> absorptionFilter = new Filter<Item>() {
		@Override
		public boolean match(final Item item) {
			return item != null && item.getName() != null && item.getName().startsWith("Absorption");
		}
	};

	private int nextAbsorption = Calculations.random(500, 800);

	private Item absorption;

	public AbsorptionNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return RabrgNightmareZone.absorptionHitpoints <= nextAbsorption && (absorption = ctx.getInventory().get(absorptionFilter)) != null;
	}

	@Override
	public int execute() {
		if (!ctx.getTabs().isOpen(Tab.INVENTORY)) {
			ctx.getTabs().open(Tab.INVENTORY);
			MethodProvider.sleep(450, 600);
		}
		nextAbsorption = Calculations.random(500, 800);
		for (int i = 0; i < Calculations.random(8, 20); i++) {
			absorption.interact("Drink");
			if ((absorption = ctx.getInventory().get(absorptionFilter)) == null || RabrgNightmareZone.absorptionHitpoints > 900) {
				break;
			}
			MethodProvider.sleep(575, 825);
		}
		return Calculations.random(450, 800);
	}

	@Override
	public String getName() {
		return "Drink absorption potion";
	}

}
