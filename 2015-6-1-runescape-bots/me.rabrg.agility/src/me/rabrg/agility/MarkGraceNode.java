package me.rabrg.agility;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.wrappers.items.GroundItem;

public final class MarkGraceNode extends Node {

	private GroundItem markOfGrace;

	public MarkGraceNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (markOfGrace = ctx.getGroundItems().closest("Mark of grace")) != null && ctx.getMap().canReach(markOfGrace);
	}

	@Override
	public int execute() {
		if (markOfGrace.distance() > 6) {
			ctx.getWalking().walk(markOfGrace);
		} else if (!markOfGrace.isOnScreen()) {
			ctx.getCamera().rotateToEntity(markOfGrace);
			return Calculations.random(0, 255);
		} else {
			markOfGrace.interactForceRight("Take");
		}
		return Calculations.random(600, 1800);
	}

	@Override
	public String getName() {
		return "Mark of grace";
	}

}
