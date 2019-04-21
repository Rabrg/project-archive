package me.matt.tironmine;

import java.awt.Graphics;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Game;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;

@Manifest(name = "IronMiner", authors = "Rabrg", version = 0.1)
public final class IronMiner extends AbstractScript implements PaintListener {

	private Tile CENTER_TILE;

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
		CENTER_TILE = Players.getLocal().getLocation();
		startTime = System.currentTimeMillis();
		startLevel = Skills.getRealLevel(Skill.Mining);
		startXp = Skills.getExperience(Skill.Mining);
		return true;
	}

	@Override
	public int loop() {
		if (Players.getLoaded().length > 1) {
			if (Game.instaHopRandomP2P()) {
				sleep(1200, 2400);
			}
			return 0;
		} else if (!CENTER_TILE.equals(Players.getLocal().getLocation())) {
			Walking.walkTileMM(CENTER_TILE);
			return 2400;
		} else if (Inventory.hasItemSelected()) {
			Inventory.deselectItem();
		}
		final GameObject rocks = GameObjects.getNearestInRange(1, 13710, 13711, 12606, 12605);
		if (Inventory.isFull() || (Inventory.containsOneOf("Iron ore") && rocks == null)) {
			Inventory.fastDropAll(440, 1623, 1621, 1619, 1617);
		} else if (Players.getLocal().getAnimation() < 1 && rocks != null) {
			if (rocks.interact("Mine")) {
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						if (Players.getLocal().getAnimation() > 1 || Players.getLoaded().length > 1)
							return true;
						final GameObject newRocks = GameObjects.getTopAt(rocks.getLocation());
						if (newRocks == null)
							return true;
						return newRocks.getID() != rocks.getID();
					}
				}, Random.nextInt(600, 1200));
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						if (Players.getLoaded().length > 1)
							return true;
						final GameObject newRocks = GameObjects.getTopAt(rocks.getLocation());
						if (newRocks == null)
							return true;
						return newRocks.getID() != rocks.getID();
					}
				}, Random.nextInt(600, 1200));
			}
		}
		return Random.nextInt(0, 256);
	}

	@Override
	public void onRepaint(final Graphics g) {
		g.drawString("Time elapsed: " + (int)(getElapsed() / (1000*60*60)) + ":" + (int)((getElapsed() / (1000*60)) % 60) + ":" + (int)(getElapsed() / 1000) % 60, 15, 30);
		g.drawString("Mining " + startLevel + " (+" + (Skills.getRealLevel(Skill.Mining) - startLevel)+ ") xp: " + (Skills.getExperience(Skill.Mining) - startXp) +", xp/h: " + getPerHour(Skills.getExperience(Skill.Mining) - startXp), 15, 45);
	}

}
