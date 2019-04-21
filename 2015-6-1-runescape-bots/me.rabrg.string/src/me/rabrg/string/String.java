package me.rabrg.string;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.widgets.WidgetChild;

@ScriptManifest(author = "Rabrg", category = Category.CRAFTING, name = "String", version = 0)
public final class String extends AbstractScript {

	private WidgetChild child;

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.CRAFTING);
	}

	@Override
	public int onLoop() {
		if (!getInventory().contains("Ball of wool") || !getInventory().contains("Ruby amulet (u)") || getBank().isOpen()) {
			if (!getBank().isOpen() && getBank().openClosest()) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getBank().isOpen();
					}
				}, Calculations.random(4200, 8400));
			} else if (getBank().isOpen() && getInventory().contains("Ruby amulet") && getBank().depositAllItems()) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getInventory().isEmpty();
					}
				}, Calculations.random(1200, 1800));
			} else if (getBank().isOpen() && !getInventory().contains("Ball of wool") && getBank().withdraw("Ball of wool", 14)) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getInventory().contains("Ball of wool");
					}
				}, Calculations.random(1200, 1800));
			} else if (getBank().isOpen() && !getInventory().contains("Ruby amulet (u)") && getBank().withdraw("Ruby amulet (u)", 14)) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getInventory().contains("Ruby amulet (u)");
					}
				}, Calculations.random(1200, 1800));
			} else if (getBank().close()) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !getBank().isOpen();
					}
				}, Calculations.random(1200, 1800));
			}
		} else if ((child = getWidgets().getChildWidget(309, 6)) != null && child.isVisible()) {
			if (child.interact("Make All")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !getInventory().contains("Ball of wool") || !getInventory().contains("Ruby amulet (u)");
					}
				}, Calculations.random(24000, 36000));
			}
		} else if (getInventory().contains("Ball of wool") && getInventory().contains("Ruby amulet (u)")) {
			if (getInventory().isItemSelected() && getInventory().get("Ruby amulet (u)").interact("Use")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return (child = getWidgets().getChildWidget(309, 6)) != null && child.isVisible();
					}
				}, Calculations.random(1200, 1800));
			} else if (getInventory().get("Ball of wool").interact("Use")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getInventory().isItemSelected();
					}
				}, Calculations.random(1200, 1800));
			}
		}
		return Calculations.random(0, 300);
	}

	@Override
	public void onPaint(final Graphics g) {
		g.drawString("Rabrg Stringer", 15, 45);
		g.drawString("Crafting (" + getSkillTracker().getStartLevel(Skill.CRAFTING) + "+" + getSkillTracker().getGainedLevels(Skill.CRAFTING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.CRAFTING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.CRAFTING), 15, 60);
	}

}
