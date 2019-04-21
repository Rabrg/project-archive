package me.rabrg.cooking.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;

public final class BankNode extends Node {

	public BankNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return getRawItem() == null || ctx.getBank().isOpen();
	}

	@Override
	public int execute() {
		if (!ctx.getBank().isOpen() && ctx.getBank().openClosest()) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getBank().isOpen();
				}
			}, Calculations.random(6400, 7200));
		} else if (ctx.getBank().isOpen()) {
			if (getRawItem() != null) {
				ctx.getBank().close();
			} else if (ctx.getInventory().isFull()) {
				ctx.getBank().depositAllItems();
			} else {
				ctx.getBank().withdrawAll(RAW_ITEM_FILTER);
			}
		}
		return Calculations.random(850, 1200);
	}

	@Override
	public String getName() {
		return "Banking";
	}

}
