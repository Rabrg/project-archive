package me.rabrg.agility.varrock;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class WallNode extends VarrockNode {

	private GameObject wall;

	public WallNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return WALL_AREA.contains(ctx.getLocalPlayer()) && (wall = ctx.getGameObjects().closest("Wall")) != null;
	}

	@Override
	public int execute() {
		if (!wall.isOnScreen()) {
			ctx.getCamera().rotateToEntity(wall);
		} else if (wall.interact("Balance")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !WALL_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Wall";
	}

}
