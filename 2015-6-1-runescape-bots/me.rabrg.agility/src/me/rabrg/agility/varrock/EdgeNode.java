package me.rabrg.agility.varrock;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class EdgeNode extends VarrockNode {

	private GameObject ledge;

	public EdgeNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return EDGE_AREA.contains(ctx.getLocalPlayer()) && (ledge = ctx.getGameObjects().closest("Edge")) != null;
	}

	@Override
	public int execute() {
		if (!ledge.isOnScreen()) {
			ctx.getCamera().rotateToEntity(ledge);
		} else if (ledge.interact("Jump-off")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !EDGE_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Edge";
	}

}
