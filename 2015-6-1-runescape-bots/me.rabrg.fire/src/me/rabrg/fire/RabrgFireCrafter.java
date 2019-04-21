package me.rabrg.fire;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.fire.node.BankNode;
import me.rabrg.fire.node.EnterFireAltarNode;
import me.rabrg.fire.node.FireAltarNode;
import me.rabrg.fire.node.Node;
import me.rabrg.fire.node.TeleportDuelArenaNode;

@ScriptManifest(author="Rabrg", category= Category.COMBAT, name="Rabrg Fire Crafter", version=0.1, description="Crafts fire runes")
public final class RabrgFireCrafter extends AbstractScript {

	private final Node[] nodes = { new BankNode(this), new TeleportDuelArenaNode(this), new EnterFireAltarNode(this), new FireAltarNode(this) };

	@Override
	public void onStart() {
		Node.smallPouch = getInventory().contains("Small pouch");
		Node.mediumPouch = getInventory().contains("Medium pouch");
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate())
				return node.execute();
		}
		return Calculations.random(5, 25);
	}

}
