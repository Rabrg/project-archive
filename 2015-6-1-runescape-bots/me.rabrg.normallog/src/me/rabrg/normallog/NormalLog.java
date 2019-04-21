package me.rabrg.normallog;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.normallog.node.Banker;
import me.rabrg.normallog.node.Cutter;
import me.rabrg.normallog.node.Node;

@ScriptManifest(author="Rabrg", category= Category.WOODCUTTING, name="Normal log", version=0.1, description="Cuts logs")
public final class NormalLog extends AbstractScript {

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
