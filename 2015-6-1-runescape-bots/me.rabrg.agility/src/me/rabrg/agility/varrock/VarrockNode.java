package me.rabrg.agility.varrock;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Area;

import me.rabrg.agility.Node;

abstract class VarrockNode extends Node {

	static final Area ROUGH_WALL_AREA = new Area(3150, 3350, 3350, 3550);
	static final Area CLOTHES_LINE_AREA = new Area(3213, 3409, 3220, 3420, 3);
	static final Area GAP_1_AREA = new Area(3201, 3413, 3208, 3418, 3);
	static final Area WALL_AREA = new Area(3194, 3416, 3420, 3416, 1);
	static final Area GAP_2_AREA = new Area(3192, 3402, 3198, 3407, 3);
	static final Area GAP_3_AREA = new Area(3182, 3382, 3208, 3403, 3);
	static final Area GAP_4_AREA = new Area(3218, 3393, 3232, 3402, 3);
	static final Area LEDGE_AREA = new Area(3236, 3403, 3240, 3408, 3);
	static final Area EDGE_AREA = new Area(3236, 3410, 3240, 3415, 3);

	public VarrockNode(final MethodContext ctx) {
		super(ctx);
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();

}
