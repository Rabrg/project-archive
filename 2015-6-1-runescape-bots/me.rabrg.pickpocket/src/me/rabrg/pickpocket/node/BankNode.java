package me.rabrg.pickpocket.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.skills.Skill;

public final class BankNode extends Node {

	public BankNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) <= EatNode.nextEat && ctx.getInventory().get("Lobster") == null;
	}

	@Override
	public int execute() {
		return Calculations.random(300, 900);
	}

	@Override
	public String getName() {
		return "Banking";
	}

}
