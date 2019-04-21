package me.rabrg.rat;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

import me.rabrg.rat.node.AttackNode;
import me.rabrg.rat.node.BuryNode;
import me.rabrg.rat.node.EatNode;
import me.rabrg.rat.node.LootNode;
import me.rabrg.rat.node.Node;

@ScriptManifest(author="Rabrg", category= Category.COMBAT, name="Rabrg Al-Kharid Warrior Killer", version=0.9, description="Kills Al-Kharid warriors")
public final class RabrgRatKiller extends AbstractScript {

	private final Node[] nodes = { new EatNode(this), new LootNode(this), new BuryNode(this), new AttackNode(this) };

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
			if (message.getMessage().contains("can't reach that")) {
				final GameObject door = getGameObjects().closest("Large door");
				if (door != null && door.interact("Open")) {
					sleep(850, 1200);
				}
			} else if (message.getMessage().contains("Congratulations")) {
				
			}
		}
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
