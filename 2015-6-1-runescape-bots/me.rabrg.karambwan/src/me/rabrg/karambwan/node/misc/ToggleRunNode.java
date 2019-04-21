package me.rabrg.karambwan.node.misc;

import org.tbot.methods.Random;
import org.tbot.methods.walking.Walking;

import me.rabrg.karambwan.node.Node;

public final class ToggleRunNode extends Node {

	private int amount = Random.nextInt(20, 40);

	@Override
	public boolean validate() {
		return !Walking.isRunEnabled() && Walking.getRunEnergy() > amount;
	}

	@Override
	public int execute() {
		Walking.setRun(true);
		amount = Random.nextInt(20, 40);
		return Random.nextInt(250, 750);
	}

	@Override
	public String getName() {
		return "Toggling run";
	}

}
