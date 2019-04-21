package me.rabrg.altar.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class AltarNode extends Node {

	private final String bones;

	private GameObject altar;
	private int lastEmptySlots = -1;
	private long nextUse = System.currentTimeMillis() + 3000;

	public AltarNode(final String bones, final MethodContext ctx) {
		super(ctx);
		this.bones = bones;
	}

	@Override
	public boolean validate() {
		return !YANILLE_AREA.contains(ctx.getLocalPlayer()) && ctx.getInventory().contains(bones) && (altar = ctx.getGameObjects().closest("Altar")) != null;
	}

	@Override
	public int execute() {
		final int currentEmptySlots = ctx.getInventory().getEmptySlots();
		if (lastEmptySlots != currentEmptySlots) {
			lastEmptySlots = currentEmptySlots;
			nextUse = System.currentTimeMillis() + 3000;
		}
		
		if (!ctx.getInventory().isItemSelected() && (System.currentTimeMillis() >= nextUse || ctx.getDialogues().inDialogue()) && ctx.getInventory().interact(bones, "Use")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getInventory().isItemSelected();
				}
			}, Calculations.random(600, 1200));
		} else if (ctx.getInventory().isItemSelected() && (System.currentTimeMillis() >= nextUse || ctx.getDialogues().inDialogue()) && altar.interact("Use")) {
			return Calculations.random(4200, 5400);
		}
		return Calculations.random(0, 300);
	}

	@Override
	public String getName() {
		return "Using bones on altar";
	}

}
