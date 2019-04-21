package me.rabrg.agility.varrock;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class RoughWallNode extends VarrockNode {

	private static final Tile ROUGH_WALL_TILE = new Tile(3223, 3414);

	public RoughWallNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ROUGH_WALL_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		final GameObject roughWall = ctx.getGameObjects().closest("Rough wall");
		if (roughWall == null || roughWall.distance() > 10) {
			ctx.getWalking().walk(ROUGH_WALL_TILE.getRandomizedTile(2));
			return Calculations.random(700, 1900);
		} else if (!roughWall.isOnScreen()) {
			ctx.getCamera().rotateToEntity(roughWall);
		} else if (roughWall.interactForceRight("Climb")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !ROUGH_WALL_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Rough wall";
	}

}
