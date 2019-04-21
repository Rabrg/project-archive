package me.rabrg.combat.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.wrappers.items.GroundItem;

public final class LootNode extends Node {

	private static final String[] LOOT = { "Grimy ranarr weed", "Dragon defender", "Clue scroll (hard)", "Clue scroll (elite)", "Rune full helm", "Rune med helm", "Dragon bones", "Blue dragonhide", "Nature rune", "Water rune", "Rune dagger", "Mystic hat", "Mystic hat (light)", "Dwarf weed", "Kwuarm", "Basilisk head", "Uncut diamond", "Uncut sapphire", "Loop half of key", "Tooth half of key", "Uncut emerald", "Uncut ruby", "Runite bar", "Rune battleaxe", "Rune 2h sword", "Rune sq shield", "Death rune", "Dragonstone", "Rune kiteshield", "Dragon med helm", "Shield left half", "Dragon spear", "Rune scimitar", "Fire battlestaff", "Fire rune", "Chaos rune", "Blood rune", "Law rune", "Rune arrow", "Ranarr weed", "Long bone", "Curved bone", "Lobster" };

	private GroundItem item;

	public LootNode(final MethodContext ctx) {
		super(ctx);
	}

	// IS IN COMBAT
	@Override
	public boolean validate() {
		return !ctx.getLocalPlayer().isInCombat() && (!ctx.getInventory().isFull() || /*ctx.getInventory().contains("Monkfish")*/ false) && (item = ctx.getGroundItems().closest(LOOT)) != null && item.distance() < 10;
	}

	@Override
	public int execute() {
		if (item.distance() > 8)
			ctx.getWalking().walk(item.getTile());
		else if (!item.isOnScreen())
			ctx.getCamera().rotateToEntity(item);
		else
			/*if (ctx.getInventory().isFull() && !(ctx.getInventory().contains(item.getID()) && ctx.getInventory().get(item.getID()).isStackable()))
				ctx.getInventory().get("Monkfish").interact("Eat");
			else
				item.interact("Take");*/
			item.interact("Take");
		return Calculations.random(1000, 1800);
	}

	@Override
	public String getName() {
		return "Looting items";
	}

}
