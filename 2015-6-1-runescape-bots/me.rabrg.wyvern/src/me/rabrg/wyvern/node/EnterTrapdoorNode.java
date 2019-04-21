package me.rabrg.wyvern.node;

import static me.rabrg.wyvern.WyvernScript.*;

import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.wrappers.GameObject;

public final class EnterTrapdoorNode extends Node {
	
	private static final String CLIMB_DOWN_ACTION = "Climb-down";
	
	@Override
	public boolean validate() {
		return TRAPDOOR_AREA.contains(Players.getLocal());
	}

	@Override
	public int execute() {
		for (final GameObject object : GameObjects.getAt(TRAPDOOR_TILE)) {
			if (object.hasAction(CLIMB_DOWN_ACTION))
				object.interact(CLIMB_DOWN_ACTION);
		}
		return Random.nextInt(1500, 3000);
	}

	@Override
	public int priority() {
		return 5;
	}

	@Override
	public String getName() {
		return "Climbing down trapdoor";
	}

}
