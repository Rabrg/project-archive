package me.matt.nature.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

import me.matt.nature.RabrgNature;

public final class FailsafeTeleportNode extends Node {

	private Item glory;

	public FailsafeTeleportNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getClient().isLoggedIn() && !EDGEVILLE_AREA.contains(ctx.getLocalPlayer()) && !WILDERNESS_AREA.contains(ctx.getLocalPlayer()) && !ABYSS_AREA.contains(ctx.getLocalPlayer()) && !ALTAR_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		MethodProvider.sleepUntil(new Condition() {
			@Override
			public boolean verify() {
				return EDGEVILLE_AREA.contains(ctx.getLocalPlayer()) || WILDERNESS_AREA.contains(ctx.getLocalPlayer()) || ABYSS_AREA.contains(ctx.getLocalPlayer()) || ALTAR_AREA.contains(ctx.getLocalPlayer());
			}
		}, Calculations.random(3200, 4800));
		if (!EDGEVILLE_AREA.contains(ctx.getLocalPlayer()) && !WILDERNESS_AREA.contains(ctx.getLocalPlayer()) && !ABYSS_AREA.contains(ctx.getLocalPlayer()) && !ALTAR_AREA.contains(ctx.getLocalPlayer())) {
			if ((glory = ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot())) != null) {
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
					RabrgNature.failsafes++;
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return EDGEVILLE_AREA.contains(ctx.getLocalPlayer());
						}
					}, Calculations.random(6400, 7200));
				}
			} else {
				ctx.getTabs().logout();
			}
		}
		return Calculations.random(25, 125);
	}

	@Override
	public String getName() {
		return "failsafe teleport";
	}

}
