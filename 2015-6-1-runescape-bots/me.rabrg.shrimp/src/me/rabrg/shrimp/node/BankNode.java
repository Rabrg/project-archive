package me.rabrg.shrimp.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class BankNode extends Node {

	private GameObject bankBooth;

	public BankNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().getEmptySlots() != 27 && ((bankBooth = ctx.getGameObjects().closest("Bank booth")) != null && bankBooth.distance() <= 10) && (ctx.getBank().isOpen() || ctx.getBank().openClosest());
	}

	@Override
	public int execute() {
		if (ctx.getBank().isOpen()) {
			if (ctx.getInventory().getEmptySlots() != 27 && ctx.getBank().depositAllExcept("Small fishing net")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getInventory().getEmptySlots() == 27;
					}
				}, Calculations.random(3000, 4200));
			} else if (ctx.getInventory().getEmptySlots() == 27) {
				ctx.getBank().close();
			}
		}
		return Calculations.random(600, 1200);
	}

	@Override
	public String getName() {
		return "Banking";
	}

}
