package me.rabrg.runecrafting;

import java.awt.Graphics;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.ScriptCategory;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Random;

import me.rabrg.runecrafting.node.Node;
import me.rabrg.runecrafting.node.NodeController;
import me.rabrg.runecrafting.node.abyss.TeleportMageNode;
import me.rabrg.runecrafting.node.abyss.TravelMageNode;

@Manifest(authors = "Rabrg", name = "RabrgRunecrafting", category = ScriptCategory.RUNECRAFTING)
public final class RabrgRunecrafting extends AbstractScript implements PaintListener {

	private final NodeController controller = new NodeController(new TravelMageNode(), new TeleportMageNode());
	
	private Node currentNode;
	
	@Override
	public int loop() {
		if ((currentNode = controller.get()) != null) {
			return currentNode.execute();
		}
		return Random.nextInt(25, 150);
	}

	@Override
	public void onRepaint(final Graphics g) {
		g.drawString(currentNode != null ? "Node: " + currentNode.getName() : "Node: null", 360, 20);
	}

}
