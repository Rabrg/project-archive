package me.rabrg.fire.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class EnterFireAltarNode extends Node {

	private static final Tile MYSTERIOUS_RUINS_TILE = new Tile(3312, 3253);

	private GameObject mysteriousRuins;

	public EnterFireAltarNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !CASTLE_WARS_AREA.contains(ctx.getLocalPlayer()) && !FIRE_ALTAR_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		mysteriousRuins = ctx.getGameObjects().closest("Mysterious ruins");
		if (mysteriousRuins == null || mysteriousRuins.distance() > 10) {
			ctx.getWalking().walk(MYSTERIOUS_RUINS_TILE);
		} else {
			if (!mysteriousRuins.isOnScreen()) {
				ctx.getCamera().rotateToEntity(mysteriousRuins);
				return Calculations.random(350, 500);
			} else if (mysteriousRuins.interact("Enter")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return FIRE_ALTAR_AREA.contains(ctx.getLocalPlayer());
					}
				}, Calculations.random(6000, 8500));
			}
		}
		return Calculations.random(1100, 1800);
	}

	@Override
	public String getName() {
		return "Entering fire altar";
	}

}
