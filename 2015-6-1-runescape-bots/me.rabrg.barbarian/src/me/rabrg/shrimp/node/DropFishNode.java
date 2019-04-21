package me.rabrg.shrimp.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

public final class DropFishNode extends Node {

	public DropFishNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().isFull();
	}

	@Override
	public int execute() {
		ctx.getInventory().dropAllExcept("Barbarian rod", "Feather");
		return Calculations.random(900, 1800);
	}

	@Override
	public String getName() {
		return "Droping";
	}

}
