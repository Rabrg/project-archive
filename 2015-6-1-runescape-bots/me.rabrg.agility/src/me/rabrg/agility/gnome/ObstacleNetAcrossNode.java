package me.rabrg.agility.gnome;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class ObstacleNetAcrossNode extends GnomeNode {

	private GameObject obstacleNet;

	public ObstacleNetAcrossNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return OBSTACLE_NET_ACROSS_AREA.contains(ctx.getLocalPlayer()) && (obstacleNet = ctx.getGameObjects().closest("Obstacle net")) != null;
	}

	@Override
	public int execute() {
		if (!obstacleNet.isOnScreen()) {
			ctx.getCamera().rotateToEntity(obstacleNet);
		} else  {
			MethodProvider.log("" + obstacleNet.interact() + " " + OBSTACLE_PIPE_AREA.contains(ctx.getLocalPlayer()));
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return OBSTACLE_PIPE_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Obstacle net across";
	}

}
