package me.rabrg.karambwan.node.house;

import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Inventory;

import me.rabrg.karambwan.node.Node;

public final class TeleportHouseNode extends Node {

	@Override
	public boolean validate() {
		return CASTLE_WARS_AREA.contains(Players.getLocal()) || DUEL_ARENA_BANK_AREA.contains(Players.getLocal());
	}

	@Override
	public int execute() {
		Inventory.getFirst("Teleport to house").interact("Break");
		return Random.nextInt(4650, 4800);
	}

	@Override
	public String getName() {
		return "Teleporting to house";
	}

}
