package me.rabrg.air;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.air.node.AltarNode;
import me.rabrg.air.node.BankNode;
import me.rabrg.air.node.Node;
import me.rabrg.air.node.RuinsNode;

@ScriptManifest(author = "Rabrg", category = Category.RUNECRAFTING, name = "Air runecrafter", version = 0)
public final class RabrgAir extends AbstractScript {

	private final Node[] nodes = { new AltarNode(this), new RuinsNode(this), new BankNode(this) };

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate()) {
				return node.execute();
			}
		}
		return Calculations.random(5, 25);
	}

}
