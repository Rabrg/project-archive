package me.rabrg.karambwan.node.shop;

import org.tbot.methods.Game;
import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Shop;
import org.tbot.methods.ShopItem;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.NPC;

import me.rabrg.karambwan.node.Node;

public final class ShopNode extends Node {

	private NPC tiadeche;
	private ShopItem karambwan;

	@Override
	public boolean validate() {
		return (SHOP_AREA.contains(Players.getLocal()) || Players.getLocal().getLocation().getX() < 2783
				&& Players.getLocal().getLocation().getY() < 3060 && Players.getLocal().getLocation().getY() > 3055)
				&& !Inventory.isFull() && (tiadeche = Npcs.getNearest("Tiadeche")) != null;
	}

	@Override
	public int execute() {
		if (!Shop.isOpen()) {
			tiadeche.interact("Trade");
		} else if ((karambwan = Shop.getItem("Raw karambwan")) != null && karambwan.getStackSize() > 0) {
			karambwan.interact("Buy 10");
		} else {
			Game.instaHopRandomP2P();
		}
		return Random.nextInt(600, 1250);
	}

	@Override
	public String getName() {
		return "Buying karambwan";
	}

}
