package me.rabrg.pickpocket.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.wrappers.interactive.NPC;

public final class PickpocketNode extends Node {

	private NPC masterFarmer;

	public PickpocketNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (masterFarmer = ctx.getNpcs().closest("Master Farmer")) != null;
	}

	@Override
	public int execute() {
		masterFarmer.interact("Pickpocket");
		return Calculations.random(300, 1200);
	}

	@Override
	public String getName() {
		return "Pickpocketing";
	}

}
