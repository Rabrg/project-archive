package me.rabrg.jiminua.node.shop;

import org.tbot.methods.Random;
import org.tbot.methods.Shop;
import org.tbot.methods.ShopItem;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Item;

import me.rabrg.jiminua.RabrgJiminua;
import me.rabrg.jiminua.node.Node;

public final class SellItemNode extends Node {

	private Item nextItem;

	@Override
	public boolean validate() {
		return Shop.isOpen() && (nextItem = getNextItem()) != null;
	}

	@Override
	public int execute() {
		nextItem.interact("Sell 1");
		if (!RabrgJiminua.alchValues.containsKey(nextItem.getID())) {
			RabrgJiminua.profit += RabrgJiminua.alchValues.get(nextItem.getID() - 1);
		} else {
			RabrgJiminua.profit += RabrgJiminua.alchValues.get(nextItem.getID());
		}
		return Random.nextInt(675, 825);
	}

	@Override
	public String getName() {
		return "Selling items";
	}

	private static Item getNextItem() {
		int count = 0;
		for (int i = 0; i < 40; i++) {
			final ShopItem shopItem = Shop.getItemInSlot(i);
			if (shopItem == null || shopItem.getName() == null) 
				count++;
		}
		for (final Item item : Inventory.getItems()) {
			if (item.getName().equals("Coins") || item.getName().startsWith("Amulet of glory") || item.getName().contains("teleport"))
				continue;
			final ShopItem shopItem = Shop.getItem(item.getName());
			if ((shopItem != null && shopItem.getStackSize() < 2) || (count > 0 && shopItem == null)) {
				return item;
			}
		}
		return null;
	}
}
