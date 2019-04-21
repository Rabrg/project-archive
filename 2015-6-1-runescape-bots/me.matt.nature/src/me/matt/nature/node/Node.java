package me.matt.nature.node;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Area;

public abstract class Node {

	public static final Area EDGEVILLE_AREA = new Area(3075, 3467, 3130, 3520);
	public static final Area WILDERNESS_AREA = new Area(3075, 3521, 3130, 3575);
	public static final Area ABYSS_AREA = new Area(2900, 4700, 3100, 4900);
	public static final Area ALTAR_AREA = new Area(2375, 4830, 2410, 4855);

	public static boolean usingSmall;
	public static boolean smallFilled;

	public static boolean usingMedium;
	public static boolean mediumFilled;

	public static boolean usingLarge;
	public static boolean largeFilled;

	public static boolean usingGiant;
	public static boolean giantFilled;

	public final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
