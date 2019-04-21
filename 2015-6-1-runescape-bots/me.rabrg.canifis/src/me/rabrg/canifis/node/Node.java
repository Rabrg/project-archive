package me.rabrg.canifis.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public abstract class Node {

	final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	final boolean navigateInsideArea(final String name, final String action, final Area area) {
		final GameObject obstacle = ctx.getGameObjects().closest(new Filter<GameObject>() {
			@Override
			public boolean match(final GameObject object) {
				return object.getName().equals(name) && area.contains(object);
			}
		});
		if (!obstacle.isOnScreen())
			ctx.getCamera().rotateToEntity(obstacle);
		else
			return obstacle.interact(action);
		return false;
	}

	final void sleepUntilInsideArea(final Area area) {
		MethodProvider.sleepUntil(new Condition() {
			@Override
			public boolean verify() {
				return area.contains(ctx.getLocalPlayer());
			}
		}, Calculations.random(4750, 6000));
	}
}
