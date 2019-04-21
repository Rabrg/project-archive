package me.rabrg.molten;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.widgets.WidgetChild;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

@ScriptManifest(author = "Rabrg", category = Category.CRAFTING, name = "Molten", version = 0.1)
public final class Molten extends AbstractScript {

	private int nextRun = Calculations.random(25, 70);

	private int moltenGlassCount = 0;

	private WidgetChild child;

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.CRAFTING);
	}

	@Override
	public int onLoop() {
		if (!getWalking().isRunEnabled() && nextRun <= getWalking().getRunEnergy() && getWalking().toggleRun()) {
			nextRun = Calculations.random(25, 70);
		} else if (!getInventory().contains("Soda ash") || !getInventory().contains("Bucket of sand") || getBank().isOpen()) {
			if (!getBank().isOpen() && getGameObjects().getTopObjectOnTile(new Tile(3096, 3493)).interact("Bank")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getBank().isOpen();
					}
				}, Calculations.random(12000, 18000));
			} else if (getBank().isOpen() && (getInventory().contains("Bucket") || getInventory().contains("Molten glass")) && getBank().depositAllItems()) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getInventory().isEmpty();
					}
				}, Calculations.random(1200, 1800));
			} else if (getBank().isOpen() && !getInventory().contains("Bucket of sand") && getBank().withdraw("Bucket of sand", 14)) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getInventory().contains("Bucket of sand");
					}
				}, Calculations.random(1200, 1800));
			} else if (getBank().isOpen() && !getInventory().contains("Soda ash") && getBank().withdraw("Soda ash", 14)) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getInventory().contains("Soda ash");
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
		} else if ((child = getWidgets().getChildWidget(309, 6)) != null && child.isVisible() && child.interact("Make all")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !getInventory().contains("Bucket of sand") || !getInventory().contains("Soda ash") || getDialogues().continueDialogue();
				}
			}, Calculations.random(24000, 36000));
		} else if (getInventory().contains("Bucket of sand") && getInventory().interact("Bucket of sand", "Use") && getCamera().rotateToEntity(getGameObjects().closest("Furnace")) && getGameObjects().closest("Furnace").interact("Use")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return (child = getWidgets().getChildWidget(309, 6)) != null && child.isVisible();
				}
			}, Calculations.random(12000, 18000));
		}
		return Calculations.random(0, 1200);
	}

	@Override
	public void onPaint(final Graphics g) {
		g.drawString("Rabrg Molten Glass", 15, 45);
		g.drawString("Crafting (" + getSkillTracker().getStartLevel(Skill.CRAFTING) + "+" + getSkillTracker().getGainedLevels(Skill.CRAFTING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.CRAFTING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.CRAFTING), 15, 60);
		g.drawString("Molten glass: " + moltenGlassCount, 15, 75);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getType() == MessageType.GAME && message.getMessage().contains("heat the sand and soda ash"))
			moltenGlassCount++;
	}

}
