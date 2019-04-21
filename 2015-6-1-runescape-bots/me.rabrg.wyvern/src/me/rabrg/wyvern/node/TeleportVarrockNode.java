package me.rabrg.wyvern.node;

import static me.rabrg.wyvern.WyvernScript.*;

import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Inventory;

public final class TeleportVarrockNode extends Node {

	@Override
	public boolean validate() {
		return !Inventory.contains("Monkfish") && !Inventory.contains("Lobster") && (Players.getLocal().getHealthPercent() <= 35 || Inventory.isFull())&& !VARROCK_SQUARE_AREA.contains(Players.getLocal()) && !BANK_AREA.contains(Players.getLocal());
	}

	@Override
	public int execute() {
		if (Inventory.contains("Varrock teleport"))
			Inventory.getFirst("Varrock teleport").interact("Break");
		return Random.nextInt(3250, 6175);
	}

	@Override
	public int priority() {
		return 10;
	}

	@Override
	public String getName() {
		return "Teleporting to Varrock";
	}

}
