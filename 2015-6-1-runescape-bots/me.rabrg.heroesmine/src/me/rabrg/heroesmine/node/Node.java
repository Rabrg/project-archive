package me.rabrg.heroesmine.node;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Area;

public abstract class Node {

	static final Area MINE_AREA = new Area(2880, 9883, 2984, 9964);
	static final Area CASTLEWARS_AREA = new Area(2438, 3082, 2444, 3098);
	static final Area ABOVEGROUND_AREA = new Area(2875, 3500, 2920, 3575);

	final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
