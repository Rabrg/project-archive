package me.rabrg.fallador;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

@ScriptManifest(author = "Rabrg", category = Category.MISC, name = "Falador Walker", version = 0)
public final class RabrgFalladorWalk extends AbstractScript {

	private static final Tile FALLADOR_TILE = new Tile(2964, 3379);

	@Override
	public int onLoop() {
		if (FALLADOR_TILE.distance() > 10) {
			getWalking().walk(FALLADOR_TILE.getRandomizedTile(3));
		}
		return Calculations.random(700, 1700);
	}

}
