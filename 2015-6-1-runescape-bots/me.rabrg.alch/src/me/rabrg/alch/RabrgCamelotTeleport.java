package me.rabrg.alch;

import java.awt.Point;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

@ScriptManifest(author="Rabrg", category= Category.THIEVING, name="Rabrg Alch", version=0.1, description="Alchs your inventory")
public final class RabrgCamelotTeleport extends AbstractScript {

	@Override
	public int onLoop() {
		if (!getInventory().contains("Nature rune")) {
			stop();
			sleep(999999);
		} else {
			getMouse().click(new Point(703 + Calculations.random(2), 335 + (-1 * Calculations.random(2))));
		}
		return Calculations.random(1000, 1200);
	}
}
