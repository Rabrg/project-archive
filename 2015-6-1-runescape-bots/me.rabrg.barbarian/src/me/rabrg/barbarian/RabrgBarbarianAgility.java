package me.rabrg.barbarian;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;

@ScriptManifest(author="Rabrg", category=Category.AGILITY, name="Rabrg Barbarian Agility", version=0.3, description="Swings on the rope in the Barbarian Agility course")
public final class RabrgBarbarianAgility extends AbstractScript {

	private static final String FOOD_NAME = "Monkfish";

	private static final int RANGE_MIN = 15;
	private static final int RANGE_MAX = 45;

	private int next = Calculations.random(RANGE_MIN, RANGE_MAX);

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.AGILITY);
	}

	@Override
	public int onLoop() {
		if (getSkills().getBoostedLevels(Skill.HITPOINTS) <= next) {
			getInventory().get(FOOD_NAME).interact("Eat");
			next = Calculations.random(RANGE_MIN, RANGE_MAX);
		} else if (!getWalking().isRunEnabled() && getWalking().getRunEnergy() <= next) {
			getWalking().toggleRun();
			next = Calculations.random(RANGE_MIN, RANGE_MAX);
		} else if (getLocalPlayer().getY() >= 9945) {
			if (getGameObjects().closest("Ladder").interact("Climb-up")) {
				sleep(Calculations.random(1450, 1600));
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getLocalPlayer().getX() == 2551;
					}
				}, Calculations.random(1150, 1350));
			}
		} else if (getLocalPlayer().getY() >= 3552) {
			if (getGameObjects().closest(23131).interact("Swing-on")) {
				sleep(Calculations.random(1500, 1750));
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !getLocalPlayer().isAnimating() && !getLocalPlayer().isMoving();
					}
				}, Calculations.random(1150, 1350));
			}
		} else if (getLocalPlayer().getY() < 3552) {
			if (getWalking().walk(new Tile(2551, 3554).getRandomizedTile(1))) {
				sleep(Calculations.random(900, 1050));
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getLocalPlayer().getY() >= 3552;
					}
				}, Calculations.random(1150, 1350));
			}
		}
		return Calculations.random(25, 75);
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Agility xp: " + getSkillTracker().getGainedExperience(Skill.AGILITY), 5, 30);
		g.drawString("Agility xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.AGILITY), 5, 45);
	}
}
