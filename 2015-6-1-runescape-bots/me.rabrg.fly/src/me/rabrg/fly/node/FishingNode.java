package me.rabrg.fly.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

public final class FishingNode extends Node {

	public FishingNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getLocalPlayer().getAnimation() == 623;
	}

	@Override
	public int execute() {
		return Calculations.random(800, 1600);
	}

	@Override
	public String getName() {
		return "Fishing";
	}

}
