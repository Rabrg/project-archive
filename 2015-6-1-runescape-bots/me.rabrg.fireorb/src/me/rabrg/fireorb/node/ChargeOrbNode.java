package me.rabrg.fireorb.node;

import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.methods.combat.Combat;
import org.tbot.methods.combat.magic.Magic;
import org.tbot.methods.combat.magic.SpellBooks;
import org.tbot.methods.combat.prayer.Prayers;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Item;
import org.tbot.wrappers.Player;
import org.tbot.wrappers.WidgetChild;

import me.rabrg.fireorb.RabrgFireOrb;

public final class ChargeOrbNode extends Node {

	private static final Area FINAL_AREA = new Area(2800, 9800, 2879, 9900);
	public static final Area OBELISK_AREA = new Area(2817, 9829, 2820, 9831);

	private static final String FIRE_OBELISK = "Obelisk of Fire";
	
	private long lastAnimation;

	private GameObject obelisk;
	private WidgetChild widget;
	private Path path;

	@Override
	public boolean validate() {
		return FINAL_AREA.contains(Players.getLocal()) && Inventory.contains(UNPOWERED_ORB);
	}

	@Override
	public void execute() {
		if (Players.getLocal().getAnimation() > 0) {
			lastAnimation = System.currentTimeMillis();
		}
		if (Combat.isPoisoned()) {
			final Item potion = Inventory.getFirst(ANTIPOISON_FILTER);
			if (potion != null && potion.interact("Drink")) {
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						return !Combat.isPoisoned();
					}
				}, Random.nextInt(600, 900));
			}
		}
		if (OBELISK_AREA.contains(Players.getLocal())) {
			if (Prayers.isQuickPrayersActive()) {
				Prayers.activateQuickPrayers(false);
			} else if ((widget = Widgets.getWidget(309, 3)) != null && widget.interact("Make All")) {
				widget = null;
				lastAnimation = System.currentTimeMillis();
			} else if (System.currentTimeMillis() - lastAnimation > 3000) {
				if (Magic.hasSpellSelected()  && (obelisk = GameObjects.getNearest(FIRE_OBELISK)) != null && obelisk.interact("Cast")) {
					obelisk = null;
					lastAnimation = System.currentTimeMillis();
				} else if (!Magic.hasSpellSelected() && Magic.cast(SpellBooks.Modern.CHARGE_FIRE_ORB)) {
					for (final Player player : Players.getLoaded()) {
						if (!player.getName().equals(Players.getLocal().getName()) && player.distance() < 25) {
							RabrgFireOrb.hop = true;
							break;
						}
					}
					Time.sleepUntil(new Condition() {
						@Override
						public boolean check() {
							return Magic.hasSpellSelected();
						}
					}, Random.nextInt(4800, 6000));
				}
			}
		} else if (!Prayers.isQuickPrayersActive()) {
			Prayers.activateQuickPrayers(true);
		} else {
			if (path == null)
				path = Walking.findLocalPath(OBELISK_AREA.getTileArray()[Random.nextInt(OBELISK_AREA.getTileArray().length)]);
			if (path != null && path.traverse()) {
				Time.sleep(0, 1800);
			} else {
				path = Walking.findLocalPath(OBELISK_AREA.getTileArray()[Random.nextInt(OBELISK_AREA.getTileArray().length)]);
			}
		}
	}

	@Override
	public String toString() {
		return "Charging orbs";
	}

}
