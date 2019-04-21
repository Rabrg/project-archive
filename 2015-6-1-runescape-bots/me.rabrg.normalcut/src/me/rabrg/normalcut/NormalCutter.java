package me.rabrg.normalcut;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

@ScriptManifest(author = "Rabrg", category = Category.WOODCUTTING, name = "Normal cut", version = 0.1)
public final class NormalCutter extends AbstractScript {

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.WOODCUTTING);
	}

	@Override
	public int onLoop() {
		if (getInventory().isFull()) {
			if (!getTabs().isOpen(Tab.INVENTORY)) {
				getTabs().open(Tab.INVENTORY);
			}
			getInventory().dropAllExcept(new Filter<Item>() {
				@Override
				public boolean match(final Item item) {
					return item.getName().contains("axe");
				}
			});
		} else if (getLocalPlayer().getAnimation() < 1) {
			final GameObject tree = getGameObjects().closest("Tree");
			if (tree != null) {
				if (tree.interactForceLeft("Chop down")) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return !getGameObjects().getTopObjectOnTile(tree.getTile()).getName().equals("Tree");
						}
					}, Calculations.random(750, 2400));
					if (Calculations.random(0.0, 1.0) > .985) {
						getCamera().rotateTo(Calculations.random(0, 360), Calculations.random(0, 360));
					} else if (Calculations.random(0.0, 1.0) > .985) {
						getSkills().hoverSkill(Skill.WOODCUTTING);
						sleep(1200, 2400);
					} else if (Calculations.random(0.0, 1.0) > .985) {
						getMouse().moveMouseOutsideScreen();
						sleep(1200, 2400);
					}
				}
			}
		}
		return Calculations.random(0, 1200);
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Woodcutting (" + getSkillTracker().getStartLevel(Skill.WOODCUTTING) + "+" + getSkillTracker().getGainedLevels(Skill.WOODCUTTING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.WOODCUTTING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.WOODCUTTING), 5, 30);
	}
}
