package me.rabrg.pickpocket;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;

import me.rabrg.pickpocket.node.BankNode;
import me.rabrg.pickpocket.node.EatNode;
import me.rabrg.pickpocket.node.Node;
import me.rabrg.pickpocket.node.PickpocketNode;

@ScriptManifest(author="Rabrg", category= Category.THIEVING, name="Rabrg Pickpocket", version=0.1, description="Pickpockets master farmers")
public final class RabrgPickpocket extends AbstractScript {

	private final Node[] nodes = { new BankNode(this), new EatNode(this), new PickpocketNode(this) };

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate())
				return node.execute();
		}
		return Calculations.random(5, 25);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().contains("stunned!"))
			sleep(1200, 2400);
	}
}
