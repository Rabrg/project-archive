package me.rabrg.wyvern.node;

import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.walking.Walking;

import static me.rabrg.wyvern.WyvernScript.*;

public final class WalkToTrapdoorNode extends Node {
	
	@Override
	public boolean validate() {
		return RIMMINGTON_AREA.contains(Players.getLocal()) && !TRAPDOOR_AREA.contains(Players.getLocal()) && Walking.findPath(TRAPDOOR_TILE).traverse();
	}

	@Override
	public int execute() {
		return Random.nextInt(250, 650);
	}

	@Override
	public int priority() {
		return 5;
	}

	@Override
	public String getName() {
		return "Walking to trapdoor";
	}

}
