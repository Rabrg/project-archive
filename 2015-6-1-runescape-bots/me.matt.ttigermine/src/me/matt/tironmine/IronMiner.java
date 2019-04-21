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
import org.tbot.util.Condition;
import org.tbot.wrappers.GameObject;

@Manifest(name = "Tiger Miner", authors = "Rabrg", version = 0.1)
public final class IronMiner extends AbstractScript implements PaintListener {

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
		}
		final GameObject rocks = GameObjects.getNearest(21702, 21703, 21704);
		if (Inventory.isFull() || (Inventory.containsOneOf("Iron ore") && rocks == null)) {
			Inventory.fastDropAll(13219);
		} else if (Players.getLocal().getAnimation() < 1 && rocks != null) {
			if (rocks.interact("Mine")) {
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						if (Players.getLocal().getAnimation() > 1 || Players.getLoaded().length > 1)
							return true;
						return rocks.getID() != GameObjects.getTopAt(rocks.getLocation()).getID();
					}
				}, Random.nextInt(600, 1200));
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						if (Players.getLocal().getAnimation() < 1 || Players.getLoaded().length > 1)
							return true;
						return rocks.getID() != GameObjects.getTopAt(rocks.getLocation()).getID();
					}
				}, Random.nextInt(600, 1200));
			}
		}
		return Random.nextInt(0, 1024);
	}

	@Override
	public void onRepaint(final Graphics g) {
		g.drawString("Time elapsed: " + (int)(getElapsed() / (1000*60*60)) + ":" + (int)((getElapsed() / (1000*60)) % 60) + ":" + (int)(getElapsed() / 1000) % 60, 15, 30);
		g.drawString("Mining " + startLevel + " (+" + (Skills.getRealLevel(Skill.Mining) - startLevel)+ ") xp: " + (Skills.getExperience(Skill.Mining) - startXp) +", xp/h: " + getPerHour(Skills.getExperience(Skill.Mining) - startXp), 15, 45);
	}

}
