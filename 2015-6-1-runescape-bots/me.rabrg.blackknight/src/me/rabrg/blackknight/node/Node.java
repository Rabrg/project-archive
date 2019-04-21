package me.rabrg.blackknight.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;

public abstract class Node {

	static int nextRecharge = getNextRecharge();

	final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();

	static int getNextRecharge() {
		return Calculations.random(6, 12);
	}
}
