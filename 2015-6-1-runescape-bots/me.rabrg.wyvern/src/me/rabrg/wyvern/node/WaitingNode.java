package me.rabrg.wyvern.node;

import org.tbot.methods.Random;

public final class WaitingNode extends Node {

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public int execute() {
		return Random.nextInt(5, 125);
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public String getName() {
		return "Waiting";
	}

}
