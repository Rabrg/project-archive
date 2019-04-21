package me.matt.ironmine;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;

@ScriptManifest(author = "Rabrg", category = Category.MINING, name = "Tiger Miner", version = 0.1)
public final class IronMiner extends AbstractScript {

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
		if (Calculations.random(0.0, 1.0) > .95) {
			getCamera().rotateToEvent(Calculations.random(0, 360), Calculations.random(0, 360)).run();
		} else if (getInventory().isFull()) {
			getInventory().dropAllExcept(new Filter<Item>() {
				@Override
				public boolean match(final Item item) {
					return item.getName().contains("pickaxe");
				}
			});
		} else if (getLocalPlayer().getAnimation() < 1) {
			final GameObject rocks = getGameObjects().closest(21702, 21703, 21704);
			if (rocks != null) {
				if (rocks.interactForceLeft("Mine")) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return getGameObjects().getTopObjectOnTile(rocks.getTile()).getID() != rocks.getID();
						}
					}, Calculations.random(750, 2400));
				}
			}
		}
		return Calculations.random(0, 1200);
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Mining (" + getSkillTracker().getStartLevel(Skill.MINING) + "+" + getSkillTracker().getGainedLevels(Skill.MINING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.MINING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.MINING), 5, 30);
	}
}
