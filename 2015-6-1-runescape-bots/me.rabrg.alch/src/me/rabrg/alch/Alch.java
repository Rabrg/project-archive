package me.rabrg.alch;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

@ScriptManifest(author = "Rabrg", category = Category.MAGIC, name = "Alch", version = 69)
public final class Alch extends AbstractScript {

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.MAGIC, true);
	}

	@Override
	public int onLoop() {
		if (!getInventory().contains("Nature rune")) {
			getTabs().logout();
			stop();
		} else if (getPlayers().all().size() > 1) {
			getWorldHopper().quickHop(getWorlds().getRandomWorld(new Filter<World>() {
				@Override
				public boolean match(final World world) {
					return world.isMembers() && !world.isPVP();
				}
			}).getID());
		} else if (getMagic().isSpellSelected() &&
			getInventory().get(new Filter<Item>() {
				@Override
				public boolean match(final Item item) {
					return item != null && !item.getName().equals("Nature rune") && !item.getName().equals("Coins");
				}
			}).interact()) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return getTabs().getOpen() == Tab.MAGIC;
				}
			}, Calculations.random(2048, 2560));
		} else if (!getMagic().isSpellSelected() && !getTabs().isOpen(Tab.MAGIC) && getTabs().open(Tab.MAGIC)) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return getTabs().getOpen() == Tab.MAGIC;
				}
			}, Calculations.random(256, 512));
		} else if (!getMagic().isSpellSelected() && getTabs().isOpen(Tab.MAGIC) && getSkills().getBoostedLevels(Skill.MAGIC) < 55 && getMagic().castSpell(Normal.LOW_LEVEL_ALCHEMY)) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return getMagic().isSpellSelected();
				}
			}, Calculations.random(256, 512));
		} else if (!getMagic().isSpellSelected() && getTabs().isOpen(Tab.MAGIC) && getSkills().getBoostedLevels(Skill.MAGIC) >= 55 && getMagic().castSpell(Normal.HIGH_LEVEL_ALCHEMY)) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return getMagic().isSpellSelected();
				}
			}, Calculations.random(256, 512));
		}
		return Calculations.random(0, 128);
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Magic (" + getSkillTracker().getStartLevel(Skill.MAGIC) + "+" + getSkillTracker().getGainedLevels(Skill.MAGIC) + ") xp: " + getSkillTracker().getGainedExperience(Skill.MAGIC) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC), 15, 45);
	}

}
