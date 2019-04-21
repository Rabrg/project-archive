package me.rabrg.wyvern.node;

import static me.rabrg.wyvern.WyvernScript.*;

import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.wrappers.GameObject;

public final class EnterCaveNode extends Node {
	
	private static final String ENTER_ACTION = "Enter";
	
	@Override
	public boolean validate() {
		return CAVE_AREA.contains(Players.getLocal()) && !WYVERN_AREA.contains(Players.getLocal());
	}

	@Override
	public int execute() {
		for (final GameObject object : GameObjects.getAt(CAVE_TILE)) {
			if (object.hasAction(ENTER_ACTION))
				object.interact(ENTER_ACTION);
		}
		return Random.nextInt(2750, 4750);
	}

	@Override
	public int priority() {
		return 5;
	}

	@Override
	public String getName() {
		return "Entering cave";
	}

}
