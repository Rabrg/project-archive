package me.rabrg.fire.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.wrappers.items.Item;

public final class TeleportDuelArenaNode extends Node {

	private Item ring;

	public TeleportDuelArenaNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().isFull() && CASTLE_WARS_AREA.contains(ctx.getLocalPlayer()) && (ring = ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot())) != null;
	}

	@Override
	public int execute() {
		if (ctx.getTabs().getOpen() != Tab.EQUIPMENT) {
			ctx.getTabs().open(Tab.EQUIPMENT);
		} else {
			ring.interact("Duel Arena");
			return Calculations.random(4200, 4800);
		}
		return Calculations.random(400, 600);
	}

	@Override
	public String getName() {
		return "Teleporting duel arena";
	}

}
