package me.rabrg.telekinetic.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.wrappers.interactive.NPC;

public final class TalkMazeGuardianNode extends Node {

	private NPC mazeGuardian;

	public TalkMazeGuardianNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (mazeGuardian = ctx.getNpcs().closest("Maze Guardian")) != null || ctx.getDialogues().inDialogue();
	}

	@Override
	public int execute() {
		if (!ctx.getDialogues().inDialogue()) {
			mazeGuardian.interact("Talk-to");
		} else {
			if (ctx.getDialogues().canContinue()) {
				ctx.getDialogues().continueDialogue();
			} else if (ctx.getDialogues().chooseOption(1)) {
				
			}
		}
		return Calculations.random(1200, 1800);
	}

	@Override
	public String getName() {
		return "Talking";
	}

}
