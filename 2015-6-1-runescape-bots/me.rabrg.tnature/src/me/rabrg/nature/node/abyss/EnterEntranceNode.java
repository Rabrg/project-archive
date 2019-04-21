package me.rabrg.nature.node.abyss;

import org.tbot.methods.Camera;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.GameObject;

import me.rabrg.nature.node.Node;

public final class EnterEntranceNode extends Node {

	private GameObject entrance;

	@Override
	public boolean validate() {
		return ABYSS_AREA.contains(Players.getLocal()) && !Walking.canReach(NATURE_RIFT)
				&& (entrance = GameObjects.getNearest(25422, 25428)) != null && entrance.distance() <= 9;
	}

	@Override
	public int execute() {
		if (!entrance.isOnScreen()) {
			Walking.walkTileMM(entrance.getLocation(), 1, 1);
			Camera.turnTo(entrance, 60);
		} else {
			if (entrance.getName().equals("Rock")) {
				entrance.interact("Mine");
			} else if (entrance.getName().equals("Gap")) {
				entrance.interact("Squeeze-through");
			}
		}
		return Random.nextInt(250, 550);
	}

	@Override
	public String getName() {
		return "Entering entrance";
	}

}
