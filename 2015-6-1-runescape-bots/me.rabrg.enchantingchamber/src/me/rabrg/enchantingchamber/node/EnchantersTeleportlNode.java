package me.rabrg.enchantingchamber.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class EnchantersTeleportlNode extends Node {

	private GameObject enchantersTeleport;

	public EnchantersTeleportlNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !needHop && OUTSIDE_AREA.contains(ctx.getLocalPlayer()) && (enchantersTeleport = ctx.getGameObjects().closest("Enchanters Teleport")) != null;
	}

	@Override
	public int execute() {
		if (enchantersTeleport.distance() > 8) {
			if (ctx.getWalking().walk(enchantersTeleport)) {
				return Calculations.random(600, 1800);
			}
		} else if (!enchantersTeleport.isOnScreen()) {
			ctx.getCamera().rotateToEntity(enchantersTeleport);
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return enchantersTeleport.isOnScreen();
				}
			}, Calculations.random(750, 1500));
		} else  if (enchantersTeleport.interact("Enter")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !OUTSIDE_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(3200, 4400));
		}
		return Calculations.random(150, 300);
	}

	@Override
	public String getName() {
		return "Entering enchanters teleport";
	}

}
