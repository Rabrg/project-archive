package me.rabrg.lavadragon;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.lavadragon.node.HopWorldNode;
import me.rabrg.lavadragon.node.Node;

@ScriptManifest(author = "Rabrg", category = Category.COMBAT, name = "Lava Dragon", version = 0)
public class LavaDragon extends AbstractScript {

	private final Node[] nodes = { new HopWorldNode(this) };

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate())
				return node.execute();
		}
		return Calculations.random(300);
	}

}
