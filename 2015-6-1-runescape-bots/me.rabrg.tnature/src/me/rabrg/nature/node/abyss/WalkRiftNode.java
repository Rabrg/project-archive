package me.rabrg.nature.node.abyss;

import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;

import me.rabrg.nature.node.Node;

public final class WalkRiftNode extends Node {

	private Path path;
	private GameObject rift;

	@Override
	public boolean validate() {
		return ABYSS_AREA.contains(Players.getLocal()) && ((rift = GameObjects.getNearest("Nature rift")) == null || rift.distance() > 5);
	}

	@Override
	public int execute() {
		path = Walking.findLocalPath(NATURE_RIFT);
		if (path != null)
			path.traverse();
		else
			for (;;) {
				final Tile tile = Players.getLocal().getLocation().getRandomized(1, 1);
				if (Walking.canReach(tile)) {
					Walking.walkTileOnScreen(tile);
					break;
				}
			}
		return Random.nextInt(650, 1250);
	}

	@Override
	public String getName() {
		return "Walking to rift";
	}

}
