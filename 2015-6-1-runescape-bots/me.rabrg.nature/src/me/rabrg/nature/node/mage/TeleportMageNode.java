package me.rabrg.nature.node.mage;

import org.tbot.methods.Camera;
import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.NPC;

import me.rabrg.nature.node.Node;

public final class TeleportMageNode extends Node {
	private NPC mageOfZamorak;

	@Override
	public boolean validate() {
		return Inventory.isFull() && EDGEVILLE_WILDERNESS_AREA.contains(Players.getLocal())
				&& (mageOfZamorak = Npcs.getNearest("Mage of Zamorak")) != null && mageOfZamorak.distance() <= 8;
	}

	@Override
	public int execute() {
		if (!mageOfZamorak.isOnScreen()) {
			Camera.turnTo(mageOfZamorak, 60);
			return Random.nextInt(300, 600);
		} else {
			mageOfZamorak.interact("Teleport");
		}
		return Random.nextInt(650, 1115);
	}

	@Override
	public String getName() {
		return "Teleporting to abyss";
	}

}
