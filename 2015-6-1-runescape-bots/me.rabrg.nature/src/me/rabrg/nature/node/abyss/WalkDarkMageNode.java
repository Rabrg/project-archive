package me.rabrg.nature.node.abyss;

import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.Tile;

import me.rabrg.nature.node.Node;

public final class WalkDarkMageNode extends Node {

	private static final Tile DARK_MAGE_TILE = new Tile(3039, 4835);

	private Path path;

	@Override
	public boolean validate() {
		return ABYSS_AREA.contains(Players.getLocal()) && Inventory.containsOneOf(5515, 5513, 5511) && (path = Walking.findLocalPath(DARK_MAGE_TILE)) != null;
	}

	@Override
	public int execute() {
		path.traverse();
		return Random.nextInt(650, 1250);
	}

	@Override
	public String getName() {
		return "Walking to repair";
	}

}
