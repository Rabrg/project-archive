package me.rabrg.fire.node;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Area;

public abstract class Node {

	static final Area CASTLE_WARS_AREA = new Area(2435, 3082, 2464, 3103);
	static final Area FIRE_ALTAR_AREA = new Area(2569, 4827, 2592, 4860);

	MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public static boolean smallPouch, mediumPouch;

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
