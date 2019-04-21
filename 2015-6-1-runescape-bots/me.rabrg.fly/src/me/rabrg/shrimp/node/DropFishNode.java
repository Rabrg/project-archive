package me.rabrg.shrimp.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

public final class DropFishNode extends Node {

	public DropFishNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().isFull() || droping;
	}

	@Override
	public int execute() {
		if (!ctx.getInventory().contains("Raw trout", "Raw salmon")) {
			droping = false;
			return Calculations.random(900, 1800);
		} else if (!droping) {
			droping = true;
		}
		ctx.getInventory().dropAllExcept("Fly fishing rod", "Feather");
		return Calculations.random(900, 1800);
	}

	@Override
	public String getName() {
		return "Droping";
	}

}
