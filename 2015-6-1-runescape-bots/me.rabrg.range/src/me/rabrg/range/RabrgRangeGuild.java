package me.rabrg.range;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.range.node.CloseTargetNode;
import me.rabrg.range.node.DialogueNode;
import me.rabrg.range.node.EquipArrowsNode;
import me.rabrg.range.node.Node;
import me.rabrg.range.node.ReturnNode;
import me.rabrg.range.node.StartNode;
import me.rabrg.range.node.TargetNode;

@ScriptManifest(author="Rabrg", category= Category.COMBAT, name="Rabrg Range Guild", version=0.8, description="Does the target shootout minigame")
public final class RabrgRangeGuild extends AbstractScript {

	private final Node[] nodes = { new EquipArrowsNode(this), new ReturnNode(this), new CloseTargetNode(this), new StartNode(this), new DialogueNode(this), new TargetNode(this) };

	private Node node;

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.RANGED);
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes)
			if ((this.node = node).validate())
				return node.execute();
		return Calculations.random(5, 25);
	}

	@Override
	public void onPaint(final Graphics g) {
		g.drawString("Node: " + (node == null ? "null" : node.getName()), 5, 90);
		g.drawString("Ranged xp: " + getSkillTracker().getGainedExperience(Skill.RANGED), 5, 105);
		g.drawString("Ranged xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.RANGED), 5, 120);
		g.drawString("Levels gained: " + getSkillTracker().getGainedLevels(Skill.RANGED), 5, 135);
	}
}
