package me.matt.nature;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

import me.matt.nature.node.AbyssNode;
import me.matt.nature.node.AltarNode;
import me.matt.nature.node.AntistuckNode;
import me.matt.nature.node.BankNode;
import me.matt.nature.node.EscapeNode;
import me.matt.nature.node.FailsafeTeleportNode;
import me.matt.nature.node.Node;
import me.matt.nature.node.WildernessDitchNode;
import me.matt.nature.node.ZamorakMageNode;

@ScriptManifest(author = "Rabrg", category = Category.RUNECRAFTING, name = "Rabrg Nature", version = 0.1)
public final class RabrgNature extends AbstractScript {

	private static final int COST = 250;

	private final Node[] nodes = { new EscapeNode(this), new AntistuckNode(this), new FailsafeTeleportNode(this), new BankNode(this), new WildernessDitchNode(this), new ZamorakMageNode(this), new AbyssNode(this), new AltarNode(this) };

	private Node currentNode;

	public static int crafted;
	public static int hops;
	public static int ringOfLifes;
	public static int failsafes;

	@Override
	public void onStart() {
		if (getInventory().contains("Small pouch")) {
			Node.usingSmall = true;
			Node.smallFilled = true;
		}
		if (getInventory().contains("Medium pouch")) {
			Node.usingMedium = true;
			Node.mediumFilled = true;
		}
		if (getInventory().contains("Large pouch")) {
			Node.usingLarge = true;
			Node.largeFilled = true;
		}
		if (getInventory().contains("Giant pouch")) {
			Node.usingGiant = true;
			Node.giantFilled = true;
		}
		getSkillTracker().start(Skill.RUNECRAFTING);
		log("small: " + Node.usingSmall + ", medium: " + Node.usingMedium + ", large: " + Node.usingLarge + ", giant: " + Node.usingGiant);
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate()) {
				currentNode = node;
				return currentNode.execute();
			}
		}
		if (currentNode != null) {
			log(currentNode.getName());
		}
		currentNode = null;
		return Calculations.random(25, 75);
	}

	private long getElapsed() {
		return System.currentTimeMillis() - getSkillTracker().getStartTime(Skill.RUNECRAFTING);
	}

	private int getPerHour(double value) {
		if (getElapsed() > 0) {
			return (int) (value * 3600000d / getElapsed());
		} else {
			return 0;
		}
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Rabrg Nature", 5, 45);
		g.drawString("Node: " + (currentNode == null ? "wait" : currentNode.getName()), 5, 60);
		g.drawString("Time elapsed: " + (int)(getElapsed() / (1000*60*60)) + ":" + (int)((getElapsed() / (1000*60)) % 60) + ":" + (int)(getElapsed() / 1000) % 60, 5, 75);
		g.drawString("Profit: " + (COST * crafted), 5, 90);
		g.drawString("Profit/h: " + getPerHour((COST * crafted)), 5, 105);
		g.drawString("Runecrafting (" + getSkillTracker().getStartLevel(Skill.RUNECRAFTING) + "+" + getSkillTracker().getGainedLevels(Skill.RUNECRAFTING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.RUNECRAFTING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.RUNECRAFTING), 5, 120);
		g.drawString("Hops: " + hops, 5, 135);
		g.drawString("Ring of lifes: " + ringOfLifes, 5, 150);
		g.drawString("Failsafe teleports: " + failsafes, 5, 165);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getType() == MessageType.GAME && message.getMessage().contains("dead")) {
			getTabs().logout();
		}
	}
}
