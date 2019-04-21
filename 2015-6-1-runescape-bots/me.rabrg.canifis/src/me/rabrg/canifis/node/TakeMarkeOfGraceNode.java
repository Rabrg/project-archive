package me.rabrg.canifis.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.GroundItem;

public final class TakeMarkeOfGraceNode extends Node {

	private GroundItem markOfGrace;

	public TakeMarkeOfGraceNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (markOfGrace = ctx.getGroundItems().closest("Mark of grace")) != null && markOfGrace.distance() <= 8;
	}

	@Override
	public int execute() {
		if (!markOfGrace.isOnScreen()) {
			ctx.getCamera().rotateToEntity(markOfGrace);
		} else if (markOfGrace.interact("Take")) {
			MethodContext.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getGroundItems().closest("Mark of grace") == null;
				}
			}, Calculations.random(2500, 5000));
		}
		return Calculations.random(450, 900);
	}

}
