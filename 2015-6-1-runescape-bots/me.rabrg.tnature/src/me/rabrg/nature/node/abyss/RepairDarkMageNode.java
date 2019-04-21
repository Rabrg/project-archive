package me.rabrg.nature.node.abyss;

import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.NPC;

import me.rabrg.nature.node.Node;

public final class RepairDarkMageNode extends Node {

	private NPC darkMage;

	@Override
	public boolean validate() {
		return ABYSS_AREA.contains(Players.getLocal()) && Inventory.containsOneOf(5515, 5513, 5511) && (darkMage = Npcs.getNearest("Dark mage")) != null && darkMage.distance() < 5;
	}

	@Override
	public int execute() {
		darkMage.interact("Repairs");
		return Random.nextInt(900, 1450);
	}

	@Override
	public String getName() {
		return "Repairing pouches";
	}

}
