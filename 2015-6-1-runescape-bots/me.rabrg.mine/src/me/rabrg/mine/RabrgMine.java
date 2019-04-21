package me.rabrg.mine;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;

@ScriptManifest(author = "Rabrg", category = Category.COMBAT, name = "Rabrg Mine", version = 0.1)
public final class RabrgMine extends AbstractScript {

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.MINING);
	}

	@Override
	public int onLoop() {
		if (this.getPlayers().all(new Filter<Player>() {
			@Override
			public boolean match(final Player player) {
				return !player.getName().equals(getLocalPlayer().getName()) && player.distance() < 10;
			}
		}).size() > 0) {
			getWorldHopper().hopWorld(getWorlds().getRandomWorld(new Filter<World>() {
				@Override
				public boolean match(final World world) {
					return world.isMembers() && !world.isPVP();
				}
			}));
		}
		if (getInventory().isFull()) {
			getInventory().dropAllExcept(new Filter<Item>() {
				@Override
				public boolean match(final Item item) {
					return item.getName().contains("pickaxe");
				}
			});
		} else if (getLocalPlayer().getAnimation() < 1) {
			final GameObject rocks = getGameObjects().closest(13449, 21702, 21703, 21704);
			if (rocks != null) {
				if (rocks.interact("Mine")) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							switch (getGameObjects().getTopObjectOnTile(rocks.getTile()).getID()) {
							case 13449:
							case 21702:
							case 21703:
							case 21704:
							case 21705:
								return false;
							default:
								return true;
							}
						}
					}, Calculations.random(1200, 2400));
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
