package me.rabrg.fly.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

public final class DropNode extends Node {

	public DropNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().isFull();
	}

	@Override
	public int execute() {
		ctx.getInventory().dropAllExcept("Feather", "Fly fishing rod", "Tinderbox");
		return Calculations.random(0, 255);
	}

	@Override
	public String getName() {
		return "dropping";
	}

}
