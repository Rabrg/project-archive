package me.rabrg.nmz;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;

import me.rabrg.nmz.node.AbsorptionNode;
import me.rabrg.nmz.node.FlickHealNode;
import me.rabrg.nmz.node.Node;
import me.rabrg.nmz.node.OverloadNode;
import me.rabrg.nmz.node.StopScriptNode;

@ScriptManifest(author="Rabrg", category= Category.COMBAT, name="Rabrg Nightmare Zone", version=0.8, description="Drinks overloads and absorption potions in NMZ")
public final class RabrgNightmareZone extends AbstractScript {

	public static int absorptionHitpoints = 900;

	private final Node[] nodes = { new StopScriptNode(this), new FlickHealNode(this), new OverloadNode(this), new AbsorptionNode(this)};

	@Override
	public void onStart() {
		getSkillTracker().start();
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes)
			if (node.validate())
				return node.execute();
		return Calculations.random(5, 25);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().contains("hitpoints of damage")) {
			absorptionHitpoints = Integer.parseInt(message.getMessage().split("have ")[1].split(" hitpoints")[0]);
		}
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Attack xp: " + getSkillTracker().getGainedExperience(Skill.ATTACK), 5, 30);
		g.drawString("Attack xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK), 5, 45);
		g.drawString("Strength xp: " + getSkillTracker().getGainedExperience(Skill.STRENGTH), 5, 60);
		g.drawString("Strength xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH), 5, 75);
		g.drawString("Defence xp: " + getSkillTracker().getGainedExperience(Skill.DEFENCE), 5, 90);
		g.drawString("Defence xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE), 5, 105);
		g.drawString("Hitpoints xp: " + getSkillTracker().getGainedExperience(Skill.HITPOINTS), 5, 120);
		g.drawString("Hitpoints xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS), 5, 135);
	}
}
