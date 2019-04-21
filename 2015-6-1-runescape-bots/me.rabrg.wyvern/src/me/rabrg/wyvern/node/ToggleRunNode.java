package me.rabrg.wyvern.node;

import org.tbot.methods.Random;
import org.tbot.methods.walking.Walking;

public final class ToggleRunNode extends Node {

	@Override
	public boolean validate() {
		return !Walking.isRunEnabled() && Walking.getRunEnergy() > 25;
	}

	@Override
	public int execute() {
		Walking.setRun(true);
		return Random.nextInt(250, 750);
	}

	@Override
	public int priority() {
		return 6;
	}

	@Override
	public String getName() {
		return "Toggling run";
	}

}
