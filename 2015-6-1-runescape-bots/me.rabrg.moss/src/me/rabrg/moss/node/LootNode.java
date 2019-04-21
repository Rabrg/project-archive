package me.rabrg.moss.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.GroundItem;

public final class LootNode extends Node {

	private GroundItem item;

	public LootNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (!ctx.getInventory().isFull() && (item = ctx.getGroundItems().closest("Big bones", "Air rune", "Cosmic rune", "Earth rune", "Nature rune", "Law rune", "Blood rune", "Chaos rune", "Death rune", "Uncut sapphire", "Uncut emerald", "Uncut ruby", "Nature rune", "Runite bar", "Rune spear", "Rune battleaxe", "Rune 2h sword", "Rune javelin", "Uncut diamond", "Rune sq shield", "Dragonstone", "Rune kiteshield")) != null) || ctx.getInventory().contains("Big bones");
	}

	@Override
	public int execute() {
		MethodProvider.log("1");
		if (ctx.getInventory().contains("Big bones")) {
			MethodProvider.log("2");
			ctx.getInventory().get("Big bones").interact();
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !ctx.getInventory().contains("Big bones");
				}
			}, Calculations.random(2400, 3600));
		} else if (item.interact()) {
			MethodProvider.log("3");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getGroundItems().closest(item.getName()) == null;
				}
			}, Calculations.random(2400, 3600));
		}
		return Calculations.random(0, 255);
	}

	@Override
	public String getName() {
		return "loot";
	}

}
