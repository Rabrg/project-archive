package me.rabrg.nature.node.misc;

import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Equipment;

import me.rabrg.nature.node.Node;

public final class FailSafeNode extends Node {

	private long lastFail = System.currentTimeMillis();
	private int fails = 0;

	@Override
	public boolean validate() {
		return !EDGEVILLE_WILDERNESS_AREA.contains(Players.getLocal()) && !ABYSS_AREA.contains(Players.getLocal()) && !ALTAR_AREA.contains(Players.getLocal());
	}

	@Override
	public int execute() {
		if (System.currentTimeMillis() - lastFail > 60000)
			fails = 0;
		fails++;
		if (fails >= 5) {
			Equipment.getItemInSlot(Equipment.SLOTS_NECK).interact("Edgeville");
		}
		lastFail = System.currentTimeMillis();
		return Random.nextInt(3000, 3600);
	}

	@Override
	public String getName() {
		return "Fail safe teleporting";
	}

}
