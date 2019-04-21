package me.rabrg.oaklog;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.oaklog.node.Banker;
import me.rabrg.oaklog.node.Cutter;
import me.rabrg.oaklog.node.Node;

@ScriptManifest(author="Rabrg", category= Category.WOODCUTTING, name="Oak log", version=0.1, description="Cuts oak logs")
public final class OakLog extends AbstractScript {

	private final Node[] nodes = { new Cutter(this), new Banker(this) };

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate()) {
				return node.execute();
			}
		}
		return Calculations.random(0, 255);
	}

}
