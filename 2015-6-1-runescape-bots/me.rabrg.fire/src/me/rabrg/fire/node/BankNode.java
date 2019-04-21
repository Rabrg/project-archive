package me.rabrg.fire.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class BankNode extends Node {

	public BankNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ((!ctx.getInventory().isFull() || (!smallPouch && ctx.getInventory().contains("Small pouch")) || (!mediumPouch && ctx.getInventory().contains("Medium pouch")) || ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) == null) || ctx.getBank().isOpen()) && CASTLE_WARS_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		if (!ctx.getBank().isOpen()) {
			if (ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) != null && ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()).getName().equals("Ring of dueling(1)")) {
				if (ctx.getTabs().getOpen() != Tab.EQUIPMENT) {
					ctx.getTabs().open(Tab.EQUIPMENT);
				} else {
					ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()).interact("Remove");
				}
			} else if (ctx.getTabs().getOpen() != Tab.INVENTORY) {
				ctx.getTabs().open(Tab.INVENTORY);
				return Calculations.random(400, 600);
			} else if (ctx.getInventory().contains("Ring of dueling(8)")) {
				ctx.getInventory().get("Ring of dueling(8)").interact("Wear");
			} else if (ctx.getInventory().count("Pure essence") >= 6 && (!mediumPouch && ctx.getInventory().contains("Medium pouch"))) {
				if (ctx.getInventory().get("Medium pouch").interact("Fill")) {
					mediumPouch = true;
				}
			} else if (ctx.getInventory().count("Pure essence") >= 3 && (!smallPouch && ctx.getInventory().contains("Small pouch"))) {
				if (ctx.getInventory().get("Small pouch").interact("Fill")) {
					smallPouch = true;
				}
			} else {
				final GameObject bankChest = ctx.getGameObjects().closest("Bank chest");
				if (!bankChest.isOnScreen()) {
					ctx.getCamera().rotateToEntity(bankChest);
				} else {
					bankChest.interact("Use");
				}
			}
		} else {
			if (ctx.getInventory().contains("Fire rune")) {
				ctx.getBank().depositAll("Fire rune");
			} else if (ctx.getInventory().contains("Ring of dueling(1)")) {
				ctx.getBank().deposit("Ring of dueling(1)");
			} else if (ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) == null && !ctx.getInventory().contains("Ring of dueling(8)")) {
				ctx.getBank().withdraw("Ring of dueling(8)");
			} else if (!ctx.getInventory().isFull()) {
				ctx.getBank().withdrawAll("Pure essence");
			} else {
				ctx.getBank().close();
			}
		}
		return Calculations.random(800, 900);
	}

	@Override
	public String getName() {
		return "Banking";
	}

}
