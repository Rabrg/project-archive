package me.rabrg.hillgiants;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.wrappers.widgets.message.Message;

import me.rabrg.hillgiants.node.AttackNode;
import me.rabrg.hillgiants.node.BankNode;
import me.rabrg.hillgiants.node.BuryNode;
import me.rabrg.hillgiants.node.EatNode;
import me.rabrg.hillgiants.node.HopWorldNode;
import me.rabrg.hillgiants.node.Node;
import me.rabrg.hillgiants.node.ToggleRunNode;
import me.rabrg.hillgiants.node.WalkAttackNode;
import me.rabrg.hillgiants.node.WalkBankNode;

@ScriptManifest(author = "Rabrg", category = Category.COMBAT, name = "Hill Giants", version = 0.3)
public final class HillGiants extends AbstractScript implements PaintListener, MessageListener {

	private Node[] nodes;
	private Node currentNode;

	@Override
	public void onStart() {
		log("Starting script TEST");
		nodes = new Node[] { new EatNode(this), new HopWorldNode(this), new ToggleRunNode(this), /*new LootNode(this),*/ new BuryNode(this), new WalkAttackNode(this), new AttackNode(this), new WalkBankNode(this), new BankNode(this) };
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
		return Calculations.random(0, 300);
	}

	@Override 
    public void onPaint(Graphics graphics) {
        graphics.drawString("Node: " + (currentNode == null ? "null" : currentNode), 15, 60);
    }

	@Override
	public void onGameMessage(Message arg0) {
		if (arg0.getMessage().contains("already under"))
			sleep(3000, 3600);
	}

	@Override
	public void onPlayerMessage(Message arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrivateInMessage(Message arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrivateOutMessage(Message arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTradeMessage(Message arg0) {
		// TODO Auto-generated method stub
		
	}
}
