package me.rabrg.wyvern.node;

import org.tbot.methods.Players;
import org.tbot.methods.Random;

public final class WaitDeadNode extends Node {

	@Override
	public boolean validate() {
		return Players.getLocal().getInteractingEntity() != null && Players.getLocal().getInteractingEntity().isDead();
	}

	@Override
	public int execute() {
		
		return Random.nextInt(2250, 2750);
	}

	@Override
	public int priority() {
		return 7;
	}

	@Override
	public String getName() {
		return "Waiting for loot to drop";
	}

}
