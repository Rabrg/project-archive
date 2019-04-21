package me.rabrg.range.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class TargetNode extends Node {

	private static final Tile TARGET_ONE = new Tile(2679, 3426);
			
	public TargetNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getDialogues().inDialogue() && (ctx.getWidgets().getWidget(325)== null || !ctx.getWidgets().getWidget(325).isVisible()) && ingame;
	}

	@Override
	public int execute() {
		final GameObject target = ctx.getGameObjects().getTopObjectOnTile(TARGET_ONE);
		if (!target.isOnScreen())
			ctx.getCamera().rotateToEntity(target);
		else if (target.interact("Fire-at")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return (ctx.getWidgets().getWidget(325) != null && ctx.getWidgets().getWidget(325).isVisible()) || ctx.getDialogues().inDialogue();
				}
			}, Calculations.random(4000, 6000));
			count++;
		}
		return Calculations.random(75, 300);
	}

	@Override
	public String getName() {
		return "Interacting with target";
	}

}
