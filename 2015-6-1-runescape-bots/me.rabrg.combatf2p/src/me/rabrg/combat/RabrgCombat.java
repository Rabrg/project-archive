package me.rabrg.combat;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;

import me.rabrg.combat.node.AttackNode;
import me.rabrg.combat.node.BuryNode;
import me.rabrg.combat.node.EatNode;
import me.rabrg.combat.node.LootNode;
import me.rabrg.combat.node.Node;

@ScriptManifest(author="Rabrg", category= Category.COMBAT, name="Rabrg Combat F2P", version=0.9, description="Kills things")
public final class RabrgCombat extends AbstractScript {

	public static RabrgCombat script;

	private final Node[] nodes = { new EatNode(this), new LootNode(this), new BuryNode(this), new AttackNode(this) };

	private Node currentNode;

	@Override
	public void onStart() {
		script = this;
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
		if (message.getMessage().contains("I'm already under attack."))
			sleep(1200, 1600);
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Node: " + (currentNode == null ? "null" : currentNode.getName()), 5, 15);
		g.drawString("Attack xp: " + getSkillTracker().getGainedExperience(Skill.ATTACK), 5, 30);
		g.drawString("Attack xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK), 5, 45);
		g.drawString("Strength xp: " + getSkillTracker().getGainedExperience(Skill.STRENGTH), 5, 60);
		g.drawString("Strength xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH), 5, 75);
		g.drawString("Defence xp: " + getSkillTracker().getGainedExperience(Skill.DEFENCE), 5, 90);
		g.drawString("Defence xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE), 5, 105);
		g.drawString("Hitpoints xp: " + getSkillTracker().getGainedExperience(Skill.HITPOINTS), 5, 120);
		g.drawString("Hitpoints xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS), 5, 135);
		g.drawString("Slayer xp: " + getSkillTracker().getGainedExperience(Skill.SLAYER), 5, 150);
		g.drawString("Slayer xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.SLAYER), 5, 165);
		g.drawString("Prayer xp: " + getSkillTracker().getGainedExperience(Skill.PRAYER), 5, 180);
		g.drawString("Prayer xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.PRAYER), 5, 195);
		g.drawString("Ranged xp: " + getSkillTracker().getGainedExperience(Skill.RANGED), 5, 210);
		g.drawString("Ranged xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.RANGED), 5, 225);
	}
}
