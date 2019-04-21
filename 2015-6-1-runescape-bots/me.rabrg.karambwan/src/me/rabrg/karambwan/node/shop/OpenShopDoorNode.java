package me.rabrg.karambwan.node.shop;

import org.tbot.methods.Camera;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.wrappers.GameObject;

import me.rabrg.karambwan.node.Node;

public final class OpenShopDoorNode extends Node {

	private GameObject door;

	@Override
	public boolean validate() {
		return !SHOP_AREA.contains(Players.getLocal())
				&& !(Players.getLocal().getLocation().getX() < 2783 && Players.getLocal().getLocation().getY() < 3060
						&& Players.getLocal().getLocation().getY() > 3055)
				&& Npcs.getNearest("Tiadeche") != null && (door = GameObjects.getNearest("Bamboo Door")) != null
				&& door.hasAction("Open");
	}

	@Override
	public int execute() {
		if (!door.isOnScreen()) {
			Camera.turnTo(door);
		} else {
			door.interact("Open");
		}
		return Random.nextInt(1200, 1500);
	}

	@Override
	public String getName() {
		return "Opening shop door";
	}

}
