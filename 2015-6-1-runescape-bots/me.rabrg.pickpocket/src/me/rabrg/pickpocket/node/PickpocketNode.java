package me.rabrg.pickpocket.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.wrappers.interactive.NPC;

public final class PickpocketNode extends Node {

	private NPC target;

	public PickpocketNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (target = ctx.getNpcs().closest("Bandit")) != null && (target.getAnimation() == 838 || (target.getOverhead() != null && target.getOverhead().equals("I'll kill you for that!")));
	}

	@Override
	public int execute() {
		target.interact("Pickpocket");
		return Calculations.random(300, 500);
	}

	@Override
	public String getName() {
		return "Pickpocketing";
	}

}
