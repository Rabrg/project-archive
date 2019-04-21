package me.rabrg.nature.node.abyss;

import org.tbot.methods.Camera;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.wrappers.GameObject;

import me.rabrg.nature.node.Node;

public final class EnterRiftNode extends Node {

	private GameObject rift;

	@Override
	public boolean validate() {
		return ABYSS_AREA.contains(Players.getLocal()) && (rift = GameObjects.getNearest("Nature rift")) != null
				&& rift.distance() <= 5;
	}

	@Override
	public int execute() {
		if (!rift.isOnScreen()) {
			Camera.turnTo(rift, 60);
		} else {
			rift.interact("Exit-through");
		}
		return Random.nextInt(450, 950);
	}

	@Override
	public String getName() {
		return "Entering rift";
	}

}
