package me.rabrg.altar;

import java.awt.EventQueue;
import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.altar.node.AltarNode;
import me.rabrg.altar.node.BankNode;
import me.rabrg.altar.node.EnterPortalNode;
import me.rabrg.altar.node.LeavePortalNode;
import me.rabrg.altar.node.Node;

@ScriptManifest(author = "Rabrg", category = Category.PRAYER, name = "Rabrg Altar Pro", version = 0.0)
public final class RabrgAltar extends AbstractScript {

	// fix stamina potions

	private Node[] nodes;
	private Node currentNode;

	@Override
	public void onStart() {
		final RabrgAltar instance = this;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new RabrgAltarFrame(instance).setVisible(true);
			}
		});
	}

	void actualStart(final String bones, final boolean staminaPotions) {
		log("" + staminaPotions);
		nodes = new Node[] { new BankNode(bones, staminaPotions, this), new EnterPortalNode(bones, this), new AltarNode(bones, this), new LeavePortalNode(bones, this) };
		getSkillTracker().start(Skill.PRAYER, true);
	}

	@Override
	public int onLoop() {
		if (nodes != null) {
			for (final Node node : nodes) {
				if (node.validate()) {
					currentNode = node;
					return currentNode.execute();
				}
			}
		}
		currentNode = null;
		return Calculations.random(300);
	}

	@Override
	public void onPaint(final Graphics g) {
		if (nodes != null) {
			g.drawString("Node: " + (currentNode != null ? currentNode.getName() : "stuck"), 15, 45);
			g.drawString("Prayer (" + getSkillTracker().getStartLevel(Skill.PRAYER) + "+" + getSkillTracker().getGainedLevels(Skill.PRAYER) + ") xp: " + getSkillTracker().getGainedExperience(Skill.PRAYER) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.PRAYER), 15, 60);
		} else {
			g.drawString("Node: config", 15, 45);
		}
	}
}
