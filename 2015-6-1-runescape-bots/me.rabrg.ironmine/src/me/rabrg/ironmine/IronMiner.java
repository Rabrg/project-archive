package me.rabrg.ironmine;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;

@ScriptManifest(author = "Rabrg", category = Category.MINING, name = "R Iron Miner", version = 0.1)
public final class IronMiner extends AbstractScript {

	private static final Tile CENTER_TILE = new Tile(2338, 3640);

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.MINING);
	}

	@Override
	public int onLoop() {
		for (final Player player : getPlayers().all()) {
			if (!player.getName().equals(getLocalPlayer().getName())) {
				getWorldHopper().quickHop(getWorlds().getRandomWorld(new Filter<World>() {
					@Override
					public boolean match(final World world) {
						return world.isMembers() && !world.isPVP();
					}
				}).getID());
				sleep(4200, 8400);
				break;
			}
		}
		final GameObject rocks = getGameObjects().closest(12605, 12606);
		if (!getLocalPlayer().getTile().equals(CENTER_TILE)) {
			getWalking().walkExact(CENTER_TILE);
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return getLocalPlayer().getTile().equals(CENTER_TILE);
				}
			}, Calculations.random(750, 2400));
		} else if (getInventory().isFull()) {
			if (!getTabs().isOpen(Tab.INVENTORY)) {
				getTabs().open(Tab.INVENTORY);
			}
			getInventory().dropAllExcept(new Filter<Item>() {
				@Override
				public boolean match(final Item item) {
					return item.getName().contains("pickaxe");
				}
			});
		} else if (getLocalPlayer().getAnimation() < 1) {
			if (rocks != null && rocks.distance() < 2) {
				if (rocks.interactForceLeft("Mine")) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							switch (getGameObjects().getTopObjectOnTile(rocks.getTile()).getID()) {
							case 12605:
							case 12606:
								return false;
							default:
								return true;
							}
						}
					}, Calculations.random(750, 2400));
					if (Calculations.random(0.0, 1.0) > .985) {
						getCamera().rotateTo(Calculations.random(0, 360), Calculations.random(0, 360));
					} else if (Calculations.random(0.0, 1.0) > .985) {
						getSkills().hoverSkill(Skill.MINING);
						sleep(1200, 2400);
					} else if (Calculations.random(0.0, 1.0) > .985) {
						getMouse().moveMouseOutsideScreen();
						sleep(1200, 2400);
					}
				}
			}
		}
		return Calculations.random(0, 512);
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Mining (" + getSkillTracker().getStartLevel(Skill.MINING) + "+" + getSkillTracker().getGainedLevels(Skill.MINING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.MINING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.MINING), 5, 30);
	}
}
