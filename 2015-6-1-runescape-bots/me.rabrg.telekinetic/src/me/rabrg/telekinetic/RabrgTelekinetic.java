package me.rabrg.telekinetic;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.telekinetic.node.Node;
import me.rabrg.telekinetic.node.SolvePuzzleNode;
import me.rabrg.telekinetic.node.TalkMazeGuardianNode;

@ScriptManifest(author = "Rabrg", category = Category.MAGIC, name = "Telekinetic", version = 0.1)
public final class RabrgTelekinetic extends AbstractScript {

	private final Node[] nodes = { new SolvePuzzleNode(this), new TalkMazeGuardianNode(this) };

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
	}
}
