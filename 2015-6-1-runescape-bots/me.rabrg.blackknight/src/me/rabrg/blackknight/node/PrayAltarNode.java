package me.rabrg.blackknight.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class PrayAltarNode extends Node {

	private GameObject altar;

	public PrayAltarNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getSkills().getBoostedLevels(Skill.PRAYER) <= nextRecharge && (altar = ctx.getGameObjects().closest("Chaos altar")) != null && ctx.getMap().canReach(altar);
	}

	@Override
	public int execute() {
		if (altar.interact("Pray-at")) {
			nextRecharge = getNextRecharge();
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getSkills().getBoostedLevels(Skill.PRAYER) == ctx.getSkills().getRealLevel(Skill.PRAYER);
				}
			}, Calculations.random(3000, 4200));
		}
		return Calculations.random(75, 1200);
	}

	@Override
	public String getName() {
		return "Praying at altar";
	}

}
