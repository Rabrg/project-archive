package me.rabrg.agility.gnome;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class BalancingRopeNode extends GnomeNode {

	private GameObject balancingRope;

	public BalancingRopeNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return BALANCING_ROPE_AREA.contains(ctx.getLocalPlayer()) && (balancingRope = ctx.getGameObjects().closest("Balancing rope")) != null;
	}

	@Override
	public int execute() {
		if (!balancingRope.isOnScreen()) {
			ctx.getCamera().rotateToEntity(balancingRope);
		} else if (balancingRope.interactForceRight("Walk-on")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return TREE_BRANCH_DOWN_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Obstacle net";
	}

}
