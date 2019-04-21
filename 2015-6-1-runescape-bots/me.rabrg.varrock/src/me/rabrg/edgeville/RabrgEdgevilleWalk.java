package me.rabrg.edgeville;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

@ScriptManifest(author = "Rabrg", category = Category.MISC, name = "Edgeville Walker", version = 0)
public final class RabrgEdgevilleWalk extends AbstractScript {

	private static final Tile EDGEVILLE_TILE = new Tile(3093, 3493);

	@Override
	public int onLoop() {
		if (EDGEVILLE_TILE.distance() > 10) {
			getWalking().walk(EDGEVILLE_TILE.getRandomizedTile(3));
		}
		return Calculations.random(700, 1700);
	}

}
