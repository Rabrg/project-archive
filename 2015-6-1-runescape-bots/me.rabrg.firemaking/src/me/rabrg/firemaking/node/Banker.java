package me.rabrg.firemaking.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;

public final class Banker extends Node {

	public Banker(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getInventory().contains(LOGS_FILTER) || ctx.getBank().isOpen();
	}

	@Override
	public int execute() {
		if (!ctx.getBank().isOpen()) {
			if (ctx.getBank().openClosest()) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getBank().isOpen();
					}
				}, Calculations.random(10000, 15000));
			}
		} else if (!ctx.getInventory().contains(LOGS_FILTER)){
			if (ctx.getBank().withdrawAll(LOGS_FILTER)) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getInventory().contains(LOGS_FILTER);
					}
				}, Calculations.random(5000, 7500));
			}
		} else {
			ctx.getBank().close();
		}
		return Calculations.random(0, 255);
	}

	@Override
	public String getName() {
		return "bank";
	}

}
