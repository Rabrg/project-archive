package me.rabrg.shrimp.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.NPC;

public final class WalkFishNode extends Node {

	private static final Tile FISHING_SPOT_TILE = new Tile(3273, 3147);

	private NPC fishingSpot;

	public WalkFishNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getInventory().isFull() && ((fishingSpot = ctx.getNpcs().closest(FISHING_SPOT_FILTER)) == null || fishingSpot.distance() > 10);
	}

	@Override
	public int execute() {
		if (fishingSpot == null) {
			ctx.getWalking().walk(FISHING_SPOT_TILE);
		} else {
			ctx.getWalking().walk(fishingSpot);
		}
		return Calculations.random(1000, 2400);
	}

	@Override
	public String getName() {
		return "Walking to fishing spot";
	}

}
