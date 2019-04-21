package me.rabrg.fireorb.node;

import org.tbot.methods.Bank;
import org.tbot.methods.Camera;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Time;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Item;

public final class ClimbLadderNode extends Node {

	private static final Area ABOVE_GROUND_AREA = new Area(2875, 3390, 2907, 3478);
	private static final Area LADDER_AREA = new Area(2881, 3396, 2887, 3400);

	private static final int LADDER = 16680;
	private static final String CLIMB_DOWN = "Climb-down";
	private Path path;
	private GameObject ladder;
	private Item teleport;

	@Override
	public boolean validate() {
		return !Bank.isOpen() && (ABOVE_GROUND_AREA.contains(Players.getLocal()) || (CLAN_WARS_AREA.contains(Players.getLocal()) && Inventory.contains(UNPOWERED_ORB) && Inventory.getFirst(ANTIPOISON_FILTER) != null && Inventory.isFull() && !Inventory.contains(RING_OF_DUELING_MAX)  && (Skills.getCurrentLevel(Skill.PRAYER) >= 20 && Skills.getCurrentLevel(Skill.HITPOINTS) >= Skills.getRealLevel(Skill.HITPOINTS) - 5 && Walking.getRunEnergy() >= 60)));
	}

	@Override
	public void execute() {
		if ((ladder = GameObjects.getNearest(LADDER)) != null && ladder.distance() < 7) {
			if (!ladder.isOnScreen())
				Camera.turnTo(ladder, 60);
			else if (ladder.interact(CLIMB_DOWN)) {
				path = null;
				ladder = null;
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						return !ABOVE_GROUND_AREA.contains(Players.getLocal());
					}
				}, Random.nextInt(2400, 3600));
			}
		} else if (!ABOVE_GROUND_AREA.contains(Players.getLocal()) && (teleport = Inventory.getFirst(HOUSE_TELEPORT)) != null && teleport.interact("Break")) {
			Time.sleepUntil(new Condition() {
				@Override
				public boolean check() {
					return ABOVE_GROUND_AREA.contains(Players.getLocal());
				}
			}, Random.nextInt(4200, 4800));
		} else if (path == null)
			path = Walking.findLocalPath(LADDER_AREA.getTileArray()[Random.nextInt(LADDER_AREA.getTileArray().length)]);
		else if (path != null && path.traverse())
			Time.sleep(0, 1800);
		else
			path = Walking.findLocalPath(LADDER_AREA.getTileArray()[Random.nextInt(LADDER_AREA.getTileArray().length)]);
	}

	@Override
	public String toString() {
		return "Climbing down ladder";
	}

}
