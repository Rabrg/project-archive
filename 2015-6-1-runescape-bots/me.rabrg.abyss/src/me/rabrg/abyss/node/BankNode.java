package me.rabrg.abyss.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import me.rabrg.abyss.RabrgAbyssPro;

public final class BankNode extends Node {

	private WidgetChild runningWidget;
	private Item amulet;
	private Item chargedGlory;

	private int pureEssences;

	public BankNode(final RabrgAbyssPro script) {
		super(script);
	}

	@Override
	public boolean validate() {
		runningWidget = script.getWidgets().getChildWidget(160, 27);
		pureEssences = script.getInventory().count("Pure essence");
		amulet = script.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot());
		chargedGlory = script.getInventory().get("Amulet of glory(6)");
		if (chargedGlory == null) {
			chargedGlory = script.getInventory().get("Amulet of glory(4)");
		}
		return EDGEVILLE_AREA.contains(script.getLocalPlayer()) && (!script.getInventory().isFull() || script.getInventory().contains(script.FOOD_NAME) || (script.getSmallPouch().isUsing() && !script.getSmallPouch().isFull()) || (script.getMediumPouch().isUsing() && !script.getMediumPouch().isFull()) || (script.getLargePouch().isUsing() && !script.getLargePouch().isFull()) || (script.getGiantPouch().isUsing() && !script.getGiantPouch().isFull()) || amulet == null || amulet.getName().equals("Amulet of glory"));
	}

	@Override
	public void execute() {
		// drop item
		if (script.getInventory().isFull() && amulet != null && amulet.getName().equals("Amulet of glory") && !script.getBank().isOpen() && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && script.getInventory().contains("Pure essence") && script.getInventory().get("Pure essence").interact("Drop")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return !script.getInventory().isFull();
				}
			}, Calculations.random(900, 1200));
		} else
		// remove amulet
		if (!script.getInventory().isFull() && amulet != null && amulet.getName().equals("Amulet of glory") && !script.getBank().isOpen() && (script.getTabs().isOpen(Tab.EQUIPMENT) || script.getTabs().open(Tab.EQUIPMENT)) && amulet.interact("Remove")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return (amulet = script.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot())) == null;
				}
			}, Calculations.random(900, 1200));
		}
		// equip charged amulet
		else if (amulet == null && chargedGlory != null && !script.getBank().isOpen() && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && chargedGlory.interact("Wear")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return (amulet = script.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot())) != null;
				}
			}, Calculations.random(900, 1200));
		}
		// fill giant pouch
		else if (!script.getBank().isOpen() && script.getGiantPouch().isUsing() && !script.getGiantPouch().isFull() && script.getInventory().count("Pure essence") >= script.getGiantPouch().getCapacity() && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && script.getInventory().get("Giant pouch").interact("Fill")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return pureEssences != script.getInventory().count("Pure essence");
				}
			}, Calculations.random(900, 1200));
		}
		// fill large pouch
		else if (!script.getBank().isOpen() && script.getLargePouch().isUsing() && !script.getLargePouch().isFull() && script.getInventory().count("Pure essence") >= script.getLargePouch().getCapacity() && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && script.getInventory().get("Large pouch").interact("Fill")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return pureEssences != script.getInventory().count("Pure essence");
				}
			}, Calculations.random(900, 1200));
		}
		// fill medium pouch
		else if (!script.getBank().isOpen() && script.getMediumPouch().isUsing() && !script.getMediumPouch().isFull() && script.getInventory().count("Pure essence") >= script.getMediumPouch().getCapacity() && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && script.getInventory().get("Medium pouch").interact("Fill")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return pureEssences != script.getInventory().count("Pure essence");
				}
			}, Calculations.random(900, 1200));
		}
		// fill small pouch
		else if (!script.getBank().isOpen() && script.getSmallPouch().isUsing() && !script.getSmallPouch().isFull() && script.getInventory().count("Pure essence") >= script.getSmallPouch().getCapacity() && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && script.getInventory().get("Small pouch").interact("Fill")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return pureEssences != script.getInventory().count("Pure essence");
				}
			}, Calculations.random(900, 1200));
		}
		// eat food
		else if (!script.getBank().isOpen() && script.getInventory().contains(script.FOOD_NAME) && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && script.getInventory().get(script.FOOD_NAME).interact("Eat")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return script.getLocalPlayer().getAnimation() > 0;
				}
			}, Calculations.random(900, 1200));
		}
		// drink stamina potion
		else if (script.STAMINA_POTIONS && runningWidget.getTextureId() != 1092 && !script.getBank().isOpen() && script.getInventory().contains(STAMINA_POTION_FILTER) && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && script.getInventory().get(STAMINA_POTION_FILTER).interact("Drink")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return runningWidget.getTextureId() == 1092;
				}
			}, Calculations.random(900, 1200));
		}
		// open bank
		else if (!script.getBank().isOpen() && (script.getInventory().getEmptySlots() != 0 || (amulet == null && chargedGlory == null)) && script.getBank().openClosest()) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return script.getBank().isOpen();
				}
			}, Calculations.random(4800, 7200));
		}
		// deposit runes
		else if (script.getBank().isOpen() && script.getInventory().contains(script.RUNE + " rune") && script.getBank().depositAll(script.RUNE + " rune")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return !script.getInventory().contains(script.RUNE + " rune");
				}
			}, Calculations.random(900, 1200));
		}
		// deposit stamina potion
		else if (script.getBank().isOpen() && runningWidget.getTextureId() == 1092 && (script.getInventory().contains(STAMINA_POTION_FILTER) || script.getInventory().contains("Vial")) && (script.getBank().depositAll(STAMINA_POTION_FILTER) || script.getBank().depositAll("Vial"))) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return !script.getInventory().contains(STAMINA_POTION_FILTER) && !script.getInventory().contains("Vial");
				}
			}, Calculations.random(900, 1200));
		}
		// deposit glory
		else if (script.getBank().isOpen() && script.getInventory().contains("Amulet of glory") && script.getBank().depositAll("Amulet of glory")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return !script.getInventory().contains("Amulet of glory");
				}
			}, Calculations.random(900, 1200));
		}
		// withdraw charged glory
		else if (script.getBank().isOpen() && amulet == null && chargedGlory == null && !script.getInventory().isFull() && (script.getBank().withdraw("Amulet of glory(6)") || script.getBank().withdraw("Amulet of glory(4)"))) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return script.getInventory().contains("Amulet of glory(6)", "Amulet of glory(4)");
				}
			}, Calculations.random(900, 1200));
		}
		// deposit essence for glory
		else if (script.getBank().isOpen() && amulet == null && chargedGlory == null && script.getInventory().isFull() && script.getBank().deposit("Pure essence")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return !script.getInventory().isFull();
				}
			}, Calculations.random(900, 1200));
		}
		// withdraw food
		else if (script.getBank().isOpen() && !script.getInventory().contains(script.FOOD_NAME) && (script.getSkills().getBoostedLevels(Skill.HITPOINTS) - 1) + script.FOOD_HEAL  <= script.getSkills().getRealLevel(Skill.HITPOINTS) && script.getBank().withdraw(script.FOOD_NAME, (script.getSkills().getRealLevel(Skill.HITPOINTS) - script.getSkills().getBoostedLevels(Skill.HITPOINTS)) / (script.FOOD_HEAL - 3))) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return script.getInventory().contains(script.FOOD_NAME);
				}
			}, Calculations.random(900, 1200));
		}
		// withdraw stamina potion: script.STAMINA_POTIONS && runningWidget.getTextureId() != 1092
		else if (script.getBank().isOpen() && !script.getInventory().contains(STAMINA_POTION_FILTER) && script.STAMINA_POTIONS && runningWidget.getTextureId() != 1092 && script.getWalking().getRunEnergy() < 80 && (script.getBank().withdraw("Stamina potion(1)") || script.getBank().withdraw("Stamina potion(2)") || script.getBank().withdraw("Stamina potion(3)") || script.getBank().withdraw("Stamina potion(4)"))) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return script.getInventory().contains(STAMINA_POTION_FILTER);
				}
			}, Calculations.random(900, 1200));
		}
		// withdraw pure essence
		else if (script.getBank().isOpen() && script.getInventory().getEmptySlots() > 0 && script.getBank().withdrawAll("Pure essence")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return script.getInventory().isFull();
				}
			}, Calculations.random(900, 1200));
		}
		// close bank
		else if (script.getBank().isOpen() && (script.getInventory().isFull() || script.getInventory().contains("Amulet of glory(6)") || script.getInventory().contains(script.FOOD_NAME)) && script.getBank().close()) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return !script.getBank().isOpen();
				}
			}, Calculations.random(900, 1200));
		}
	}

	@Override
	public String toString() {
		return "Bank";
	}

}
