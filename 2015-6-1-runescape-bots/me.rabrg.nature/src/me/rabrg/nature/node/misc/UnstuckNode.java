package me.rabrg.nature.node.misc;

import org.tbot.methods.Camera;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.Tile;

import me.rabrg.nature.node.Node;

public final class UnstuckNode extends Node {

	private static final Area BANK_AREA = new Area(3091, 3488, 3098, 3499);

	private long nextMove = System.currentTimeMillis() + Random.nextInt(5000, 10000);
	private Tile lastTile;

	@Override
	public boolean validate() {
		return System.currentTimeMillis() >= nextMove && !Players.getLocal().isMoving() && Players.getLocal().getAnimation() < 1 && !ALTAR_AREA.contains(Players.getLocal()) && !BANK_AREA.contains(Players.getLocal());
	}

	@Override
	public int execute() {
		if (lastTile == null || (lastTile.getX() != Players.getLocal().getLocation().getX() || lastTile.getY() != Players.getLocal().getLocation().getY())) {
			lastTile = Players.getLocal().getLocation();
			nextMove = System.currentTimeMillis() + Random.nextInt(1500, 4500);
			return 10;
		}
		Camera.rotateAndTiltRandomly();
		for (int xOffset = -4; xOffset < 4; xOffset++) {
			for (int yOffset = -4; yOffset < 4; yOffset++) {
				final Tile tile = new Tile(Players.getLocal().getLocation().getX() + xOffset, Players.getLocal().getLocation().getY() + yOffset);
				if (Walking.canReach(tile)) {
					Walking.walkTileOnScreen(tile);
					nextMove = System.currentTimeMillis() + Random.nextInt(1500, 4500);
					return Random.nextInt(950, 1200);
				}
			}
		}
		nextMove = System.currentTimeMillis() + Random.nextInt(1500, 4500);
		return Random.nextInt(950, 1200);
	}

	@Override
	public String getName() {
		return "Unstuck check";
	}

}
