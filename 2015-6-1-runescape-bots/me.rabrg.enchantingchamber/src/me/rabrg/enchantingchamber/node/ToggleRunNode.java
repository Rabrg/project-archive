package me.rabrg.enchantingchamber.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

public final class ToggleRunNode extends Node {

	private int nextRun = getNextRun();

	public ToggleRunNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getWalking().getRunEnergy() >= nextRun && !ctx.getWalking().isRunEnabled();
	}

	@Override
	public int execute() {
		ctx.getWalking().toggleRun();
		nextRun = getNextRun();
		return Calculations.random(75, 150);
	}

	@Override
	public String getName() {
		return "Toggling run";
	}

	private static int getNextRun() {
		return Calculations.random(20, 80);
	}
}
