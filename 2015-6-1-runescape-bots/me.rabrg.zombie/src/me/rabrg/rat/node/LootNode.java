package me.rabrg.rat.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.wrappers.items.GroundItem;

public final class LootNode extends Node {

	private static final Filter<GroundItem> boneFilter = new Filter<GroundItem>() {
		@Override
		public boolean match(GroundItem item) {
			return item.getName().equals(BONES_NAME) && RAT_AREA.contains(item);
		}
	};

	private GroundItem item;

	public LootNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getLocalPlayer().isInCombat() && !ctx.getInventory().isFull() && (item = ctx.getGroundItems().closest(boneFilter)) != null;
	}

	@Override
	public int execute() {
		if (item.distance() > 8)
			ctx.getWalking().walk(item.getTile());
		else if (!item.isOnScreen())
			ctx.getCamera().rotateToEntity(item);
		else
			item.interact("Take");
		return Calculations.random(1000, 1800);
	}

	@Override
	public String getName() {
		return "Looting items";
	}

}
