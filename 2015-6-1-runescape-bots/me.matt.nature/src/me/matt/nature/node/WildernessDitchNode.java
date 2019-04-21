package me.matt.nature.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class WildernessDitchNode extends Node {

	private Tile wildernessDitchTile = getRandomWildernessDitchTile();
	private GameObject wildernessDitch;

	public WildernessDitchNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() { // glory
		if ((!smallFilled && usingSmall) || (!mediumFilled && usingMedium) || (!largeFilled && usingLarge) || (!giantFilled && usingGiant)) {
			return false;
		}
		return EDGEVILLE_AREA.contains(ctx.getLocalPlayer()) && ctx.getInventory().isFull() && (wildernessDitch = ctx.getGameObjects().getTopObjectOnTile(wildernessDitchTile)) != null;
	}

	@Override
	public int execute() {
		if (wildernessDitch.distance() > 7) {
			ctx.getWalking().walk(wildernessDitch);
			return Calculations.random(600, 1800);
		} else if (wildernessDitch.interact("Cross")) {
			wildernessDitchTile = getRandomWildernessDitchTile();
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !EDGEVILLE_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(6400, 7200));
		} else if (!wildernessDitch.isOnScreen()) {
			ctx.getCamera().rotateToEntity(wildernessDitch);
			ctx.getCamera().rotateToPitch(Calculations.random(190, 383));
			return Calculations.random(150, 300);
		}
		return Calculations.random(25, 125);
	}

	@Override
	public String getName() {
		return "wilderness ditch";
	}

	private Tile getRandomWildernessDitchTile() {
		return new Tile(3084 + Calculations.random(22), 3521 + Calculations.random(1));
	}
}
