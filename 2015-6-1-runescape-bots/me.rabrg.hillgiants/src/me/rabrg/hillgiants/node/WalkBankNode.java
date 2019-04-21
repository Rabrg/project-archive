package me.rabrg.hillgiants.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class WalkBankNode extends Node {

	private GameObject bankChest;

	public WalkBankNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getInventory().contains(FOOD_FILTER)  && (bankChest = ctx.getGameObjects().closest("Bank chest")) != null && bankChest.distance() > 10;
	}

	@Override
	public int execute() {
		ctx.getWalking().walk(BANK_TILE);
		return Calculations.random(600, 1800);
	}

	@Override
	public String toString() {
		return "Walking to bank";
	}

}
