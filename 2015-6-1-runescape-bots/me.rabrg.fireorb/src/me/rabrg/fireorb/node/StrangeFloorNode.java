package me.rabrg.fireorb.node;

import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.Camera;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;

public final class StrangeFloorNode extends Node {

	private static final Area STRANGE_FLOOR_AREA = new Area(2880, 9794, 2887, 9821);

	private static final String STRANGE_FLOOR = "Strange floor";
	private static final String JUMP_OVER = "Jump-over";

	private GameObject strangeFloor;
	private Path path;

	@Override
	public boolean validate() {
		return STRANGE_FLOOR_AREA.contains(Players.getLocal());
	}

	@Override
	public void execute() {
		LogHandler.log(1);
		if ((strangeFloor = GameObjects.getNearest(STRANGE_FLOOR)) != null) {
			LogHandler.log(2);
			if (strangeFloor.distance() > 6) {
				LogHandler.log(3);
				if (path == null) {
					path = Walking.findLocalPath(new Tile(2882, 9813));
				}
				if (path != null && path.traverse()) {
					path.traverse();
				} else {
					path = Walking.findLocalPath(new Tile(2882, 9813));
				}
				Time.sleep(0, 1800);
			} else if (!strangeFloor.isOnScreen()) {
				LogHandler.log(4);
				Camera.turnTo(strangeFloor, 60);
			} else if (strangeFloor.interact(JUMP_OVER)) {
				path = null;
				LogHandler.log(5);
				strangeFloor = null;
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						return !STRANGE_FLOOR_AREA.contains(Players.getLocal());
					}
				}, Random.nextInt(4800, 6000));
			}
		}
	}

	@Override
	public String toString() {
		return "Jumping over strange floor";
	}

}
