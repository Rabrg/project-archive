package me.rabrg.moss.node;

import org.dreambot.api.methods.MethodContext;

public abstract class Node {

	MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
