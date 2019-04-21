package me.rabrg.range.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

public final class EquipArrowsNode extends Node {

	public EquipArrowsNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().contains("Bronze arrow");
	}

	@Override
	public int execute() {
		ctx.getInventory().get("Bronze arrow").interact("Wield");
		return Calculations.random(750, 900);
	}

	@Override
	public String getName() {
		return "Equiping arrows";
	}

}
