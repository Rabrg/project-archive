package me.rabrg.earth.node;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Area;

public abstract class Node {

	static final Area ALTAR_AREA = new Area(2640, 4820, 2680, 4860);

	final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();
}
