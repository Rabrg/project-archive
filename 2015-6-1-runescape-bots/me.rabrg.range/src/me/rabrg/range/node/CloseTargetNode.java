package me.rabrg.range.node;

import java.awt.Rectangle;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;

public final class CloseTargetNode extends Node {

	private static final Rectangle CLOSE_BUTTON = new Rectangle(477, 32, 18, 20);

	public CloseTargetNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (ctx.getWidgets().getWidget(325) != null && ctx.getWidgets().getWidget(325).isVisible());
	}

	@Override
	public int execute() {
		if (ctx.getMouse().click(CLOSE_BUTTON, false)) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return (ctx.getWidgets().getWidget(325) == null || !ctx.getWidgets().getWidget(325).isVisible());
				}
			}, Calculations.random(1500, 3000));
		}
		return Calculations.random(75, 150);
	}

	@Override
	public String getName() {
		return "Closing target";
	}

}
