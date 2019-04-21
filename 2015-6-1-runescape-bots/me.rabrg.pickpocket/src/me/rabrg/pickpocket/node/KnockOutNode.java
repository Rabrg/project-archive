package me.rabrg.pickpocket.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.wrappers.interactive.NPC;

public final class KnockOutNode extends Node {

	private NPC target;

	public KnockOutNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (target = ctx.getNpcs().closest("Bandit")) != null && (target.getAnimation() != 838 || (target.getOverhead() != null && target.getOverhead().contains("Zzzz")));
	}

	@Override
	public int execute() {
		target.interact("Knock-Out");
		return Calculations.random(450, 650);
	}

	@Override
	public String getName() {
		return "Pickpocketing";
	}

}
