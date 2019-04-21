package me.rabrg.normallog.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;

public final class Banker extends Node {

	private static final Tile BANK_TILE = new Tile(3183, 3440);

	public Banker(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().isFull();
	}

	@Override
	public int execute() {
		if (BANK_TILE.distance() > 15) {
			ctx.getWalking().walk(BANK_TILE);
		} else if (!ctx.getBank().isOpen()) {
			if (ctx.getBank().openClosest()) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getBank().isOpen();
					}
				}, Calculations.random(5000, 7500));
			}
		} else {
			if (ctx.getBank().depositAllExcept("Rune axe", "Adamant axe", "Mithril axe", "Black axe", "Steel axe", "Iron axe")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getInventory().getEmptySlots() > 0;
					}
				}, Calculations.random(5000, 7500));
			}
		}
		return Calculations.random(0, 255);
	}

	@Override
	public String getName() {
		return "bank";
	}

}
