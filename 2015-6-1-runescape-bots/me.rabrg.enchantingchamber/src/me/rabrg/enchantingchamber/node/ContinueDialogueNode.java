package me.rabrg.enchantingchamber.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;

public final class ContinueDialogueNode extends Node {

	public ContinueDialogueNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getDialogues().canContinue();
	}

	@Override
	public int execute() {
		if (ctx.getDialogues().clickContinue()) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !ctx.getDialogues().canContinue();
				}
			}, Calculations.random(3800, 4400));
		}
		return Calculations.random(75, 150);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
