package me.rabrg.hillgiants.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class BankNode extends Node {

	private GameObject bankChest;

	public BankNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getInventory().contains(FOOD_FILTER) && (bankChest = ctx.getGameObjects().closest("Bank chest")) != null && bankChest.distance() < 10;
	}

	@Override
	public int execute() {
		if (!ctx.getBank().isOpen() && ctx.getBank().openClosest()) {
			ctx.getWalking().setRunThreshold(Calculations.random(20, 80));
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getBank().isOpen();
				}
			}, Calculations.random(1800, 3600));
		} else if (ctx.getBank().isOpen() && !ctx.getInventory().isEmpty() && ctx.getBank().depositAllItems()) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getInventory().isEmpty();
				}
			}, Calculations.random(600, 900));
		} else if (ctx.getBank().isOpen() && ctx.getInventory().isEmpty() && ctx.getBank().withdraw(FOOD_FILTER, 28)) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !ctx.getInventory().isEmpty();
				}
			}, Calculations.random(600, 900));
		}
		return Calculations.random(0, 300);
	}

	@Override
	public String toString() {
		return "Banking";
	}

}
