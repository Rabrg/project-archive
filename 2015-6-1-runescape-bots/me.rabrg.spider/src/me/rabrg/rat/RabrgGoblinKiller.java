package me.rabrg.rat;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

import me.rabrg.rat.node.AttackNode;
import me.rabrg.rat.node.EatNode;
import me.rabrg.rat.node.Node;

@ScriptManifest(author="Rabrg", category= Category.COMBAT, name="Rabrg Giant Spider Killer", version=0.9, description="Kills rats in Varrock's sewers")
public final class RabrgGoblinKiller extends AbstractScript {

	private final Node[] nodes = { new EatNode(this), new AttackNode(this) };

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
	public void onMessage(final Message message) {
		if (message.getType() == MessageType.GAME) {
			if (message.getMessage().contains("already under attack")) {
				sleep(1600, 2400);
			} else if (message.getMessage().contains("Congratulations")) {
				
			}
		} else if (message.getType() == MessageType.PLAYER) {
			if (message.getMessage().contains("bot")) {
				getKeyboard().type("stfu lol", true, true);
			} else if (message.getMessage().contains("?") || message.getMessage().contains("do") || message.getMessage().contains("why") || message.getMessage().contains("can") || message.getMessage().contains("where")) {
				getKeyboard().type("nope", true, true);
			}
		}
	}

	@Override
	public void onPaint(final Graphics g){
		g.fillRect(10, 460, 75, 10);
		g.drawString("Node: " + (currentNode == null ? "null" : currentNode.getName()), 5, 45);
		g.drawString("Attack (" + getSkillTracker().getStartLevel(Skill.ATTACK) + "+" + getSkillTracker().getGainedLevels(Skill.ATTACK) + ") xp: " + getSkillTracker().getGainedExperience(Skill.ATTACK) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK), 5, 60);
		g.drawString("Strength (" + getSkillTracker().getStartLevel(Skill.STRENGTH) + "+" + getSkillTracker().getGainedLevels(Skill.STRENGTH) + ") xp: " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH), 5, 75);
		g.drawString("Defence (" + getSkillTracker().getStartLevel(Skill.DEFENCE) + "+" + getSkillTracker().getGainedLevels(Skill.DEFENCE) + ") xp: " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE), 5, 90);
		g.drawString("Hitpoints (" + getSkillTracker().getStartLevel(Skill.HITPOINTS) + "+" + getSkillTracker().getGainedLevels(Skill.HITPOINTS) + ") xp: " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS), 5, 105);
	}
}
