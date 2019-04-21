package me.rabrg.goldsmith;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

@ScriptManifest(author="Rabrg", category= Category.SMITHING, name="Gold Smith", version=0.1, description="Smiths gold")
public final class GoldSmither extends AbstractScript {

	private static final String GEM = "Ruby";

	private Widget widget;

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.CRAFTING);
	}

	@Override
	public int onLoop() {
		if (!getInventory().contains("Gold bar") || !getInventory().contains(GEM) || getBank().isOpen()) {
			if (!getBank().isOpen()) {
				getWalking().setRunThreshold(Calculations.random(25, 70));
				if (getGameObjects().closest("Bank booth").interact()) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return getBank().isOpen();
						}
					}, Calculations.random(9000, 18000));
				}
			} else {
				if (getInventory().contains(JEWELRY_FILTER)) {
					if (getBank().depositAll(JEWELRY_FILTER)) {
						sleepUntil(new Condition() {
							@Override
							public boolean verify() {
								return !getInventory().contains(JEWELRY_FILTER);
							}
						}, Calculations.random(1200, 1800));
					}
				} else if (!getInventory().contains("Gold bar")) {
					if (getBank().count("Gold bar") < 13)
						stop();
					if (getBank().withdraw("Gold bar", 13)) {
						sleepUntil(new Condition() {
							@Override
							public boolean verify() {
								return getInventory().contains("Gold bar");
							}
						}, Calculations.random(1200, 1800));
					}
				} else if (!getInventory().contains(GEM)) {
					if (getBank().count(GEM) < 13)
						stop();
					if (getBank().withdraw(GEM, 13)) {
						sleepUntil(new Condition() {
							@Override
							public boolean verify() {
								return getInventory().contains(JEWELRY_FILTER);
							}
						}, Calculations.random(1200, 1800));
					}
				} else {
					if (getBank().close()) {
						sleepUntil(new Condition() {
							@Override
							public boolean verify() {
								return !getBank().isOpen();
							}
						}, Calculations.random(1200, 1800));
					}
				}
			}
		} else if ((widget = getWidgets().getWidget(446)) == null || !widget.isVisible()){
			if (!getInventory().isItemSelected() && getInventory().get("Gold bar").interact()) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getInventory().isItemSelected();
					}
				}, Calculations.random(1200, 1800));
			} else if (getCamera().rotateToEntity(getGameObjects().closest("Furnace")) && getGameObjects().closest("Furnace").interactForceRight("Use")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return (widget = getWidgets().getWidget(446)) != null && widget.isVisible();
					}
				}, Calculations.random(9000, 18000));
			}
		} else if (getDialogues().inDialogue()) {
			getKeyboard().type(13, true);
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !getDialogues().inDialogue();
				}
			}, Calculations.random(2400, 4800));
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return getDialogues().inDialogue() || !getInventory().contains("Gold bar") || !getInventory().contains(GEM);
				}
			}, Calculations.random(18000, 36000));
		} else if (widget.getChild(35).interact("Make-X")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return getDialogues().inDialogue();
				}
			}, Calculations.random(2400, 4800));
		}
		return Calculations.random(0, 1024);
	}

	@Override
	public void onPaint(final Graphics g) {
		g.drawString("Rabrg Gold Smith", 15, 45);
		g.drawString("Crafting (" + getSkillTracker().getStartLevel(Skill.CRAFTING) + "+" + getSkillTracker().getGainedLevels(Skill.CRAFTING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.CRAFTING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.CRAFTING), 15, 60);
	}

	private static final Filter<Item> JEWELRY_FILTER = new Filter<Item>() {
		@Override
		public boolean match(final Item item) {
			return item != null && item.getName() != null && (item.getName().toLowerCase().contains("ring") || item.getName().toLowerCase().contains("amulet")) && !item.getName().contains("mould");
		}
	};
}
