package me.rabrg.agility.gnome;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class ObstacleNetUpNode extends GnomeNode {

	private GameObject obstacleNet;

	public ObstacleNetUpNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return OBSTACLE_NET_UP_AREA.contains(ctx.getLocalPlayer()) && (obstacleNet = ctx.getGameObjects().closest("Obstacle net")) != null;
	}

	@Override
	public int execute() {
		if (!obstacleNet.isOnScreen()) {
			ctx.getCamera().rotateToEntity(obstacleNet);
		} else if (obstacleNet.interact()) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return TREE_BRANCH_UP_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Obstacle net up";
	}

}
