package me.rabrg.superanti;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;

@ScriptManifest(author = "Rabrg", category = Category.MONEYMAKING, name = "Superantipoison", version = 0.1)
public final class Superantipoison extends AbstractScript {

	private static final Filter<Item> NONFULL_FILTER = new Filter<Item>() {
		@Override
		public boolean match(final Item item) {
			if (item == null)
				return false;
			switch (item.getName()) {
			case "Superantipoison(1)":
			case "Superantipoison(2)":
			case "Superantipoison(3)":
				return true;
			default:
				return false;
			}
		}
	};

	private GroundItem groundAnti;

	@Override
	public int onLoop() {
		if (getInventory().contains("Vial") && getInventory().get("Vial").interact("Drop")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !getInventory().contains("Vial");
				}
			}, Calculations.random(1200, 1800));
		} else if (getInventory().count(NONFULL_FILTER) > 1) {
			Item firstItem = null;
			for (final Item item : getInventory().all(NONFULL_FILTER)) {
				if (firstItem == null) {
					firstItem = item;
				} else if (firstItem != null && firstItem != item && firstItem.useOn(item)) {
					return Calculations.random(750, 900);
				}
			}
		} else if (!getInventory().isFull() && (groundAnti = getGroundItems().closest("Superantipoison(1)")) != null && groundAnti.interact("Take")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return getGroundItems().closest("Superantipoison(1)") == null;
				}
			}, Calculations.random(1200, 1800));
		} else if (!getInventory().isFull() && groundAnti == null && getWorldHopper().hopWorld(getWorlds().getRandomWorld(new Filter<World>() {
					@Override
					public boolean match(final World world) {
						return world.isMembers() && !world.isPVP();
					}
				}))) {
			return Calculations.random(300, 600);
		}
		return Calculations.random(300);
	}

}
