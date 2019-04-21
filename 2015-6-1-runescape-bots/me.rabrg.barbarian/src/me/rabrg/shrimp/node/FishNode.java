package me.rabrg.shrimp.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;

public final class FishNode extends Node {

	private NPC fishingSpot;

	public FishNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getInventory().isFull() && ((fishingSpot = ctx.getNpcs().closest(1542)) != null && (fishingSpot.distance() > 1 || ctx.getLocalPlayer().getAnimation() != 623));
	}

	@Override
	public int execute() {
		if (fishingSpot.interact()) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getLocalPlayer().getAnimation() == 623;
				}
			}, Calculations.random(3000, 4200));
		}
		return Calculations.random(600, 1200);
	}

	@Override
	public String getName() {
		return "Interacting with fishing spot";
	}

}
