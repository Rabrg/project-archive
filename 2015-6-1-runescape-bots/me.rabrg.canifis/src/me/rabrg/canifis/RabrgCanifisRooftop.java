package me.rabrg.canifis;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import me.rabrg.canifis.node.NavigateObstacleNode;
import me.rabrg.canifis.node.Node;
import me.rabrg.canifis.node.TakeMarkeOfGraceNode;

@ScriptManifest(author="Rabrg", category=Category.AGILITY, name="Rabrg Canifis Agility", version=0.1, description="Completes the Canifis rooftop agility course")
public final class RabrgCanifisRooftop extends AbstractScript {

	private final Node[] nodes = { new TakeMarkeOfGraceNode(this), new NavigateObstacleNode(this) };

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.AGILITY);
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes)
			if (node.validate())
				return node.execute();
		return Calculations.random(5, 25);
	}

	@Override
	public void onPaint(final Graphics g) {
		g.drawString("Agility xp: " + getSkillTracker().getGainedExperience(Skill.AGILITY), 15, 15);
		g.drawString("Agility xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.AGILITY), 15, 30);
	}
}
