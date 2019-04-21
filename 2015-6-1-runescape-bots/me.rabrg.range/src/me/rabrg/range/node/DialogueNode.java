package me.rabrg.range.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

public final class DialogueNode extends Node {

	public DialogueNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getDialogues().inDialogue();
	}

	@Override
	public int execute() {
		if (ctx.getDialogues().getOptions() != null && ctx.getDialogues().clickOption(1)) {
			count = 0;
			ingame = true;
		} else {
			if (ctx.getDialogues().clickContinue()) {
				if (count >= 10) {
					count = 0;
					ingame = false;
				}
			}
		}
		return Calculations.random(500, 900);
	}

	@Override
	public String getName() {
		return "Talking to judge";
	}

}
