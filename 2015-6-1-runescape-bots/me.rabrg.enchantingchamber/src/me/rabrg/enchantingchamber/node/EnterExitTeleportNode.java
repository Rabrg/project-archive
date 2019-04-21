package me.rabrg.enchantingchamber.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class EnterExitTeleportNode extends Node {

	private GameObject exitTeleport;

	public EnterExitTeleportNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return INSIDE_AREA.contains(ctx.getLocalPlayer()) && !ctx.getInventory().contains("Dragonstone") && ctx.getGroundItems().closest("Dragonstone") == null && (exitTeleport = ctx.getGameObjects().closest("Exit Teleport")) != null;
	}

	@Override
	public int execute() {
		if (exitTeleport.distance() > 8) {
			if (ctx.getWalking().walk(exitTeleport)) {
				return Calculations.random(600, 1800);
			}
		} else if (!exitTeleport.isOnScreen()) {
			ctx.getCamera().rotateToEntity(exitTeleport);
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return exitTeleport.isOnScreen();
				}
			}, Calculations.random(750, 1500));
		} else  if (exitTeleport.interact("Enter")) {
			needHop = true;
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return OUTSIDE_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(3200, 4400));
		}
		return Calculations.random(75, 150);
	}

	@Override
	public String getName() {
		return "Entering exit teleport";
	}

}
