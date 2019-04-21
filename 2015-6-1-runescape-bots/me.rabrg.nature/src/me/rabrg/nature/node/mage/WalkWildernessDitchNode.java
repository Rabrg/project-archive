package me.rabrg.nature.node.mage;

import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;

import me.rabrg.nature.node.Node;

public final class WalkWildernessDitchNode extends Node {

	private static final Tile PATH_ONE_TILE = new Tile(3088, 3520);
	private static final Tile PATH_TWO_TILE = new Tile(3102, 3520);

	public static boolean pathOne = Random.nextInt(1, 10) >= 7;

	private GameObject ditch;
	private Path path;

	@Override
	public boolean validate() {
		return Inventory.isFull() && EDGEVILLE_WILDERNESS_AREA.contains(Players.getLocal()) && Players.getLocal().getLocation().getY() <= 3520 && ((ditch = GameObjects.getNearest("Wilderness Ditch")) == null || ditch.distance() > 5) && (path = Walking.findPath(pathOne ? PATH_ONE_TILE : PATH_TWO_TILE)) != null;
	}

	@Override
	public int execute() {
		path.traverse();
		return Random.nextInt(650, 1250);
	}

	@Override
	public String getName() {
		return "Walking to ditch";
	}

}
