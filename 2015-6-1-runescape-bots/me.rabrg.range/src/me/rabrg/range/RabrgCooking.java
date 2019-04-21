package me.rabrg.range;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.range.node.BankNode;
import me.rabrg.range.node.CookNode;
import me.rabrg.range.node.Node;

@ScriptManifest(author="Rabrg", category= Category.COOKING, name="Range Cooker", version=0.1, description="Cooks")
public final class RabrgCooking extends AbstractScript {

	private final Node[] nodes = { new BankNode(this), new CookNode(this) };

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate())
				return node.execute();
		}
		return Calculations.random(5, 75);
	}

}
