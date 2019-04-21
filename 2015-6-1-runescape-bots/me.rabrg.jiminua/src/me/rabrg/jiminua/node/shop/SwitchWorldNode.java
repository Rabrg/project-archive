package me.rabrg.jiminua.node.shop;

import org.tbot.methods.Game;
import org.tbot.methods.Random;

import me.rabrg.jiminua.RabrgJiminua;
import me.rabrg.jiminua.node.Node;

public final class SwitchWorldNode extends Node {

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public int execute() {
		Game.instaHop(RabrgJiminua.getNextWorld());
		return Random.nextInt(25, 125);
	}

	@Override
	public String getName() {
		return "Switching worlds";
	}
}
