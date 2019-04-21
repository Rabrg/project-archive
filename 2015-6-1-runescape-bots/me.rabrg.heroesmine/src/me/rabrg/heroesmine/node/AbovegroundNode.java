package me.rabrg.heroesmine.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;

public final class AbovegroundNode extends Node {

	private static final Tile LADDER_TILE = new Tile(2892, 3507);

	public AbovegroundNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ABOVEGROUND_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		if (ctx.getGameObjects().getTopObjectOnTile(LADDER_TILE) != null && ctx.getGameObjects().getTopObjectOnTile(LADDER_TILE).distance() < 6 && ctx.getGameObjects().getTopObjectOnTile(LADDER_TILE).interact("Climb-down")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !ABOVEGROUND_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(2400, 4800));
		} else if (ctx.getWalking().walk(LADDER_TILE)) {
			return Calculations.random(600, 1200);
		}
		return Calculations.random(300);
	}

	@Override
	public String getName() {
		return "Walking aboveground";
	}

}
