package me.rabrg.nature.node.bank;

import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.Bank;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Equipment;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Item;

import me.rabrg.nature.RabrgNature;
import me.rabrg.nature.node.Node;

public final class BankNode extends Node {

	@Override
	public boolean validate() {
		return EDGEVILLE_WILDERNESS_AREA.contains(Players.getLocal()) && (Skills.getCurrentLevel(Skill.Hitpoints) <= 14 ||(!Inventory.isFull() || ((!giantPouch && Inventory.contains("Giant pouch")) || (!largePouch && Inventory.contains("Large pouch")) || (!mediumPouch && Inventory.contains("Medium pouch")) || (!smallPouch && Inventory.contains("Small pouch")))));
	}

	@Override
	public int execute() {
		if (!Bank.isOpen() && Equipment.getItemInSlot(Equipment.SLOTS_NECK) != null && Equipment.getItemInSlot(Equipment.SLOTS_NECK).getName().equals("Amulet of glory")) {
			if (Inventory.isFull())
				Inventory.getFirst("Pure essence").interact("Drop");
			else
				Equipment.getItemInSlot(Equipment.SLOTS_NECK).interact("Remove");
			LogHandler.log(2);
		} else if (Bank.isOpen() && (Equipment.getItemInSlot(Equipment.SLOTS_NECK) == null && inventoryContainsChargedGlory() != null || Inventory.contains("Monkfish") || (Inventory.containsOneOf("Stamina potion(4)", "Stamina potion(3)", "Stamina potion(2)", "Stamina potion(1)") && trip % 2 == 0 && needsDrink) || (Inventory.isFull() && ((!giantPouch && Inventory.contains("Giant pouch")) || (!largePouch && Inventory.contains("Large pouch")) || (!mediumPouch && Inventory.contains("Medium pouch")) || (!smallPouch && Inventory.contains("Small pouch")))))) {
			Bank.close();
			LogHandler.log(18);
		} else if (Bank.isOpen() && Bank.isNoted()) {
			Bank.setNoted(false);
			LogHandler.log(17);
		} else if (!Bank.isOpen() && Equipment.getItemInSlot(Equipment.SLOTS_NECK) == null && inventoryContainsChargedGlory() != null) {
			inventoryContainsChargedGlory().interact("Wear");
			LogHandler.log(16);
		} else if (!Bank.isOpen() && Equipment.getItemInSlot(Equipment.SLOTS_RING) == null && Inventory.contains("Ring of life")) {
			Inventory.getFirst("Ring of life").interact("Wear");
			LogHandler.log(15);
		} else if (!Bank.isOpen() && Inventory.contains("Monkfish")) {
			Inventory.getFirst("Monkfish").interact("Eat");
			LogHandler.log(14);
		} else if (!Bank.isOpen() && Inventory.containsOneOf("Stamina potion(4)", "Stamina potion(3)", "Stamina potion(2)", "Stamina potion(1)") && trip % 2 == 0 && needsDrink) {
			if (Inventory.getFirst("Stamina potion(4)", "Stamina potion(3)", "Stamina potion(2)", "Stamina potion(1)").interact("Drink")) {
				needsDrink = false;
				RabrgNature.sips++;
			}
			LogHandler.log(13);
		} else if (!Bank.isOpen() && Inventory.contains("Pure essence")
				&& ((!giantPouch && Inventory.contains("Giant pouch") && Inventory.getCount("Pure essence") >= 12) || (!largePouch && Inventory.contains("Large pouch") && Inventory.getCount("Pure essence") >= 9) || (!mediumPouch && Inventory.contains("Medium pouch") && Inventory.getCount("Pure essence") >= 6) || (!smallPouch && Inventory.contains("Small pouch") && Inventory.getCount("Pure essence") >= 3))) {
			if (!giantPouch && Inventory.getCount("Pure essence") >= 12 && Inventory.contains("Giant pouch")) {
				if (Inventory.getFirst("Giant pouch").interact("Fill"))
					giantPouch = true;
			} else if (!largePouch && Inventory.getCount("Pure essence") >= 9 && Inventory.contains("Large pouch")) {
				if (Inventory.getFirst("Large pouch").interact("Fill"))
					largePouch = true;
			} else if (!mediumPouch && Inventory.getCount("Pure essence") >= 6 && Inventory.contains("Medium pouch")) {
				if (Inventory.getFirst("Medium pouch").interact("Fill"))
					mediumPouch = true;
			} else if (!smallPouch && Inventory.getCount("Pure essence") >= 3 && Inventory.contains("Small pouch")) {
				if (Inventory.getFirst("Small pouch").interact("Fill"))
					smallPouch = true;
			}
			LogHandler.log(12);
		} else if (!Bank.isOpen() && (!Inventory.isFull() || Skills.getCurrentLevel(Skill.Hitpoints) <= 14)) {
			Bank.openNearestBank();
			LogHandler.log(11);
		} else if (Bank.isOpen() && Inventory.contains("Nature rune")) {
			if (Bank.depositAll("Nature rune"))
				RabrgNature.crafted += Inventory.getCount("Nature rune");
			LogHandler.log(10);
		} else if (Bank.isOpen() && (Inventory.contains("Amulet of glory") || Inventory.containsOneOf("Vial", "Stamina potion(4)", "Stamina potion(3)", "Stamina potion(2)", "Stamina potion(1)"))) {
			Bank.depositAllExcept("Giant pouch", "Large pouch", "Medium pouch", "Small pouch");
			LogHandler.log(9);
		} else if (Bank.isOpen() && Equipment.getItemInSlot(Equipment.SLOTS_NECK) == null && !Inventory.contains("Amulet of glory(6)")) {
			Bank.withdraw("Amulet of glory(6)", 1);
			LogHandler.log(8);
		} else if (Bank.isOpen() && Equipment.getItemInSlot(Equipment.SLOTS_RING) == null && !Inventory.contains("Ring of life")) {
			if (Inventory.isFull()) {
				Bank.depositAll("Pure essence");
			} else {
				Bank.withdraw("Ring of life", 1);
			}
			LogHandler.log(7);
		} else  if (Bank.isOpen() && Skills.getCurrentLevel(Skill.Hitpoints) + 9 < Skills.getRealLevel(Skill.Hitpoints)) {
			if (Inventory.isFull()) {
				Bank.depositAll("Pure essence");
				Time.sleep(850, 1200);
			}
			Bank.withdraw(7946, (Skills.getRealLevel(Skill.Hitpoints) - Skills.getCurrentLevel(Skill.Hitpoints)) / 15 + 1);
			LogHandler.log(6);
		} else if (Bank.isOpen() && trip % 2 == 0 && needsDrink && !Inventory.containsOneOf("Stamina potion(4)", "Stamina potion(3)", "Stamina potion(2)", "Stamina potion(1)")) {
			if (!Bank.withdraw("Stamina potion(1)", 1))
				if (!Bank.withdraw("Stamina potion(2)", 1))
					if (!Bank.withdraw("Stamina potion(3)", 1))
						Bank.withdraw("Stamina potion(4)", 1);
			LogHandler.log(5);
		} else if (Bank.isOpen() && !Inventory.isFull()) {
			Bank.withdrawAll("Pure essence");
			LogHandler.log(4);
		} else if (Bank.isOpen()){
			Bank.close();
			LogHandler.log(3);
		}
		return Random.nextInt(925, 1000);
	}

	@Override
	public String getName() {
		return "Banking";
	}

	private Item inventoryContainsChargedGlory() {
		for (final Item item : Inventory.getItems()) {
			if (item.getName().startsWith("Amulet of glory(")) {
				return item;
			}
		}
		return null;
	}
}
