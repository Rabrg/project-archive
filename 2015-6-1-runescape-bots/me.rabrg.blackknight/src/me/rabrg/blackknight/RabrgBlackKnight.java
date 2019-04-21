package me.rabrg.blackknight;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

import me.rabrg.blackknight.node.EnterAltarNode;
import me.rabrg.blackknight.node.LeaveAltarNode;
import me.rabrg.blackknight.node.Node;
import me.rabrg.blackknight.node.PrayAltarNode;
import me.rabrg.blackknight.node.TargetBlackKnightNode;

@ScriptManifest(author = "Rabrg", category = Category.COMBAT, name = "Black Knight", version = 0.1)
public final class RabrgBlackKnight extends AbstractScript {

	private final Node[] nodes = { new TargetBlackKnightNode(this), new EnterAltarNode(this), new PrayAltarNode(this), new LeaveAltarNode(this) };

	private Node currentNode;

	@Override
	public void onStart() {
		getSkillTracker().start();
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if ((currentNode = node).validate())
				return node.execute();
		}
		currentNode = null;
		return Calculations.random(5, 75);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getType() == MessageType.GAME) {
			if (message.getMessage().contains("already under attack")) {
				sleep(3000, 36000);
			}
		}
	}

	@Override
	public void onPaint(final Graphics g){
		g.fillRect(10, 460, 75, 10);
		g.drawString("Node: " + (currentNode == null ? "waiting" : currentNode.getName()), 5, 45);
		g.drawString("Attack (" + getSkillTracker().getStartLevel(Skill.ATTACK) + "+" + getSkillTracker().getGainedLevels(Skill.ATTACK) + ") xp: " + getSkillTracker().getGainedExperience(Skill.ATTACK) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK), 5, 60);
		g.drawString("Strength (" + getSkillTracker().getStartLevel(Skill.STRENGTH) + "+" + getSkillTracker().getGainedLevels(Skill.STRENGTH) + ") xp: " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH), 5, 75);
		g.drawString("Defence (" + getSkillTracker().getStartLevel(Skill.DEFENCE) + "+" + getSkillTracker().getGainedLevels(Skill.DEFENCE) + ") xp: " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE), 5, 90);
		g.drawString("Hitpoints (" + getSkillTracker().getStartLevel(Skill.HITPOINTS) + "+" + getSkillTracker().getGainedLevels(Skill.HITPOINTS) + ") xp: " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS), 5, 105);
	}
}
