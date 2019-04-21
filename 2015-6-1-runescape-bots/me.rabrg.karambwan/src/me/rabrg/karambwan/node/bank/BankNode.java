package me.rabrg.karambwan.node.bank;

import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.Bank;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Equipment;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.GameObject;

import me.rabrg.karambwan.RabrgKarambwan;
import me.rabrg.karambwan.node.Node;

public final class BankNode extends Node {
	@Override
	public boolean validate() {
		if (CASTLE_WARS_AREA.contains(Players.getLocal()) || DUEL_ARENA_BANK_AREA.contains(Players.getLocal())) {
			if ((Equipment.getItemInSlot(12) == null
					|| !Equipment.getItemInSlot(12).getName().startsWith("Ring of dueling"))
					&& !Inventory.contains("Ring of dueling(8")) {
				return true;
			}
			if (Inventory.contains("Raw karambwan")) {
				return true;
			}
			if (!Inventory.contains("Coins") || Inventory.getFirst("Coins").getStackSize() < 10000) {
				return true;
			}
			if (!Inventory.contains("Teleport to house")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int execute() {
		if ((Equipment.getItemInSlot(12) == null
				|| !Equipment.getItemInSlot(12).getName().startsWith("Ring of dueling"))
				&& Inventory.contains("Ring of dueling(8)") && Bank.isOpen()) {
			Bank.close();
			LogHandler.log("1");
		} else if ((Equipment.getItemInSlot(12) == null
				|| !Equipment.getItemInSlot(12).getName().startsWith("Ring of dueling"))
				&& Inventory.contains("Ring of dueling(8)") && !Bank.isOpen()) {
			Inventory.getFirst("Ring of dueling(8)").interact("Wear");
			LogHandler.log("2");
		} else if (!Bank.isOpen() && DUEL_ARENA_BANK_AREA.contains(Players.getLocal())) {
			GameObject chest;
			if ((chest = GameObjects.getNearest("Open chest")) != null) {
				chest.interact("Bank");
			} else if ((chest = GameObjects.getNearest("Closed chest")) != null) {
				chest.interact("Open");
			}
			LogHandler.log("3");
		} else if (!Bank.isOpen()) {
			Bank.openNearestBank();
			LogHandler.log("4");
		} else if (Inventory.contains("Raw karambwan")) {
			Bank.depositAllExcept(new String[] { "Coins" });
			RabrgKarambwan.karambwans += 27;
			LogHandler.log("5");
		} else if (!Inventory.contains("Coins") || Inventory.getFirst("Coins").getStackSize() < 10000) {
			Bank.withdraw("Coins", 50000);
			LogHandler.log("6");
		} else if (!Inventory.contains("Teleport to house")) {
			Bank.withdraw("Teleport to house", 1);
			LogHandler.log("7");
		} else if ((Equipment.getItemInSlot(12) == null
				|| !Equipment.getItemInSlot(12).getName().startsWith("Ring of dueling"))
				&& !Inventory.contains("Ring of dueling(8)")) {
			Bank.withdraw("Ring of dueling(8)", 1);
			LogHandler.log("8");
		}
		return Random.nextInt(875, 1275);
	}

	@Override
	public String getName() {
		return "Banking";
	}
}
