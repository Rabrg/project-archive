package me.rabrg.shrimp.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;

public final class ContinueNode extends Node {

	public ContinueNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getDialogues().canContinue();
	}

	@Override
	public int execute() {
		if (ctx.getDialogues().continueDialogue()) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !ctx.getDialogues().canContinue();
				}
			}, Calculations.random(800, 900));
		}
		return Calculations.random(600, 900);
	}

	@Override
	public String getName() {
		return "Continuing dialogue";
	}

}
