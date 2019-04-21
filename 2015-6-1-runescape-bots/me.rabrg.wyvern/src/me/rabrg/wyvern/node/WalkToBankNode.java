package me.rabrg.wyvern.node;

import org.tbot.methods.Bank;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.walking.Walking;

import static me.rabrg.wyvern.WyvernScript.*;

public final class WalkToBankNode extends Node {

	@Override
	public boolean validate() {
		return !Bank.isOpen() && VARROCK_SQUARE_AREA.contains(Players.getLocal()) && !BANK_AREA.contains(Players.getLocal()) && Walking.findPath(BANK_TILE).traverse();
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
		return "Walking to bank";
	}

}
