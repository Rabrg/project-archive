package me.rabrg.karambwan.node;

import org.tbot.wrappers.Area;

public abstract class Node {

	public static final Area DUEL_ARENA_BANK_AREA = new Area(3380, 3267, 3385, 3275);
	public static final Area CASTLE_WARS_AREA = new Area(2435, 3081, 2446, 3098);
	public static final Area SHOP_AREA = new Area(2778, 3055, 2782, 3059);

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
