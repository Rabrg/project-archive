package me.rabrg.rat.node;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Area;

public abstract class Node {

	public static final Area RAT_AREA = new Area(3227, 9857, 3278, 9873);

	public static final String BONES_NAME = "Bones";

	MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
