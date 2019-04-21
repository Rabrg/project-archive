package me.rabrg.enchantingchamber;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.enchantingchamber.node.ContinueDialogueNode;
import me.rabrg.enchantingchamber.node.EnchantDragonstoneNode;
import me.rabrg.enchantingchamber.node.EnchantersTeleportlNode;
import me.rabrg.enchantingchamber.node.EnterExitTeleportNode;
import me.rabrg.enchantingchamber.node.HopWorldNode;
import me.rabrg.enchantingchamber.node.Node;
import me.rabrg.enchantingchamber.node.TakeDragonstoneNode;
import me.rabrg.enchantingchamber.node.ToggleRunNode;

@ScriptManifest(author = "Rabrg", category = Category.MAGIC, name = "Enchanting Chamber", version = 0.1)
public final class RabrgEnchantingChamber extends AbstractScript {

	private final Node[] nodes = { new ToggleRunNode(this), new ContinueDialogueNode(this), new EnchantersTeleportlNode(this), new TakeDragonstoneNode(this), new EnchantDragonstoneNode(this), new EnterExitTeleportNode(this), new HopWorldNode(this) };

	private Node currentNode;

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate()) {
				currentNode = node;
				return currentNode.execute();
			}
		}
		currentNode = null;
		return Calculations.random(25, 125);
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Node: " + (currentNode == null ? "Waiting" : currentNode.getName()), 5, 45);
		g.drawString("Enchants: " + Node.enchants, 5, 60);
	}
}
