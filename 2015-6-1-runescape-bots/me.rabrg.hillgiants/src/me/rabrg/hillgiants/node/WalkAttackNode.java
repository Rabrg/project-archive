package me.rabrg.hillgiants.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

public final class WalkAttackNode extends Node {

	public WalkAttackNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().contains(FOOD_FILTER) && ctx.getNpcs().closest("Hill Giant") == null;
	}

	@Override
	public int execute() {
		ctx.getWalking().walk(ATTACK_TILE.getRandomizedTile());
		return Calculations.random(600, 1800);
	}

	@Override
	public String toString() {
		return "Walking to attack";
	}

}
