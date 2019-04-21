package me.rabrg.dance;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

@ScriptManifest(author = "Rabrg", category = Category.THIEVING, name = "Rabrg Man", version = 0.2)
public final class RabrgDance extends AbstractScript {

	private NPC thieving;
	private int nextEat = Calculations.random(7,  20);

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.THIEVING);
	}

	@Override
	public int onLoop() {
		if (getSkills().getBoostedLevels(Skill.HITPOINTS) <= nextEat) {
			while (getSkills().getRealLevel(Skill.HITPOINTS) >= getSkills().getBoostedLevels(Skill.HITPOINTS) + 4)
				getInventory().get(new Filter<Item>() {
					@Override
					public boolean match(final Item item) {
						if (item == null || item.getName() == null)
							return false;
						return item.getName().toLowerCase().contains("cake");
					}
				}).interact("Eat");
		} else if ((thieving = getNpcs().closest("Al-Kharid warrior")) != null) {
			if (thieving.interact("Pickpocket")) {
				sleep(0, 1200);
			}
		}
		return Calculations.random(25, 600);
	}

	@Override
	public void onPaint(final Graphics g) {
		g.drawString("Thieving (" + getSkillTracker().getStartLevel(Skill.THIEVING) + "+" + getSkillTracker().getGainedLevels(Skill.THIEVING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.THIEVING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.THIEVING), 5, 60);
	}
}

