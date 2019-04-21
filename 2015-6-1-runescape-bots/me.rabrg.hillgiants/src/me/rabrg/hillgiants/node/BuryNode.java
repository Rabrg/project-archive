package me.rabrg.hillgiants.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

public final class BuryNode extends Node {

	private Item bones;

	public BuryNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getLocalPlayer().isInCombat() && (bones = ctx.getInventory().get("Big bones")) != null;
	}

	@Override
	public int execute() {
		if (bones.interact("Bury")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(0, 300);
					return bones == null || ctx.getInventory().getItemInSlot(bones.getSlot()) == null;
				}
			}, Calculations.random(600, 1200));
		}
		return Calculations.random(0, 300);
	}

	@Override
	public String toString() {
		return "Burying bones";
	}

}
