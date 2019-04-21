package me.rabrg.enchantingchamber.node;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Area;

public abstract class Node {

	static final Area OUTSIDE_AREA = new Area(3353, 3295, 3373, 3334);
	static final Area INSIDE_AREA = new Area(3343, 9619, 3384, 9660);

	public static int enchants = 0;

	static boolean needHop;

	final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
