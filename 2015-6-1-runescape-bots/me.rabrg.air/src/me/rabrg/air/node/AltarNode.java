package me.rabrg.air.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class AltarNode extends Node {

	public AltarNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ALTAR_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		if (ctx.getInventory().isFull()) {
			final GameObject altar = ctx.getGameObjects().closest("Altar");
			if (altar.distance() > 5) {
				ctx.getWalking().walk(altar.getTile().getRandomizedTile(3));
				return Calculations.random(500, 1750);
			} else if (!altar.isOnScreen()) {
				ctx.getCamera().rotateToEntity(altar);
			} else if (altar.interact()) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getLocalPlayer().getAnimation() > 1;
					}
				}, Calculations.random(5400, 7200));
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getLocalPlayer().getAnimation() < 1;
					}
				}, Calculations.random(3200, 4800));
			}
		} else {
			final GameObject portal = ctx.getGameObjects().closest("Portal");
			if (portal.distance() > 5) {
				ctx.getWalking().walk(portal.getTile().getRandomizedTile(3));
				return Calculations.random(500, 1750);
			} else if (!portal.isOnScreen()) {
				ctx.getCamera().rotateToEntity(portal);
			} else if (portal.interact()) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !ALTAR_AREA.contains(ctx.getLocalPlayer());
					}
				}, Calculations.random(5400, 7200));
			}
		}
		return Calculations.random(0, 250);
	}

}
