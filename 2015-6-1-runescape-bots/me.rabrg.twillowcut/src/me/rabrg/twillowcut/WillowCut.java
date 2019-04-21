package me.rabrg.twillowcut;

import java.awt.Graphics;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Camera;
import org.tbot.methods.Game;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Time;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;

@Manifest(name = "Willow Cutter")
public final class WillowCut extends AbstractScript implements PaintListener {

	private long startTime;
	private int startLevel;
	private int startXp;

	private long getElapsed() {
		return System.currentTimeMillis() - startTime;
	}

	private int getPerHour(final double value) {
		if (getElapsed() > 0) {
			return (int) (value * 3600000d / getElapsed());
		} else {
			return 0;
		}
	}

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startLevel = Skills.getRealLevel(Skill.Woodcutting);
		startXp = Skills.getExperience(Skill.Woodcutting);
		return true;
	}

	private GameObject spot;
	private Tile spotTile;

	private boolean empty = true;

	@Override
	public int loop() {
		if (Players.getLoaded().length > 1) {
			Game.instaHopRandomP2P();
			return 3600;
		} else {
			spot = GameObjects.getNearest(new Filter<GameObject>() {
				@Override
				public boolean accept(final GameObject object) {
					return object.getID() == 11755;
				}
			});
			if (Inventory.isFull() || !empty) {
				Inventory.dropAllExcept(6739);
				if (Inventory.getEmptySlots() < 27) {
					empty = false;
				} else { 
					empty = true;
				}
			} else if (Players.getLocal().getAnimation() < 1 || !spot.getLocation().equals(spotTile)) {
				if (!spot.isOnScreen()) {
					Camera.turnTo(spot);
				} else if (spot.interact("Chop down")) {
					spotTile = spot.getLocation();
					Time.sleepUntil(new Condition() {
						@Override
						public boolean check() {
							return Players.getLocal().getAnimation() > 1;
						}
					}, Random.nextInt(2048, 8192));
					if (Random.nextInt(1, 100) > 99) {
						Camera.rotateAndTiltRandomly();
					}
				}
			}
			return Random.nextInt(0, 512);
		}
	}

	@Override
	public void onRepaint(final Graphics g) {
		g.drawString("Time elapsed: " + (int)(getElapsed() / (1000*60*60)) + ":" + (int)((getElapsed() / (1000*60)) % 60) + ":" + (int)(getElapsed() / 1000) % 60, 15, 30);
		g.drawString("Woodcutting " + startLevel + " (+" + (Skills.getRealLevel(Skill.Woodcutting) - startLevel)+ ") xp: " + (Skills.getExperience(Skill.Woodcutting) - startXp) +", xp/h: " + getPerHour(Skills.getExperience(Skill.Woodcutting) - startXp), 15, 45);
	}

}
