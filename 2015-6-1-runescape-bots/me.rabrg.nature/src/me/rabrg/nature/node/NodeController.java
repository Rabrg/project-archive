package me.rabrg.nature.node;

import java.util.ArrayList;
import java.util.List;

public class NodeController {

	private final List<Node> nodes = new ArrayList<Node>();

	public NodeController(final Node... nodes) {
		for (final Node node : nodes) {
			this.nodes.add(node);
		}
	}

	public Node get() {
		for (final Node node : nodes) {
			if (node.validate()) {
				return node;
			}
		}
		return null;
	}
}
