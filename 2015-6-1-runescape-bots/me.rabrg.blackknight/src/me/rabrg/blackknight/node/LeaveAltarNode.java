package me.rabrg.blackknight.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class LeaveAltarNode extends Node {

	private GameObject altar;

	public LeaveAltarNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getSkills().getBoostedLevels(Skill.PRAYER) == ctx.getSkills().getRealLevel(Skill.PRAYER) && (altar = ctx.getGameObjects().closest("Chaos altar")) != null && ctx.getMap().canReach(altar);
	}

	@Override
	public int execute() {
		final GameObject sturdyDoor = ctx.getGameObjects().closest("Sturdy door");
		if (sturdyDoor != null && sturdyDoor.interact("Open"))
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !ctx.getMap().canReach(altar);
				}
			}, Calculations.random(3000, 4200));
		return Calculations.random(75, 1200);
	}

	@Override
	public String getName() {
		return "Leaving altar room";
	}

}
