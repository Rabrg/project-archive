package me.rabrg.nature.node.misc;

import org.tbot.methods.Random;
import org.tbot.methods.walking.Walking;

import me.rabrg.nature.node.Node;

public final class ToggleRunNode extends Node {

	private int nextRun = Random.nextInt(20, 55);

	@Override
	public boolean validate() {
		return Walking.isMoving() && Walking.getRunEnergy() >= nextRun && !Walking.isRunEnabled();
	}

	@Override
	public int execute() {
		Walking.setRun(true);
		nextRun = Random.nextInt(20, 55);
		return Random.nextInt(750, 1000);
	}

	@Override
	public String getName() {
		return "Toggling run";
	}

}
