package me.rabrg.karambwan.node.shop;

import org.tbot.methods.Npcs;
import org.tbot.methods.Random;
import org.tbot.methods.walking.LocalPath;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Tile;

import me.rabrg.karambwan.node.Node;

public final class WalkToShopNode extends Node {
	private static final Tile SHOP_TILE = new Tile(2783, 3058);
	private NPC tiadeche;
	private LocalPath path;

	@Override
	public boolean validate() {
		return ((this.tiadeche = Npcs.getNearest("Tiadeche")) == null || this.tiadeche.distance() > 10)
				&& (this.path != null || (this.path = Walking.findLocalPath(SHOP_TILE)) != null);
	}

	@Override
	public int execute() {
		if (!this.path.traverse()) {
			this.path = Walking.findLocalPath(SHOP_TILE);
		}
		return Random.nextInt(600, 1000);
	}

	@Override
	public String getName() {
		return "Walking to shop";
	}
}
