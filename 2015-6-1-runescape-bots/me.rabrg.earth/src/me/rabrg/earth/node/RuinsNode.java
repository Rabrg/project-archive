package me.rabrg.earth.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class RuinsNode extends Node {

	private static final Tile MYSTERIOUS_RUINS_TILE = new Tile(3305, 3472);

	public RuinsNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().isFull() && !ALTAR_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		final GameObject mysteriousRuins = ctx.getGameObjects().closest("Mysterious ruins");
		if (mysteriousRuins == null || mysteriousRuins.distance() > 10) {
			if (mysteriousRuins == null) {
				ctx.getWalking().walk(MYSTERIOUS_RUINS_TILE.getRandomizedTile(5));
			} else {
				ctx.getWalking().walk(mysteriousRuins.getTile().getRandomizedTile(5));
			}
			return Calculations.random(500, 1750);
		} else if (mysteriousRuins.interactForceRight("Enter")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ALTAR_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 250);
	}

}
