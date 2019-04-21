package me.rabrg.varrock;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

@ScriptManifest(author = "Rabrg", category = Category.MISC, name = "Varrock Walker", version = 0)
public final class RabrgVarrockWalk extends AbstractScript {

	private static final Tile VARROCK_TILE = new Tile(3210, 3424);

	@Override
	public int onLoop() {
		if (VARROCK_TILE.distance() > 10) {
			getWalking().walk(VARROCK_TILE.getRandomizedTile(3));
		}
		return Calculations.random(700, 1700);
	}

}
