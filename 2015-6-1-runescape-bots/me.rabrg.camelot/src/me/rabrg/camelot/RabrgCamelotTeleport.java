package me.rabrg.camelot;

import java.awt.Point;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;


@ScriptManifest(author="Rabrg", category= Category.THIEVING, name="Rabrg Camelot Teleport", version=0.1, description="Teleports to Camelot")
public final class RabrgCamelotTeleport extends AbstractScript {

	@Override
	public int onLoop() {
		if (!getInventory().contains("Law rune")) {
			stop();
			sleep(999999);
		} else if (!getTabs().isOpen(Tab.MAGIC))
			getTabs().open(Tab.MAGIC);
		else if (Calculations.random(1, 25) == 23)
				getMouse().move(new Point(690 + Calculations.random(-10, 10), 305 + Calculations.random(-10, 10)));
		else
			getMagic().castSpell(Normal.CAMELOT_TELEPORT);
		return Calculations.random(150, 1000);
	}

}
