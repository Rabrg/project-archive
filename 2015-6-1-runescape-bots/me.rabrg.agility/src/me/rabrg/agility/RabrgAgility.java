package me.rabrg.agility;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.agility.gnome.BalancingRopeNode;
import me.rabrg.agility.gnome.LogBalanceNode;
import me.rabrg.agility.gnome.ObstacleNetAcrossNode;
import me.rabrg.agility.gnome.ObstacleNetUpNode;
import me.rabrg.agility.gnome.ObstaclePipeNode;
import me.rabrg.agility.gnome.TreeBranchDownNode;
import me.rabrg.agility.gnome.TreeBranchUpNode;

@ScriptManifest(author = "Rabrg", category = Category.AGILITY, name = "Agility Courses", version = 0.1)
public final class RabrgAgility extends AbstractScript {

	private static final Area GNOME_AGILITY_COURSE_AREA = new Area(2465, 3410, 2495, 3445);

	private Node[] nodes;

	private Node currentNode;

	private String courseName;

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.AGILITY);
		if (GNOME_AGILITY_COURSE_AREA.contains(getLocalPlayer())) {
			courseName = "Tree Gnome Stronghold Agility Course";
			nodes = new Node[] { new LogBalanceNode(this), new ObstacleNetUpNode(this), new TreeBranchUpNode(this), new BalancingRopeNode(this), new TreeBranchDownNode(this), new ObstacleNetAcrossNode(this), new ObstaclePipeNode(this) };
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
		return Calculations.random(5, 25);
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Course: " + (courseName == null ? "unknown" : courseName), 5, 30);
		g.drawString("Node: " + (currentNode == null ? "Waiting" : currentNode.getName()), 5, 45);
		g.drawString("Agility (" + getSkillTracker().getStartLevel(Skill.AGILITY) + "+" + getSkillTracker().getGainedLevels(Skill.AGILITY) + ") xp: " + getSkillTracker().getGainedExperience(Skill.AGILITY) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.AGILITY), 5, 60);
	}
}
