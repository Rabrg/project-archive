package me.rabrg.range.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;

public final class StartNode extends Node {

	private NPC judge;

	public StartNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ingame && (judge = ctx.getNpcs().closest("Competition Judge")) != null && !ctx.getDialogues().inDialogue();
	}

	@Override
	public int execute() {
		if (judge.interact("Talk-to")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getDialogues().inDialogue();
				}
			}, Calculations.random(500, 900));
			return 0;
		} else
			return Calculations.random(500, 900);
	}

	@Override
	public String getName() {
		return "Interacting with judge";
	}

}
