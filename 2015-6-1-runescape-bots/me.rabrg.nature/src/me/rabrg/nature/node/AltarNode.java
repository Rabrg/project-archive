package me.rabrg.nature.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

import me.rabrg.nature.RabrgNature;

public final class AltarNode extends Node {

	private GameObject altar;
	private Item glory;

	public AltarNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ALTAR_AREA.contains(ctx.getLocalPlayer()) && (altar = ctx.getGameObjects().closest("Altar")) != null;
	}

	@Override
	public int execute() {
		if ((ctx.getInventory().contains("Pure essence") || giantFilled || largeFilled || mediumFilled || smallFilled)) {
			if (altar.distance() > 7) {
				ctx.getWalking().walk(altar);
				return Calculations.random(600, 1800);
			} else if (!altar.isOnScreen()) {
				ctx.getCamera().rotateToEntity(altar);
				return Calculations.random(150, 300);
			} else {
				final int slots = ctx.getInventory().getEmptySlots();
				if (!ctx.getTabs().isOpen(Tab.INVENTORY)) {
					if (ctx.getTabs().open(Tab.INVENTORY)) {
						MethodProvider.sleepUntil(new Condition() {
							@Override
							public boolean verify() {
								return ctx.getTabs().isOpen(Tab.INVENTORY);
							}
						}, Calculations.random(750, 900));
					}
				} else if (giantFilled && slots >= 12) {
					if (ctx.getInventory().get("Giant pouch").interact("Empty")) {
						giantFilled = false;
						MethodProvider.sleepUntil(new Condition() {
							@Override
							public boolean verify() {
								return slots != ctx.getInventory().getEmptySlots();
							}
						}, Calculations.random(750, 900));
					}
				} else if (largeFilled && slots >= 9) {
					if (ctx.getInventory().get("Large pouch").interact("Empty")) {
						largeFilled = false;
						MethodProvider.sleepUntil(new Condition() {
							@Override
							public boolean verify() {
								return slots != ctx.getInventory().getEmptySlots();
							}
						}, Calculations.random(750, 900));
					}
				} else if (mediumFilled && slots >= 6) {
					if (ctx.getInventory().get("Medium pouch").interact("Empty")) {
						mediumFilled = false;
						MethodProvider.sleepUntil(new Condition() {
							@Override
							public boolean verify() {
								return slots != ctx.getInventory().getEmptySlots();
							}
						}, Calculations.random(750, 900));
					}
				} else if (smallFilled && slots >= 3) {
					if (ctx.getInventory().get("Small pouch").interact("Empty")) {
						smallFilled = false;
						MethodProvider.sleepUntil(new Condition() {
							@Override
							public boolean verify() {
								return slots != ctx.getInventory().getEmptySlots();
							}
						}, Calculations.random(750, 900));
					}
				} else if (altar.interact("Craft-rune")) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return slots != ctx.getInventory().getEmptySlots();
						}
					}, Calculations.random(6400, 7200));
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return ctx.getLocalPlayer().getAnimation() != 791;
						}
					}, Calculations.random(3000, 4200));
				}
			}
		} else if ((glory = ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot())) != null) {
			if (!ctx.getTabs().isOpen(Tab.EQUIPMENT)) {
				if (ctx.getTabs().open(Tab.EQUIPMENT)) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return ctx.getTabs().isOpen(Tab.EQUIPMENT);
						}
					}, Calculations.random(750, 900));
				}
			} else if (glory.interact("Edgeville")) {
				RabrgNature.crafted += ctx.getInventory().count("Nature rune");
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !ALTAR_AREA.contains(ctx.getLocalPlayer());
					}
				}, Calculations.random(6400, 7200));
			}
		}
		return Calculations.random(25, 125);
	}

	@Override
	public String getName() {
		return "altar";
	}

}
