package me.rabrg.runecrafting.node;

import org.tbot.wrappers.NPC;

public abstract class Node {

	public static final int MINIMUM_MAGE_DISTANCE = 12;
	public static final String MAGE_OF_ZAMORAK = "Mage of Zamorak";
	public static final String TELEPORT = "Teleport";
	public static NPC mageOfZamorak;
	
	public static boolean mediumPouchFilled = true; // testing
	public static boolean smallPouchFilled = true;
	
	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
