package me.rabrg.hillgiants.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;

public final class LootNode extends Node {

	private static final String[] LOOT = { "Big bones", "Long bone", "Curved bone", "Limpwurt root", "Champion scroll", "Grimy ranarr weed" };

	private GroundItem groundItem;
	private Item food;

	public LootNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getLocalPlayer().isInCombat() && (!ctx.getInventory().isFull() || (food = ctx.getInventory().get(FOOD_FILTER)) != null) && (groundItem= ctx.getGroundItems().closest(LOOT)) != null;
	}

	@Override
	public int execute() {
		if (ctx.getInventory().isFull() && food != null && food.interact("Eat")) {
			return Calculations.random(600, 900);
		} else if (groundItem.interact("Take")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(0, 300);
					return !groundItem.exists();
				}
			}, ctx.getWalking().isRunEnabled() ? Calculations.random(3600, 4200) : Calculations.random(6000, 7200));
		} else if (ctx.getWalking().walk(groundItem)){
			return Calculations.random(600, 1200);
		}
		return Calculations.random(0, 300);
	}

	@Override
	public String toString() {
		return "Looting";
	}

}
