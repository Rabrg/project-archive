package me.rabrg.agility.gnome;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class LogBalanceNode extends GnomeNode {

	private GameObject logBalance;

	public LogBalanceNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return LOG_BALANCE_AREA.contains(ctx.getLocalPlayer()) && (logBalance = ctx.getGameObjects().closest("Log balance")) != null;
	}

	@Override
	public int execute() {
		if (logBalance.distance() > 5) {
			ctx.getWalking().walk(logBalance);
			return Calculations.random(600, 1800);
		} else if (!logBalance.isOnScreen()) {
			ctx.getCamera().rotateToEntity(logBalance);
		} else if (logBalance.interact()) {
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
		return "Log balance";
	}

}
