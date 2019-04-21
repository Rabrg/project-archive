package me.rabrg.willowlog.node;

import org.dreambot.api.methods.MethodContext;

public abstract class Node {

	final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
