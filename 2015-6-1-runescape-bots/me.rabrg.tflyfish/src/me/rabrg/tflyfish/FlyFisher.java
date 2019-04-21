package me.rabrg.tflyfish;

import java.awt.Graphics;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Camera;
import org.tbot.methods.Game;
import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Time;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Tile;

@Manifest(name = "Fly Fisher")
public final class FlyFisher extends AbstractScript implements PaintListener {

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
		startLevel = Skills.getRealLevel(Skill.Fishing);
		startXp = Skills.getExperience(Skill.Fishing);
		return true;
	}

	private NPC spot;
	private Tile spotTile;

	private boolean empty = true;

	@Override
	public int loop() {
		if (Players.getLoaded().length > 1) {
			Game.instaHopRandomP2P();
			return 3600;
		} else {
			spot = Npcs.getNearest(new Filter<NPC>() {
				@Override
				public boolean accept(final NPC npc) {
					return npc.getID() == 1506 && npc.getLocation().getX() < 2387;
				}
			});
			if (Inventory.isFull() || !empty) {
				Inventory.dropAllExcept(309, 314);
				if (Inventory.getEmptySlots() != 26) {
					empty = false;
				} else { 
					empty = true;
				}
			} else if (Players.getLocal().getAnimation() < 1 || !spot.getLocation().equals(spotTile)) {
				if (!spot.isOnScreen()) {
					Camera.turnTo(spot);
				} else if (spot.interact("Lure")) {
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
		g.drawString("Fishing " + startLevel + " (+" + (Skills.getRealLevel(Skill.Fishing) - startLevel)+ ") xp: " + (Skills.getExperience(Skill.Fishing) - startXp) +", xp/h: " + getPerHour(Skills.getExperience(Skill.Fishing) - startXp), 15, 45);
	}

}
