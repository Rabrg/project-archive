package me.rabrg.jiminua.node.shop;

import org.tbot.methods.Camera;
import org.tbot.methods.Game;
import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Shop;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.NPC;

import me.rabrg.jiminua.node.Node;

public final class OpenShopNode extends Node {

	private NPC jiminua;

	@Override
	public boolean validate() {
		return Game.isLoggedIn() && !Shop.isOpen() && (jiminua = Npcs.getNearest("Jiminua")) != null;
	}

	@Override
	public int execute() {
		if (jiminua.distance() > Random.nextInt(5, 8))
			Walking.walkTileMM(jiminua.getLocation(), 3, 3);
		else if (!jiminua.isOnScreen() && !jiminua.getLocation().equals(Players.getLocal().getLocation()))
			Camera.turnTo(jiminua);
		else if (!Walking.isMoving())
			jiminua.interact("Trade");
		return Random.nextInt(450, 1400);
	}

	@Override
	public String getName() {
		return "Opening shop";
	}
}
