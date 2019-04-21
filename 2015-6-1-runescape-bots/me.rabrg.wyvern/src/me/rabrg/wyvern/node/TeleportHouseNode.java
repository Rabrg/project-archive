package me.rabrg.wyvern.node;

import static me.rabrg.wyvern.WyvernScript.*;

import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Inventory;

public final class TeleportHouseNode extends Node {

	@Override
	public boolean validate() {
		return BANK_AREA.contains(Players.getLocal()) && Inventory.contains("Monkfish") && Inventory.getFirst("Teleport to house").interact("Break");
	}

	@Override
	public int execute() {
		return Random.nextInt(3250, 6175);
	}

	@Override
	public int priority() {
		return 5;
	}

	@Override
	public String getName() {
		return "Teleporting to house";
	}

}
