package me.rabrg.power;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;

import me.rabrg.power.node.Node;

public final class RabrgPowerPro extends AbstractScript {

	private Node[] nodes;
	private Node currentNode;

	@Override
	public void onStart() {
		nodes = new Node[] {  };
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate()) {
				currentNode = node;
				return currentNode.execute();
			}
		}
		return Calculations.random(0, 512);
	}

}
