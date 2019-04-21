package me.rabrg.agility.varrock;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class ClothesLineNode extends VarrockNode {

	private GameObject clothesLine;

	public ClothesLineNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return CLOTHES_LINE_AREA.contains(ctx.getLocalPlayer()) && (clothesLine = ctx.getGameObjects().closest("Clothes line")) != null;
	}

	@Override
	public int execute() {
		if (!clothesLine.isOnScreen()) {
			ctx.getCamera().rotateToEntity(clothesLine);
		} else if (clothesLine.interact("Cross")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !CLOTHES_LINE_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Clothes line";
	}

}
