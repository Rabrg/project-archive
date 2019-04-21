package me.rabrg.nature.node.abyss;

import org.tbot.methods.Camera;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;

import me.rabrg.nature.node.Node;

public final class WalkEntranceNode extends Node {

	private GameObject entrance;
	private Path path;

	@Override
	public boolean validate() {
		return ABYSS_AREA.contains(Players.getLocal()) && !Camera.isLocked() && !Walking.canReach(NATURE_RIFT) && ((entrance = GameObjects.getNearest(25422, 25428)) == null || entrance.distance() > 9);
	}

	@Override
	public int execute() {
		path = null;
		if (entrance != null) {
			for (int xOffset = -4; xOffset < 4; xOffset++) {
				for (int yOffset = -4; yOffset < 4; yOffset++) {
					final Tile tile = new Tile(entrance.getLocation().getX() + xOffset, entrance.getLocation().getY() + yOffset);
					if (Walking.canReach(tile)) {
						path = Walking.findLocalPath(tile);
						break;
					}
				}
			}
			if (path != null)
				path.traverse();
			else
				Walking.walkTileMM(Players.getLocal().getLocation(), 1, 1);
		} else {
			Walking.walkTileMM(Players.getLocal().getLocation(), 1, 1);
		}
		return Random.nextInt(650, 1250);
	}

	public static Path findPath(final GameObject object, final int radius) {
		for (int xOffset = -radius; xOffset < radius; xOffset++) {
			for (int yOffset = -radius; yOffset < radius; yOffset++) {
				final Tile tile = new Tile(object.getLocation().getX() + xOffset, object.getLocation().getY() + yOffset);
				if (Walking.canReach(tile)) {
					return Walking.findLocalPath(tile);
				}
			}
		}
		return null;
	}

	public static Tile findTile(final GameObject object, final int radius) {
		for (int xOffset = -radius; xOffset < radius; xOffset++) {
			for (int yOffset = -radius; yOffset < radius; yOffset++) {
				final Tile tile = new Tile(object.getLocation().getX() + xOffset, object.getLocation().getY() + yOffset);
				if (Walking.canReach(tile)) {
					return tile;
				}
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return "Walking to entrance";
	}

}
