package me.rabrg.karambwan.node.house;

import org.tbot.methods.GameObjects;
import org.tbot.methods.Random;
import org.tbot.wrappers.GameObject;

import me.rabrg.karambwan.node.Node;

public final class EnterHousePortalNode extends Node {

	private GameObject portal;

	@Override
	public boolean validate() {
		return (portal = GameObjects.getNearest("Portal")) != null && portal.getID() != 15481;
	}

	@Override
	public int execute() {
		portal.interact("Enter");
		return Random.nextInt(1200, 1800);
	}

	@Override
	public String getName() {
		return "Entering house portal";
	}

}
