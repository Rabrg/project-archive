package me.rabrg.base;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.base.node.Node;

@ScriptManifest(author="Rabrg", category= Category.MISC, name="Rabrg Base Script", version=0.1, description="A base script")
public final class RabrgBase extends AbstractScript {

	private final Node[] nodes = {  };

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate())
				return node.execute();
		}
		return Calculations.random(5, 75);
	}

}
