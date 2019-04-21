package me.rabrg.abyss;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

import me.rabrg.abyss.model.Pouch;
import me.rabrg.abyss.node.AbyssNode;
import me.rabrg.abyss.node.ActivateRunNode;
import me.rabrg.abyss.node.AltarNode;
import me.rabrg.abyss.node.BankNode;
import me.rabrg.abyss.node.MageOfZamorakNode;
import me.rabrg.abyss.node.Node;
import me.rabrg.abyss.node.WaitNode;
import me.rabrg.abyss.node.WildernessDitchNode;

@ScriptManifest(author = "Rabrg", category = Category.RUNECRAFTING, name = "Rabrg Abyss Pro", version = 0.01)
public final class RabrgAbyssPro extends AbstractScript implements PaintListener, MessageListener, InventoryListener {

	// TODO: cancel air obelisk pvp
	// teleport then hop
	// mounted glory
	// stamina potions 160,27 on = 1092
	// smart abyss obstacles
	// more antiban

	public final String FOOD_NAME = "Monkfish";
	public final int FOOD_HEAL = 16;

	public final String RUNE = "Nature";

	public final boolean STAMINA_POTIONS = true;

	public final boolean AGILITY_OBSTACLE = true;
	public final boolean MINING_OBSTACLE = true;
	public final boolean THIEVING_OBSTACLE = false;

	private long startTime;

	private Pouch smallPouch;
	private Pouch mediumPouch;
	private Pouch largePouch;
	private Pouch giantPouch;

	private Node[] nodes;
	private Node currentNode;

	private int runesTotal;

	public boolean obstacle;

	@Override
	public void onStart() {
		nodes = new Node[] { new ActivateRunNode(this), new WildernessDitchNode(this), new MageOfZamorakNode(this), new AbyssNode(this), new AltarNode(this), new BankNode(this), new WaitNode(this) };
		
		smallPouch = new Pouch(this, 3, getInventory().contains("Small pouch"));
		mediumPouch = new Pouch(this, 6, getInventory().contains("Medium pouch"));
		largePouch = new Pouch(this, 9, getInventory().contains("Large pouch"));
		giantPouch = new Pouch(this, 12, getInventory().contains("Giant pouch"));
		
		startTime = System.currentTimeMillis();
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate()) {
				currentNode = node;
				currentNode.execute();
				break;
			}
		}
		return Calculations.random(5, 255);
	}

	@Override
	public void onPaint(final Graphics g) {
		if (AbyssNode.obstacleArea != null) {
			((Graphics2D) g).draw(AbyssNode.obstacleArea);
		}
		final long runTime = System.currentTimeMillis() - startTime;
		g.setColor(new Color(50, 50, 50));
		g.fillRoundRect(15, 15, 145, 103, 25, 25);
		g.setColor(new Color(45, 190, 190));
		g.drawRoundRect(15, 15, 145, 103, 25, 25);
		g.setColor(new Color(215, 110, 60));
		g.drawString("Node: " + currentNode, 28, 35);
		g.drawString("Run time: " + (runTime / (1000 * 60 * 60)) % 24 + ':' + (runTime / (1000 * 60)) % 60 + ':' + (runTime / 1000) % 60, 28, 50);
		g.drawString(RUNE + " runes: " + runesTotal, 28, 65);
		g.drawString(RUNE + " runes/h: " + (int)(runesTotal / (runTime / 3600000.0D)), 28, 80);
		g.drawString("Profit: " + runesTotal * 250, 28, 95);
		g.drawString("Profit/h: " + (int)((runesTotal * 250) / (runTime / 3600000.0D)), 28, 110);
	}

	public Pouch getSmallPouch() {
		return smallPouch;
	}

	public Pouch getMediumPouch() {
		return mediumPouch;
	}

	public Pouch getLargePouch() {
		return largePouch;
	}

	public Pouch getGiantPouch() {
		return giantPouch;
	}

	@Override
	public void onGameMessage(final Message message) {
		if (message.getType() == MessageType.GAME) {
			if (message.getMessage().startsWith("...and") || message.getMessage().startsWith("...but")) {
				if (message.getMessage().startsWith("...and"))
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return getMap().canReach(AbyssNode.ABYSS_CENTER_TILE);
						}
					}, Calculations.random(1800, 2400));
				obstacle = false;
			} else if (message.getMessage().startsWith("You attempt")) {
				obstacle = true;
			}
		}
	}

	@Override
	public void onPlayerMessage(final Message message) { }

	@Override
	public void onPrivateInMessage(final Message message) { }

	@Override
	public void onPrivateOutMessage(final Message message) { }

	@Override
	public void onTradeMessage(final Message message) { }

	@Override
	public void onItemChange(final Item[] items) {
		for (final Item item : items) {
			if (item.getName().equals(RUNE + " rune") && item.getAmount() > 0) {
				runesTotal += item.getAmount() * 2;
			}
		}
	}
}
