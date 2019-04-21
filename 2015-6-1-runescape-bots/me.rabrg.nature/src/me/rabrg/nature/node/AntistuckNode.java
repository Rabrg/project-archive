package me.rabrg.nature.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public final class AntistuckNode extends Node {

	private static final Area BANK_AREA = new Area(3091, 3488, 3097, 3499);

	private long nextMove = System.currentTimeMillis() + Calculations.random(5000, 10000);
	private Tile lastTile;

	public AntistuckNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return System.currentTimeMillis() >= nextMove && !ctx.getLocalPlayer().isMoving() && ctx.getLocalPlayer().getAnimation() < 1 && !BANK_AREA.contains(ctx.getLocalPlayer()) && !ALTAR_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		if (lastTile == null || (lastTile.getX() != ctx.getLocalPlayer().getTile().getX() || lastTile.getY() != ctx.getLocalPlayer().getTile().getY())) {
			lastTile = ctx.getLocalPlayer().getTile();
			nextMove = System.currentTimeMillis() + Calculations.random(1500, 4500);
			return 10;
		}
		ctx.getCamera().rotateTo(Calculations.random(360), Calculations.random(360));
		for (int xOffset = -4; xOffset < 4; xOffset++) {
			for (int yOffset = -4; yOffset < 4; yOffset++) {
				final Tile tile = new Tile(ctx.getLocalPlayer().getTile().getX() + xOffset, ctx.getLocalPlayer().getTile().getY() + yOffset);
				if (ctx.getMap().canReach(tile) && (xOffset != 0 && yOffset != 0)) {
					ctx.getWalking().walkExact(tile);
					nextMove = System.currentTimeMillis() + Calculations.random(1500, 4500);
					return Calculations.random(950, 1200);
				}
			}
		}
		nextMove = System.currentTimeMillis() + Calculations.random(1500, 4500);
		return 0;
	}

	@Override
	public String getName() {
		return "antistuck";
	}

}
