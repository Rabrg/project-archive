package me.rabrg.range.node;

import org.dreambot.api.methods.MethodContext;

public abstract class Node {

	static boolean ingame;
	static int count;

	final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
