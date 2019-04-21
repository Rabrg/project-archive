package me.rabrg.karambwan.node.bank;

import org.tbot.methods.Bank;
import org.tbot.methods.Random;

import me.rabrg.karambwan.node.Node;

public final class CloseBankNode extends Node {
	@Override
	public boolean validate() {
		return Bank.isOpen();
	}

	@Override
	public int execute() {
		Bank.close();
		return Random.nextInt(350, 775);
	}

	@Override
	public String getName() {
		return "Closing bank";
	}
}
