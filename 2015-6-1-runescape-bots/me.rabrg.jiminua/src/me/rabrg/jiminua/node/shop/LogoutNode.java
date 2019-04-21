package me.rabrg.jiminua.node.shop;

import org.tbot.bot.TBot;
import org.tbot.methods.Game;
import org.tbot.methods.Random;
import org.tbot.methods.Shop;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Item;

import me.rabrg.jiminua.node.Node;

public final class LogoutNode extends Node {

	@Override
	public boolean validate() {
		return Game.isLoggedIn() && !hasItemsToSell();
	}

	@Override
	public int execute() {
		if (Shop.isOpen())
			Shop.close();
		else {
			Game.logout();
			TBot.getBot().getScriptHandler().stopScript();
		}
		return Random.nextInt(450, 1200);
	}

	@Override
	public String getName() {
		return "Logging out";
	}

	private static boolean hasItemsToSell() {
		for (final Item item : Inventory.getItems()) {
			if (!item.getName().equals("Coins") && !item.getName().startsWith("Amulet of glory") && !item.getName().equals("Varrock teleport")) { // varrock
				return true;
			}
		}
		return false;
	}
}
