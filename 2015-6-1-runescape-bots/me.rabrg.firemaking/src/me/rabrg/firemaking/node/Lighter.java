package me.rabrg.firemaking.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;

public final class Lighter extends Node {

	private static final Area BANK_AREA = new Area(3092, 3240, 3097, 3246);
	private static final Tile FIRE_TILE = new Tile(3098, 3248);

	public Lighter(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().contains(LOGS_FILTER);
	}

	@Override
	public int execute() {
		ctx.getWalking().setRunThreshold(Calculations.random(30, 60));
		if (BANK_AREA.contains(ctx.getLocalPlayer())) {
			if (ctx.getWalking().walkExact(FIRE_TILE)) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getLocalPlayer().getTile().equals(FIRE_TILE);
					}
				}, Calculations.random(1200, 2400));
			}
		} else {
			if (ctx.getInventory().get("Tinderbox").interact()) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getInventory().getSelectedItemName() != null;
					}
				}, Calculations.random(600, 1200));
				if (ctx.getInventory().get(LOGS_FILTER).interact()) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return ctx.getLocalPlayer().getAnimation() > 1;
						}
					}, Calculations.random(7200, 14400));
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return ctx.getLocalPlayer().getAnimation() < 1;
						}
					}, Calculations.random(7200, 14400));
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return ctx.getDialogues().continueDialogue();
						}
					}, Calculations.random(1200, 1600));
				}
			}
			
		}
		return Calculations.random(0, 255);
	}

	@Override
	public String getName() {
		return "light";
	}

}
