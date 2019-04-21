package me.rabrg.nature.node.altar;

import org.tbot.methods.Camera;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.GameObject;

import me.rabrg.nature.node.Node;

public final class CraftRuneNode extends Node {

	private GameObject altar;

	@Override
	public boolean validate() {
		return ALTAR_AREA.contains(Players.getLocal())
				&& (Inventory.contains("Pure essence") || giantPouch || largePouch || mediumPouch || smallPouch)
				&& (altar = GameObjects.getNearest("Altar")) != null;
	}

	@Override
	public int execute() {
		if (!altar.isOnScreen()) {
			Camera.turnTo(altar, 60);
		} else if (giantPouch && Inventory.contains("Giant pouch") && Inventory.getEmptySlots() >= 12) {
			if (Inventory.getFirst("Giant pouch").interact("Empty"))
				giantPouch = false;
		} else if (largePouch && Inventory.contains("Large pouch") && Inventory.getEmptySlots() >= 9) {
			if (Inventory.getFirst("Large pouch").interact("Empty"))
				largePouch = false;
		} else if (mediumPouch && Inventory.contains("Medium pouch") && Inventory.getEmptySlots() >= 6) {
			if (Inventory.getFirst("Medium pouch").interact("Empty"))
				mediumPouch = false;
		} else if (smallPouch && Inventory.contains("Small pouch") && Inventory.getEmptySlots() >= 3) {
			if (Inventory.getFirst("Small pouch").interact("Empty"))
				smallPouch = false;
		} else {
			altar.interact("Craft-rune");
			return Random.nextInt(3000, 3600);
		}
		return Random.nextInt(825, 875);
	}

	@Override
	public String getName() {
		return "Crafting runes";
	}

}
