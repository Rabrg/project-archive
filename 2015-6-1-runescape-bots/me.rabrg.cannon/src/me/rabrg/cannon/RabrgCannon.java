package me.rabrg.cannon;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.ScriptCategory;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Random;
import org.tbot.wrappers.GameObject;

@Manifest(authors = "Rabrg", name = "Rabrg Cannon", category = ScriptCategory.OTHER)
public final class RabrgCannon extends AbstractScript {

	private GameObject cannon;

	@Override
	public int loop() {
		if ((cannon = GameObjects.getNearest("Dwarf multicannon")) != null) {
			cannon.interact("Fire");
		}
		return Random.nextInt(20000, 35000);
	}

}
