package me.rabrg.air.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;

public final class BankNode extends Node {

	private static final Tile BANK_TILE = new Tile(3012, 3356);

	public BankNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getInventory().isFull() && !ALTAR_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		if (ctx.getBank().isOpen()) {
			if (ctx.getInventory().contains("Air rune")) {
				if (ctx.getBank().depositAll("Air rune")) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return !ctx.getInventory().contains("Air rune");
						}
					}, Calculations.random(900, 1200));
				}
			} else if (!ctx.getInventory().isFull()) {
				if (ctx.getBank().withdrawAll("Pure essence")) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return ctx.getInventory().contains("Pure essence");
						}
					}, Calculations.random(900, 1200));
				}
			}
		} else if (!ctx.getBank().openClosest()) {
			ctx.getWalking().walk(BANK_TILE.getRandomizedTile(3));
			return Calculations.random(500, 1750);
		}
		return Calculations.random(0, 250);
	}

}
