package me.rabrg.wyvern.node;

import static me.rabrg.wyvern.WyvernScript.*;

import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.walking.Walking;

public final class WalkToCaveNode extends Node {

	@Override
	public boolean validate() {
		return !RIMMINGTON_AREA.contains(Players.getLocal()) && !CAVE_AREA.contains(Players.getLocal()) && !WYVERN_AREA.contains(Players.getLocal()) && !BANK_AREA.contains(Players.getLocal()) && !VARROCK_SQUARE_AREA.contains(Players.getLocal()) && Walking.findPath(CAVE_AREA_TILE).traverse();
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
		return "Walking to cave";
	}

}
