package me.matt.terrorbird;

import java.awt.Graphics;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.event.events.MessageEvent;
import org.tbot.internal.event.listeners.MessageListener;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Camera;
import org.tbot.methods.Game;
import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Time;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Filter;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Tile;

@Manifest(name = "Terrorbird", authors = "Rabrg", version = 0.1)
public final class TerrorbirdScript extends AbstractScript implements MessageListener, PaintListener {

	private static final Area AREA_1 = new Area(2373, 3432, 2383, 3450);
	private static final Area AREA_2 = new Area(2374, 3427, 2383, 3432);

	private static final Tile FAILSAFE_TILE = new Tile(2371, 3435);

	private long startTime;
	private int startLevel;
	private int startXp;

	private long lastAnimation;

	@Override
	public boolean onStart() {
		startTime = System.currentTimeMillis();
		startLevel = Skills.getRealLevel(Skill.MAGIC);
		startXp = Skills.getExperience(Skill.MAGIC);
		return true;
	}

	@Override
	public int loop() {
		if (Players.getLoaded().length > 1) {
			Game.instaHopRandomP2P();
		}
		final org.tbot.wrappers.Character interacting = Players.getLocal().getInteractingEntity();
		if (Players.getLocal().getAnimation() > 0) {
			lastAnimation = System.currentTimeMillis();
		}
		if (System.currentTimeMillis() - lastAnimation > 3200 || (interacting == null || interacting.isDead())) {
			attack();
		}
		return Random.nextInt(0, 4800);
	}

	@Override
	public void messageReceived(final MessageEvent event) {
		if (event.getMessage().getText().contains("can't reach that")) {
			Walking.walkTileMM(FAILSAFE_TILE);
			Time.sleep(600, 1800);
		} else if (event.getMessage().getText().contains("you do not have enough")) {
			Game.logout();
			setPaused(true);
		}
	}

	@Override
	public void onRepaint(final Graphics g) {
		g.drawString("Time elapsed: " + (int)(getElapsed() / (1000*60*60)) + ":" + (int)((getElapsed() / (1000*60)) % 60) + ":" + (int)(getElapsed() / 1000) % 60, 15, 30);
		g.drawString("Magic " + startLevel + " (+" + (Skills.getRealLevel(Skill.MAGIC) - startLevel)+ ") xp: " + (Skills.getExperience(Skill.MAGIC) - startXp) +", xp/h: " + getPerHour(Skills.getExperience(Skill.MAGIC) - startXp), 15, 45);
	}

	private static void attack() {
		final NPC target = Npcs.getNearest(new Filter<NPC>() {
			@Override
			public boolean accept(final NPC npc) {
				return npc != null && npc.getName() != null && npc.getName().equals("Terrorbird") && !npc.isDead() && (npc.getInteractingEntity() == null || npc.getInteractingEntity().getID() == Players.getLocal().getID()) && (AREA_1.contains(npc) || AREA_2.contains(npc));
			}
		});
		if (target != null) {
			if (!target.isOnScreen()) {
				Camera.turnTo(target);
			}
			target.interact("Attack");
		}
		Time.sleep(600, 1800);
	}

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
}
