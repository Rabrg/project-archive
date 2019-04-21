package me.rabrg.chicken;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.chicken.node.AttackNode;
import me.rabrg.chicken.node.Node;

@ScriptManifest(author="Rabrg", category= Category.COMBAT, name="Chicken Killer", version=0.1, description="Kills chickens")
public final class ChickenKiller extends AbstractScript {

	private final Node[] nodes = { new AttackNode(this) };

	private Node currentNode;

	@Override
	public void onStart() {
		getSkillTracker().start();
	}

	@Override
	public int onLoop() {
		if (this.getDialogues().canContinue())
			getDialogues().continueDialogue();
		for (final Node node : nodes)
			if ((currentNode = node).validate())
				return node.execute();
		return Calculations.random(5, 25);
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Node: " + (currentNode == null ? "null" : currentNode.getName()), 5, 45);
		g.drawString("Attack (" + getSkillTracker().getStartLevel(Skill.ATTACK) + "+" + getSkillTracker().getGainedLevels(Skill.ATTACK) + ") xp: " + getSkillTracker().getGainedExperience(Skill.ATTACK) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK), 5, 60);
		g.drawString("Strength (" + getSkillTracker().getStartLevel(Skill.STRENGTH) + "+" + getSkillTracker().getGainedLevels(Skill.STRENGTH) + ") xp: " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH), 5, 75);
		g.drawString("Defence (" + getSkillTracker().getStartLevel(Skill.DEFENCE) + "+" + getSkillTracker().getGainedLevels(Skill.DEFENCE) + ") xp: " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE), 5, 90);
		g.drawString("Hitpoints (" + getSkillTracker().getStartLevel(Skill.HITPOINTS) + "+" + getSkillTracker().getGainedLevels(Skill.HITPOINTS) + ") xp: " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS), 5, 105);
	}
}
