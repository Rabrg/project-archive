package me.rabrg.fireorb.node;

import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Time;
import org.tbot.methods.Skills.Skill;

import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;

public final class ClanWarsNode extends Node {

	@Override
	public boolean validate() {
		return (CLAN_WARS_AREA.contains(Players.getLocal()) && (Skills.getCurrentLevel(Skill.PRAYER) < 20 || Skills.getCurrentLevel(Skill.HITPOINTS) < Skills.getRealLevel(Skill.HITPOINTS) - 5 || Walking.getRunEnergy() < 60)) || ((portal = GameObjects.getNearest("Portal")) != null && portal.distance() <= 2);
	}

	private GameObject portal;
	private Path path;

	@Override
	public void execute() {
		LogHandler.log(1);
		if ((portal = GameObjects.getNearest("Free-for-all portal")) != null && portal.distance() > 5) {
			LogHandler.log(2);
			if (path == null)
				path = Walking.findLocalPath(new Tile(3352, 3164));
			if (path != null && path.traverse()) {
				Time.sleep(0, 1800);
			} else {
				path = Walking.findLocalPath(new Tile(3352, 3164));
			}
		} else if (portal != null && portal.distance() <= 5 && portal.interact("Enter")) {
			LogHandler.log(3);
			path = null;
			Time.sleepUntil(new Condition() {
				@Override
				public boolean check() {
					return (portal = GameObjects.getNearest("Free-for-all portal")) == null;
				}
			}, Random.nextInt(2400, 3600));
		} else if ((portal = GameObjects.getNearest("Portal")) != null && portal.distance() <= 2 && portal.interact("Exit")) {
			LogHandler.log(4);
			Time.sleepUntil(new Condition() {
				@Override
				public boolean check() {
					return (portal = GameObjects.getNearest("Portal")) == null || portal.distance() > 2;
				}
			}, Random.nextInt(2400, 3600));
			Time.sleep(600, 900);
		}
	}

	@Override
	public String toString() {
		return "Clan wars portal";
	}

}
