package me.rabrg.rat.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.wrappers.items.Item;

public final class BuryNode extends Node {

	private static final Filter<Item> boneFilter = new Filter<Item>() {
		@Override
		public boolean match(Item item) {
			return item != null && item.getName() != null && item.getName().equals(BONES_NAME);
		}
	};

	private Item bones;

	public BuryNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (bones = ctx.getInventory().get(boneFilter)) != null;
	}

	@Override
	public int execute() {
		bones.interact("Bury");
		return Calculations.random(600, 750);
	}

	@Override
	public String getName() {
		return "Burrying bones";
	}

}
