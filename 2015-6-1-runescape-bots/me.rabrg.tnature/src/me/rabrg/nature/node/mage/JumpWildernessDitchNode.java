package me.rabrg.nature.node.mage;

import org.tbot.methods.Camera;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.GameObject;

import me.rabrg.nature.node.Node;

public final class JumpWildernessDitchNode extends Node {

	private GameObject ditch;

	@Override
	public boolean validate() {
		return Inventory.isFull() && Players.getLocal().getLocation().getY() <= 3520 && EDGEVILLE_WILDERNESS_AREA.contains(Players.getLocal()) && ((ditch = GameObjects.getNearest("Wilderness Ditch")) != null && ditch.distance() <= 5);
	}

	@Override
	public int execute() {
		if (Players.getLocal().getAnimation()!= 6132) {
			if (!ditch.isOnScreen()) {
				Camera.turnTo(ditch, 60);
				return Random.nextInt(350, 775);
			} else {
				if (ditch.interact("Cross"))
					return Random.nextInt(3550, 3750);
				WalkWildernessDitchNode.pathOne = Random.nextInt(1, 10) >= 7;
			}
		}
		return Random.nextInt(675, 1200);
	}

	@Override
	public String getName() {
		return "Crossing ditch";
	}

}
