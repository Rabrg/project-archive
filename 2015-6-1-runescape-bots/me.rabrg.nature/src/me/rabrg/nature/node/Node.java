package me.rabrg.nature.node;

import org.tbot.wrappers.Area;
import org.tbot.wrappers.Tile;

public abstract class Node {

	public static boolean giantPouch = true, largePouch = true, mediumPouch = true, smallPouch = true;

	public static int trip = 1;
	public static boolean needsDrink;

	public static final Area EDGEVILLE_WILDERNESS_AREA = new Area(3077, 3483, 3123, 3620);
	public static final Area ABYSS_AREA = new Area(2929, 4721, 3147, 4944);
	public static final Area ALTAR_AREA = new Area(2346, 4782, 2456, 4858);

	public static final Tile NATURE_RIFT = new Tile(3035, 4842);

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
