package me.rabrg.nature.node.altar;

import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Equipment;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Item;

import me.rabrg.nature.RabrgNature;
import me.rabrg.nature.node.Node;

public final class TeleportBankNode extends Node {

	private Item amulet;

	@Override
	public boolean validate() {
		return ALTAR_AREA.contains(Players.getLocal()) && emptyBesidesPouches()
				&& (amulet = Equipment.getItemInSlot(Equipment.SLOTS_NECK)) != null;
	}

	@Override
	public int execute() {
		if (!amulet.interact("Edgeville")) 
			amulet.interact("Edgeville");
		trip++;
		if (trip % 2 == 0)
			needsDrink = true;
		RabrgNature.crafted += Inventory.getCount("Nature rune");
		return Random.nextInt(3000, 3600);
	}

	@Override
	public String getName() {
		return "Teleporting Edgeville";
	}

	public boolean emptyBesidesPouches() {
		for (final Item item : Inventory.getItems()) {
			if (!item.getName().contains("pouch") && !item.getName().equals("Nature rune") && item.getName().contains("Stamina potion") && item.getName().contains("Vial")) {
				return false;
			}
		}
		return true;
	}
}
