package me.rabrg.range.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Area;

public final class ReturnNode extends Node {

	private static final Area RANGE_AREA = new Area(2667, 3415, 2675, 3421);

	public ReturnNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !RANGE_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		ctx.getWalking().walk(ctx.getNpcs().closest("Competition Judge"));
		return Calculations.random(700, 1100);
	}

	@Override
	public String getName() {
		return "Returning to range";
	}

}
