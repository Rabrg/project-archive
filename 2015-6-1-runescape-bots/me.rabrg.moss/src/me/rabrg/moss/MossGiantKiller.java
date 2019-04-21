package me.rabrg.moss;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

import me.rabrg.moss.node.AttackNode;
import me.rabrg.moss.node.EatNode;
import me.rabrg.moss.node.LootNode;
import me.rabrg.moss.node.Node;

@ScriptManifest(author="Rabrg", category= Category.COMBAT, name="Moss giant Killer", version=0.1, description="Kills moss giants")
public final class MossGiantKiller extends AbstractScript {

	private final Node[] nodes = { new EatNode(this), new LootNode(this), new AttackNode(this) };

	private Node currentNode;

	@Override
	public void onStart() {
		getSkillTracker().start();
		EatNode.nextHeal = Calculations.random(15, (getSkills().getRealLevel(Skill.HITPOINTS) - 12));
	}

	@Override
	public int onLoop() {
		if (this.getDialogues().canContinue())
			getDialogues().continueDialogue();
		for (final Node node : nodes)
			if ((currentNode = node).validate())
				return node.execute();
		currentNode = null;
		return Calculations.random(5, 25);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getType() == MessageType.PLAYER) {
			final String msg = message.getMessage().toLowerCase();
			String[] replies = null;
			if (msg.contains("bot") || msg.contains("reported")) {
				replies = new String[] { "reported", "noob", "fag", "nooob" };
			} else if (msg.contains("where") || msg.contains("how") || msg.contains("are") || msg.contains("?")) {
				replies = new String[] { "nope", "no", "idk" };
			}
			if (replies != null) {
				sleep(1200, 3600);
				getKeyboard().type(replies[Calculations.random(replies.length)], true, true);
				sleep(600, 2400);
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
