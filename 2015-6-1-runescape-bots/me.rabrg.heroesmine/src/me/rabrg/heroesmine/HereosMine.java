package me.rabrg.heroesmine;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;

import me.rabrg.heroesmine.node.AbovegroundNode;
import me.rabrg.heroesmine.node.BankNode;
import me.rabrg.heroesmine.node.MineNode;
import me.rabrg.heroesmine.node.Node;

@ScriptManifest(author = "Rabrg", category = Category.MINING, name = "Heroes Mine", version = 1.4)
public final class HereosMine extends AbstractScript {

	private final Node[] nodes = { new MineNode(this), new BankNode(this, this), new AbovegroundNode(this) };
	private Node currentNode;

	private final long startTime = System.currentTimeMillis();

	private static final int OREPRICE = 12225;

	private int ores = 0;

	private long getElapsed() {
		return System.currentTimeMillis() - startTime;
	}

	private int getPerHour(final double value) {
		if (getElapsed() > 0) {
			return (int) (value * 3600000d / getElapsed());
		} else {
			return 0;
		}
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate()) {
				currentNode = node;
				return currentNode.execute();
			}
		}
		currentNode = null;
		return Calculations.random(300);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().contains("manage to mine some runite")) {
			ores++;
		}
	}

	@Override
	public void onPaint(final Graphics g) {
		g.drawString("Node: " + currentNode.getName(), 15, 15);
		g.drawString("Time elapsed: " + (int)(getElapsed() / (1000*60*60)) + ":" + (int)((getElapsed() / (1000*60)) % 60) + ":" + (int)(getElapsed() / 1000) % 60, 15, 30);
		g.drawString("Profit: " + (ores * OREPRICE), 15, 45);
		g.drawString("Profit/h: " + getPerHour(ores * OREPRICE), 15, 60);
	}
}
