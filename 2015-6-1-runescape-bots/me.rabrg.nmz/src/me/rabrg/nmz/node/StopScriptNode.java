package me.rabrg.nmz.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.skills.Skill;

public final class StopScriptNode extends Node {

	public StopScriptNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getNpcs().closest("Dominic Onion") != null || ctx.getNpcs().closest("Dominic") != null || ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) >= 70;
	}

	@Override
	public int execute() {
		ctx.getTabs().logout();
		return Calculations.random(600, 1200);
	}

	@Override
	public String getName() {
		return "Stopping script";
	}

}
