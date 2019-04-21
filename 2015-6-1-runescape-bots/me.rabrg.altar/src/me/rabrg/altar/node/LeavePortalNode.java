package me.rabrg.altar.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class LeavePortalNode extends Node {

	private final String bones;

	private GameObject portal;

	public LeavePortalNode(final String bones, final MethodContext ctx) {
		super(ctx);
		this.bones = bones;
	}

	@Override
	public boolean validate() {
		return !YANILLE_AREA.contains(ctx.getLocalPlayer()) && !ctx.getInventory().contains(bones) && (portal = ctx.getGameObjects().closest("Portal")) != null;
	}

	@Override
	public int execute() {
		if (portal.interact("Enter")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return YANILLE_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5200, 6400));
		}
		return Calculations.random(0, 300);
	}

	@Override
	public String getName() {
		return "Leaving portal";
	}

}
