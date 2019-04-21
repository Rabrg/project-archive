package me.rabrg.agility.gnome;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class ObstaclePipeNode extends GnomeNode {

	private GameObject obstaclePipe;

	public ObstaclePipeNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return OBSTACLE_PIPE_AREA.contains(ctx.getLocalPlayer()) && (obstaclePipe = ctx.getGameObjects().closest("Obstacle pipe")) != null;
	}

	@Override
	public int execute() {
		if (obstaclePipe.distance() > 5) {
			ctx.getWalking().walk(obstaclePipe);
			return Calculations.random(600, 1800);
		} else if (!obstaclePipe.isOnScreen()) {
			ctx.getCamera().rotateToEntity(obstaclePipe);
		} else if (obstaclePipe.interactForceRight("Squeeze-through")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return OBSTACLE_NET_UP_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Obstacle pipe";
	}

}
