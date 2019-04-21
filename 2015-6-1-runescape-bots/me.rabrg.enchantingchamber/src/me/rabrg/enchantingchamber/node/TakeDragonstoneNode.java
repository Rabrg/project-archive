package me.rabrg.enchantingchamber.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.GroundItem;

public final class TakeDragonstoneNode extends Node {

	private GroundItem dragonstone;

	public TakeDragonstoneNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return INSIDE_AREA.contains(ctx.getLocalPlayer()) && (dragonstone = ctx.getGroundItems().closest("Dragonstone")) != null;
	}

	@Override
	public int execute() {
		if (!dragonstone.isOnScreen()) {
			ctx.getCamera().rotateToEntity(dragonstone);
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return dragonstone.isOnScreen();
				}
			}, Calculations.random(750, 1500));
		} else if (dragonstone.distance() > 8) {
			if (ctx.getWalking().walk(dragonstone)) {
				return Calculations.random(600, 1800);
			}
		} else   if (dragonstone.interact("Take")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					GroundItem newDragonstone;
					if ((newDragonstone = ctx.getGroundItems().closest("Dragonstone")) != null) {
						return newDragonstone.getX() != dragonstone.getX();
					} else {
						return true;
					}
				}
			}, Calculations.random(3200, 4400));
		}
		return Calculations.random(75, 150);
	}

	@Override
	public String getName() {
		return "Taking dragonstone";
	}

}
