package me.rabrg.warriortoken;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;
@ScriptManifest(author = "Rabrg", category = Category.MISC, name = "Warrior Token", version = 0)
public final class WarriorToken extends AbstractScript {

	@Override
	public int onLoop() {
		if (getWalking().getRunEnergy() < 50) {
			getInventory().get(new Filter<Item>() {

				@Override
				public boolean match(Item arg0) {
					return arg0 != null && arg0.getName().toLowerCase().contains("energy");
				}
				
			}).interact("Drink");
			sleep(512, 1024);
		} else if (getDialogues().inDialogue() && getDialogues().getOptions() != null && getDialogues().getOptions().length > 1 && getDialogues().chooseOption(3)) {
			sleep(4800, 5200);
		} else {
			final GameObject shot = getGameObjects().closest("Shot");
			if (shot != null && shot.interact("Throw"))
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getDialogues().getOptions() != null && getDialogues().getOptions().length > 1;
					}
				}, Calculations.random(4200, 5600));
		}
		return Calculations.random(0, 512);
	}

}
