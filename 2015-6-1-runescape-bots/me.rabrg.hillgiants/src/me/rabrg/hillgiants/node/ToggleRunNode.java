package me.rabrg.hillgiants.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;

public final class ToggleRunNode extends Node {

	private int nextRun = Calculations.random(20, 80);

	public ToggleRunNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getWalking().isRunEnabled() && ctx.getWalking().getRunEnergy() >= nextRun;
	}

	@Override
	public int execute() {
		if (ctx.getWalking().toggleRun()) {
			nextRun = Calculations.random(20, 80);
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getWalking().isRunEnabled();
				}
			}, Calculations.random(900, 1200));
		}
		return Calculations.random(0, 300);
	}

	@Override
	public String toString() {
		return "Toggling run";
	}

}
