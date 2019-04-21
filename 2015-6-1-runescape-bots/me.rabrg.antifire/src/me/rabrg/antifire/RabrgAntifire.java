package me.rabrg.antifire;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(author="Rabrg", category= Category.COMBAT, name="Rabrg Antifire", version=0.1, description="Drinks antifires")
public final class RabrgAntifire extends AbstractScript {

	@Override
	public int onLoop() {
		return Calculations.random(25, 75);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().contains("about to expire")) {
			sleep(10000, 20000);
			getInventory().get(new Filter<Item>() {
				@Override
				public boolean match(final Item item) {
					return item.getName().contains("Antifire potion");
				}
			}).interact("Drink");
		}
	}
}
