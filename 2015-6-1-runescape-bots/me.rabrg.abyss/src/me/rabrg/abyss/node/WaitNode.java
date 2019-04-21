package me.rabrg.abyss.node;

import org.dreambot.api.methods.MethodProvider;

import me.rabrg.abyss.RabrgAbyssPro;

public final class WaitNode extends Node {

	public WaitNode(final RabrgAbyssPro script) {
		super(script);
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute() {
		MethodProvider.sleep(25);
	}

	@Override
	public String toString() {
		return "Wait";
	}

}
