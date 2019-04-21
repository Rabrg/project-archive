package me.rabrg.willowcut;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

@ScriptManifest(author = "Rabrg", category = Category.WOODCUTTING, name = "Willow cut", version = 0.1)
public final class WillowCutter extends AbstractScript {

	private int hops;

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.WOODCUTTING);
	}

	@Override
	public int onLoop() {
		if (getPlayers().all().size() > 1) {
			getWorldHopper().quickHop(getWorlds().getRandomWorld(new Filter<World>() {
				@Override
				public boolean match(final World world) {
					return world.isMembers() && !world.isPVP();
				}
			}).getID());
			hops++;
			sleep(4200, 8400);
		}
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
		} else if (getLocalPlayer().getAnimation() < 1) { // make smarter
			final GameObject tree = getGameObjects().closest("Willow");
			if (tree != null) {
				if (tree.interactForceLeft("Chop down")) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return !getGameObjects().getTopObjectOnTile(tree.getTile()).getName().equals("Willow") || getPlayers().all().size() > 1;
						}
					}, Calculations.random(750, 2400));
					if (Calculations.random(0.0, 1.0) > .985) {
						getCamera().rotateTo(Calculations.random(0, 360), Calculations.random(0, 360));
					} else if (Calculations.random(0.0, 1.0) > .995) {
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
		g.drawString("Hops: " + hops, 5, 45);
	}
}
