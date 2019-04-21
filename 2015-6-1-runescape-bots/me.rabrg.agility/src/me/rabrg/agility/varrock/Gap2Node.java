package me.rabrg.agility.varrock;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class Gap2Node extends VarrockNode {

	private GameObject gap;

	public Gap2Node(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return GAP_2_AREA.contains(ctx.getLocalPlayer()) && (gap = ctx.getGameObjects().closest("Gap")) != null;
	}

	@Override
	public int execute() {
		if (!gap.isOnScreen()) {
			ctx.getCamera().rotateToEntity(gap);
		} else if (gap.interact("Leap")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !GAP_2_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Gap 2";
	}

}
