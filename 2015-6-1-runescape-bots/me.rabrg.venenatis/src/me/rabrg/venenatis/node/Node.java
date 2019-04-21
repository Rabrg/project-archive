package me.rabrg.venenatis.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;

import me.rabrg.venenatis.Venenatis;

public abstract class Node {

	final Venenatis script;

	public Node(final Venenatis script) {
		this.script = script;
	}

	public abstract boolean validate();

	public abstract void execute();

	public abstract String toString();

	public static void sleepUntil(final Condition condition, final int timeout) {
		MethodProvider.sleepUntil(condition, timeout);
	}

	public static int random(final int min, final int max) {
		return Calculations.random(min, max);
	}
}
