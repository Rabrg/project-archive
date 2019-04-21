package me.rabrg.wyvern.node;

import static me.rabrg.wyvern.WyvernScript.*;

import org.tbot.methods.Bank;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Inventory;

public final class BankNode extends Node {

	@Override
	public boolean validate() {
		return (Bank.isOpen() || (BANK_AREA.contains(Players.getLocal())) && ((!Inventory.contains("Monkfish") || !Inventory.isFull())));
	}

	@Override
	public int execute() {
		if (!Bank.isOpen())
			Bank.openNearestBank();
		else {
			if (Inventory.isFull() && Inventory.contains("Monkfish"))
				Bank.close();
			else if (Inventory.containsOneOf(LOOT))
				Bank.depositAllExcept("Teleport to house", "Varrock teleport", "Super combat potion(4)", "Super combat potion(3)", "Super combat potion(2)", "Super combat potion(1)");
			else
				Bank.withdrawAll("Monkfish");
		}
		return Random.nextInt(250, 650);
	}

	@Override
	public int priority() {
		return 6;
	}

	@Override
	public String getName() {
		return "Banking";
	}

}
