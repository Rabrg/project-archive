package me.rabrg.fire.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class FireAltarNode extends Node {

	public FireAltarNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return FIRE_ALTAR_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		if (ctx.getInventory().contains("Pure essence")) {
			if (ctx.getTabs().getOpen() != Tab.INVENTORY) {
				ctx.getTabs().open(Tab.INVENTORY);
				return Calculations.random(400, 600);
			} else {
				final GameObject altar = ctx.getGameObjects().closest("Altar");
				if (altar.distance() > 5) {
					ctx.getWalking().walk(altar);
					return Calculations.random(750, 1200);
				} else if (!altar.isOnScreen()) {
					ctx.getCamera().rotateToEntity(altar);
					return Calculations.random(350, 500);
				} else if (altar.interact("Craft-rune")) {
					MethodProvider.sleep(750, 825);
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return !ctx.getLocalPlayer().isAnimating() && !ctx.getLocalPlayer().isMoving();
						}
					}, Calculations.random(6000, 7500));
				}
			}
		} else {
			if (ctx.getTabs().getOpen() != Tab.EQUIPMENT) {
				ctx.getTabs().open(Tab.EQUIPMENT);
			} else {
				ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()).interact("Castle Wars");
				return Calculations.random(3000, 3600);
			}
		}
		return Calculations.random(750, 900);
	}

	@Override
	public String getName() {
		return "FIre altar stuff";
	}

}
