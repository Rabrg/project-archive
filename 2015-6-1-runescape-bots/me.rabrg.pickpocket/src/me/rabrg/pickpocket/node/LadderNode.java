package me.rabrg.pickpocket.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class LadderNode extends Node {

	public static boolean climb = false;

	private GameObject ladder;

	public LadderNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (climb || ctx.getLocalPlayer().getZ() == 1) && (ladder = ctx.getGameObjects().closest("Ladder")) != null;
	}

	@Override
	public int execute() {
		if (ladder.interact("Climb-up") || ladder.interact("Climb-down")) {
			climb = false;
		}
		final int height = ctx.getLocalPlayer().getZ();
		MethodProvider.sleepUntil(new Condition() {
			@Override
			public boolean verify() {
				return ctx.getLocalPlayer().getZ() != height;
			}
		}, Calculations.random(3000, 4500));
		return Calculations.random(250, 500);
	}

	@Override
	public String getName() {
		return "Climbing ladder";
	}

}
