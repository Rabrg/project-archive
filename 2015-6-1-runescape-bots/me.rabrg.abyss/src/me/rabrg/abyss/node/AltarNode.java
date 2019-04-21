package me.rabrg.abyss.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

import me.rabrg.abyss.RabrgAbyssPro;

public final class AltarNode extends Node {

	private int emptySlots;

	private GameObject altar;

	public AltarNode(final RabrgAbyssPro script) {
		super(script);
	}

	@Override
	public boolean validate() {
		return ALTAR_AREA.contains(script.getLocalPlayer());
	}

	@Override
	public void execute() {
		if (script.getSmallPouch().isUsing() && script.getSmallPouch().isFull() && (emptySlots = script.getInventory().getEmptySlots()) >= script.getSmallPouch().getCapacity() && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && script.getInventory().get("Small pouch").interact("Empty")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return script.getInventory().getEmptySlots() != emptySlots;
				}
			}, Calculations.random(600, 1200));
		} else if (script.getMediumPouch().isUsing() && script.getMediumPouch().isFull() && (emptySlots = script.getInventory().getEmptySlots()) >= script.getMediumPouch().getCapacity() && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && script.getInventory().get("Medium pouch").interact("Empty")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return script.getInventory().getEmptySlots() != emptySlots;
				}
			}, Calculations.random(600, 1200));
		} else if (script.getLargePouch().isUsing() && script.getLargePouch().isFull() && (emptySlots = script.getInventory().getEmptySlots()) >= script.getLargePouch().getCapacity() && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && script.getInventory().get("Large pouch").interact("Empty")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return script.getInventory().getEmptySlots() != emptySlots;
				}
			}, Calculations.random(600, 1200));
		} else if (script.getGiantPouch().isUsing() && script.getGiantPouch().isFull() && (emptySlots = script.getInventory().getEmptySlots()) >= script.getGiantPouch().getCapacity() && (script.getTabs().isOpen(Tab.INVENTORY) || script.getTabs().open(Tab.INVENTORY)) && script.getInventory().get("Giant pouch").interact("Empty")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return script.getInventory().getEmptySlots() != emptySlots;
				}
			}, Calculations.random(600, 1200));
		} else if (script.getInventory().contains("Pure essence") && (altar = script.getGameObjects().closest("Altar")) != null && altar.interact("Craft-rune")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return script.getLocalPlayer().getAnimation() > 1;
				}
			}, Calculations.random(3600, 4200));
			if (script.getLocalPlayer().getAnimation() > 1) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						sleep(5, 25);
						return script.getLocalPlayer().getAnimation() < 1;
					}
				}, Calculations.random(3600, 4200));
			}
		} else if (!script.getInventory().contains("Pure essence") && (!script.getSmallPouch().isUsing() || !script.getSmallPouch().isFull()) && (!script.getMediumPouch().isUsing() || !script.getMediumPouch().isFull()) && (!script.getLargePouch().isUsing() || !script.getLargePouch().isFull()) && (!script.getGiantPouch().isUsing() || !script.getGiantPouch().isFull()) && (script.getTabs().isOpen(Tab.EQUIPMENT) || script.getTabs().open(Tab.EQUIPMENT))) {
			final Item glory = script.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot());
			if (glory != null && glory.interact("Edgeville")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						sleep(5, 25);
						return !ALTAR_AREA.contains(script.getLocalPlayer());
					}
				}, Calculations.random(4200, 4800));
			}
		}
	}

	@Override
	public String toString() {
		return "Altar";
	}

}
