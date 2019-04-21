package me.rabrg.fireorb.node;

import org.tbot.util.Filter;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.Item;

public abstract class Node {

	static final Area CLAN_WARS_AREA = new Area(3200, 3100, 3500, 3200);

	static final String COSMIC_RUNE = "Cosmic rune";
	static final String UNPOWERED_ORB = "Unpowered orb";
	static final String FIRE_ORB = "Fire orb";
	static final String HOUSE_TELEPORT = "Teleport to house";
	static final String RING_OF_DUELING_MAX = "Ring of dueling(8)";
	static final String ANTIPOISON_MAX = "Antipoison(4)";
	static final String VIAL = "Vial";

	static final Filter<Item> RING_OF_DUELING_FILTER = new Filter<Item>() {
		@Override
		public boolean accept(final Item item) {
			return item.getName().startsWith("Ring of dueling");
		}
	};

	static final Filter<Item> ANTIPOISON_FILTER = new Filter<Item>() {
		@Override
		public boolean accept(final Item item) {
			return item.getName().startsWith("Antipoison");
		}
	};

	public abstract boolean validate();

	public abstract void execute();

	public abstract String toString();
}
