package me.rabrg.pickpocket;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

@ScriptManifest(author = "Rabrg", category = Category.THIEVING, name = "Pickpocket", version = 0)
public final class Pickpocket extends AbstractScript {

	private NPC target;

	@Override
	public int onLoop() {
		if (getSkills().getBoostedLevels(Skill.HITPOINTS) <= 20) {
			final Item food = getInventory().get("Monkfish");
			if (food != null && food.interact("Eat")) {
				sleep(600, 1200);
			} else if (food == null) {
				getTabs().logout();
				stop();
			}
		} else if ((target = getNpcs().closest("Warrior woman")) != null && getMap().canReach(target) && getLocalPlayer().getAnimation() < 1 && target.interact("Pickpocket")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return getLocalPlayer().getAnimation() > 1;
				}
			}, Calculations.random(2400, 3600));
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return getLocalPlayer().getAnimation() < 1;
				}
			}, Calculations.random(2400, 3600));
			sleep(300, 600);
		}
		return Calculations.random(0, 512);
	}

}
