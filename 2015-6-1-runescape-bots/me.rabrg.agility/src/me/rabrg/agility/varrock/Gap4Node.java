package me.rabrg.agility.varrock;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class Gap4Node extends VarrockNode {

	private GameObject gap;

	public Gap4Node(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return GAP_4_AREA.contains(ctx.getLocalPlayer()) && (gap = ctx.getGameObjects().getTopObjectOnTile(new Tile(3233, 3403, 3))) != null;
	}

	@Override
	public int execute() {
		if (gap.distance() > 5) {
			ctx.getWalking().walk(gap);
			return Calculations.random(700, 1900);
		} else if (!gap.isOnScreen()) {
			ctx.getCamera().rotateToEntity(gap);
		} else if (gap.interact("Leap")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !GAP_4_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Gap 3";
	}

}
