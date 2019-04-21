package me.rabrg.wyvern.node;

import java.util.ArrayList;
import java.util.List;

public class NodeController {

	private final List<Node> nodeList;

	public NodeController(final Node... nodes) {
		this.nodeList = new ArrayList<Node>();
		for (final Node n : nodes)
			nodeList.add(n);
	}

	public void addNodes(final Node... nodes) {
		for (final Node n : nodes)
			nodeList.add(n);
	}

	public void clearNodes() {
		nodeList.clear();
	}

	public void removeNodes(final Node... nodes) {
		for (final Node n : nodes)
			nodeList.remove(n);
	}

	public List<Node> getNodes() {
		return nodeList;
	}

	public Node getCurrentNode() {
		final List<Node> exeucuteableNodes = new ArrayList<Node>();
		for (final Node n : nodeList)
			if (n.validate())
				exeucuteableNodes.add(n);
		for (final Node n : exeucuteableNodes)
			if (getHighest(exeucuteableNodes) == n.priority())
				return n;
		return null;
	}

	private int getHighest(final List<Node> exeucuteableNodes) {
		int highest = exeucuteableNodes.get(0).priority();
		for (final Node n : exeucuteableNodes)
			if (n.priority() > highest)
				highest = n.priority();
		return highest;
	}

}