package me.rabrg.karambwan.node.bank;

import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Shop;
import org.tbot.methods.Widgets;
import org.tbot.methods.tabs.Equipment;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Locatable;

import me.rabrg.karambwan.node.Node;

public final class TeleportBankNode extends Node {
	@Override
	public boolean validate() {
		return (SHOP_AREA.contains(new Locatable[] { Players.getLocal() })
				|| Players.getLocal().getLocation().getX() < 2783 && Players.getLocal().getLocation().getY() < 3060
						&& Players.getLocal().getLocation().getY() > 3055)
				&& Inventory.isFull();
	}

	@Override
	public int execute() {
		if (Shop.isOpen()) {
			Shop.close();
			return Random.nextInt(600, 1200);
		} else {
			if (Widgets.getCurrentTab() != 4) {
				Widgets.openTab(4);
				return Random.nextInt(600, 1200);
			}
			Equipment.getItemInSlot(12).interact("Castle Wars");
		}
		return Random.nextInt(3000, 3600);
	}

	@Override
	public String getName() {
		return "Teleporting to bank";
	}
}
