package me.rabrg.talch;

import java.awt.Graphics;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Game;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.combat.magic.Magic;
import org.tbot.methods.combat.magic.SpellBooks;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.Item;

@Manifest(name = "TAlch")
public final class TAlch extends AbstractScript implements PaintListener {

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
		startLevel = Skills.getRealLevel(Skill.MAGIC);
		startXp = Skills.getExperience(Skill.MAGIC);
		return true;
	}

	@Override
	public int loop() {
		if (!Inventory.contains("Nature rune")) {
			Game.logout();
			setPaused(true);
		} else if (Players.getLoaded().length > 1) {
			Game.instaHopRandomP2P();
		} else if (Magic.hasSpellSelected() && Inventory.getFirst(new Filter<Item>() {
			@Override
			public boolean accept(final Item item) {
				return item != null && item.getName() != null && !item.getName().equals("Nature rune") && !item.getName().equals("Coins");
			}
		}).interact("Cast")){ // XXX
			Time.sleepUntil(new Condition() {
				@Override
				public boolean check() {
					return Widgets.isTabOpen(Widgets.TAB_MAGIC);
				}
			}, Random.nextInt(2048,  2560));
		} else if (!Magic.hasSpellSelected() && !Widgets.isTabOpen(Widgets.TAB_MAGIC) && Widgets.openTab(Widgets.TAB_MAGIC)) {
			Time.sleepUntil(new Condition() {
				@Override
				public boolean check() {
					return Widgets.isTabOpen(Widgets.TAB_MAGIC);
				}
			}, Random.nextInt(2048,  2560));
		} else if (!Magic.hasSpellSelected() && Widgets.isTabOpen(Widgets.TAB_MAGIC) && Skills.getCurrentLevel(Skill.MAGIC) < 55  && Magic.cast(SpellBooks.Modern.LOW_LEVEL_ALCHEMY)) {
			Time.sleepUntil(new Condition() {
				@Override
				public boolean check() {
					return Magic.hasSpellSelected();
				}
			}, Random.nextInt(2048,  2560));
		} else if (!Magic.hasSpellSelected() && Widgets.isTabOpen(Widgets.TAB_MAGIC) && Skills.getCurrentLevel(Skill.MAGIC) >= 55  && Magic.cast(SpellBooks.Modern.HIGH_LEVEL_ALCHEMY)) {
			Time.sleepUntil(new Condition() {
				@Override
				public boolean check() {
					return Magic.hasSpellSelected();
				}
			}, Random.nextInt(2048,  2560));
		}
		return Random.nextInt(0, 128);
	}

	@Override
	public void onRepaint(final Graphics g) {
		g.drawString("Time elapsed: " + (int)(getElapsed() / (1000*60*60)) + ":" + (int)((getElapsed() / (1000*60)) % 60) + ":" + (int)(getElapsed() / 1000) % 60, 15, 30);
		g.drawString("Magic " + startLevel + " (+" + (Skills.getRealLevel(Skill.MAGIC) - startLevel)+ ") xp: " + (Skills.getExperience(Skill.MAGIC) - startXp) +", xp/h: " + getPerHour(Skills.getExperience(Skill.MAGIC) - startXp), 15, 45);
	}
}
