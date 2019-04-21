package me.rabrg.agility;

import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(name = "Barbarian assault agility", author = "Rabrg", version = 0.1, info = "", logo = "")
public final class BarbarianAssaultAgilityScript extends Script {

	private static final Area FAILED_ROPESWING_AREA = new Area(2546, 9948, 2555, 9955);

	private static final String LADDER_OBJECT_NAME = "Ladder";
	private static final String CLIMB_UP_ACTION_NAME = "Climb-up";
	private static final String CLIMB_DOWN_ACTION_NAME = "Climb-down";

	private static final int ROPESWING_OBJECT_ID = 23131;
	private static final String SWING_ON_ACTION_NAME = "Swing-on";
	private static final String ROPESWING_SUCCESS_MESSAGE = "You skillfully swing across.";
	private static final String ROPESWING_FAIL_MESSAGE = "You slip and fall to the pit below.";

	private static final String LOG_BALANCE_OBJECT_NAME = "Log balance";
	private static final String WALK_ACROSS_ACTION_NAME = "Walk-across";
	private static final String LOG_BALANCE_SUCCESS_MESSAGE = "...You make it safely to the other side.";
	private static final String LOG_BALANCE_FAIL_MESSAGE = "Something in the water bites you.";

	private int part = 0;
	private boolean failed = false;

	@Override
	public int onLoop() throws InterruptedException {
		switch(getState()) {
		case ROPESWING:
			objects.closest(ROPESWING_OBJECT_ID).interact(SWING_ON_ACTION_NAME);
			break;
		case ROPESWING_FAILED:
			if (FAILED_ROPESWING_AREA.contains(myPlayer())) {
				objects.closest(LADDER_OBJECT_NAME).interact(CLIMB_UP_ACTION_NAME);
			} else {
				failed = false;
			}
		case LOG_BALANCE:
			objects.closest(LOG_BALANCE_OBJECT_NAME).interact(WALK_ACROSS_ACTION_NAME);
			break;
		}
		return random(1200, 3800);
	}

	@Override
	public void onMessage(final Message message) {
		switch (message.getMessage()) {
		case ROPESWING_SUCCESS_MESSAGE:
		case LOG_BALANCE_SUCCESS_MESSAGE:
			part++;
			failed = false;
			break;
		case ROPESWING_FAIL_MESSAGE:
		case LOG_BALANCE_FAIL_MESSAGE:
			failed = true;
			break;
		}
	}

	private State getState() {
		if (part == 0 && !failed) {
			return State.ROPESWING;
		} else if (part == 0 && failed) {
			return State.ROPESWING_FAILED;
		} else if (part == 1 && FAILED_ROPESWING_AREA.contains(myPlayer())) {
			return State.LOG_BALANCE;
		}
		return null;
	}

	private static enum State {
		ROPESWING,
		ROPESWING_FAILED,
		LOG_BALANCE
	}
}
