package me.rabrg.shrimp.node;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class WalkBankNode extends Node {

	private static final Tile BANK_TILE = new Tile(3270, 3166);

	private GameObject bankBooth;

	public WalkBankNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().isFull() && ((bankBooth = ctx.getGameObjects().closest("Bank booth")) == null || bankBooth.distance() > 10);
	}

	@Override
	public int execute() {
		if (bankBooth != null) {
			ctx.getWalking().walk(bankBooth);
		} else {
			ctx.getWalking().walk(BANK_TILE);
		}
		return 0;
	}

	@Override
	public String getName() {
		return "Walking to bank";
	}

}
