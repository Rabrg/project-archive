package me.rabrg.shrimp;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.shrimp.node.ContinueNode;
import me.rabrg.shrimp.node.DropFishNode;
import me.rabrg.shrimp.node.FishNode;
import me.rabrg.shrimp.node.FishingNode;
import me.rabrg.shrimp.node.Node;

@ScriptManifest(author="Rabrg", category= Category.FISHING, name="Barbarian Fisher", version=0.1, description="Fishes fly")
public final class RabrgFly extends AbstractScript {

	private final Node[] nodes = { new DropFishNode(this), new ContinueNode(this), new FishNode(this), new FishingNode(this) };

	private Node currentNode;

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.FISHING);
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if ((currentNode = node).validate())
				return node.execute();
		}
		return Calculations.random(600, 1800);
	}

	@Override
	public void onPaint(final Graphics g) {
		g.drawString("Node: " + (currentNode == null ? "null" : currentNode.getName()), 5, 45);
		g.drawString("Fishing (" + getSkillTracker().getStartLevel(Skill.FISHING) + "+" + getSkillTracker().getGainedLevels(Skill.FISHING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.FISHING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING), 5, 60);
	}
}
