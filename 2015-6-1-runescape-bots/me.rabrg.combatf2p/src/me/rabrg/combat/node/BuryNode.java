package me.rabrg.combat.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.wrappers.items.Item;

public final class BuryNode extends Node {

	private static final String[] BURRY_BONES = { "Babydragon bones", "Bones", "Big bones" };

	private static final Filter<Item> boneFilter = new Filter<Item>() {
		@Override
		public boolean match(Item item) {
			for (final String string : BURRY_BONES) {
				if (item != null && item.getName() != null && item.getName().equals(string)) {
					return true;
				}
			}
			return false;
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
