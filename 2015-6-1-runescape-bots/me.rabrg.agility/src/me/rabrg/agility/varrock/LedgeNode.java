package me.rabrg.agility.varrock;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class LedgeNode extends VarrockNode {

	private GameObject ledge;

	public LedgeNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return LEDGE_AREA.contains(ctx.getLocalPlayer()) && (ledge = ctx.getGameObjects().closest("Ledge")) != null;
	}

	@Override
	public int execute() {
		if (ledge.distance() > 3) {
			MethodProvider.log("1");
			ctx.getWalking().walk(new Tile(3236, 3408, 3).getRandomizedTile(1));
		} else if (!ledge.isOnScreen()) {
			MethodProvider.log("2");
			ctx.getCamera().rotateToEntity(ledge);
		} else if (ledge.interact("Hurdle")) {
			MethodProvider.log("3");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !LEDGE_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Ledge";
	}

}
