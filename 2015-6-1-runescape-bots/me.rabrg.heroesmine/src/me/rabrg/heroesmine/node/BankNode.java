package me.rabrg.heroesmine.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.impl.Condition;

import me.rabrg.heroesmine.HereosMine;

public final class BankNode extends Node {

	private final HereosMine script;

	public BankNode(final MethodContext ctx, final HereosMine script) {
		super(ctx);
		this.script = script;
	}

	@Override
	public boolean validate() {
		return ((MINE_AREA.contains(ctx.getLocalPlayer())  && ctx.getInventory().isFull()) || CASTLEWARS_AREA.contains(ctx.getLocalPlayer()));
	}

	@Override
	public int execute() {
		if (MINE_AREA.contains(ctx.getLocalPlayer()) && !ctx.getTabs().isOpen(Tab.EQUIPMENT) && ctx.getTabs().open(Tab.EQUIPMENT)) {
			MethodProvider.log("1");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return ctx.getTabs().isOpen(Tab.EQUIPMENT);
				}
			}, Calculations.random(300, 600));
		} else if (MINE_AREA.contains(ctx.getLocalPlayer()) && ctx.getTabs().isOpen(Tab.EQUIPMENT) && ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()).interact("Castle Wars")) {
			MethodProvider.log("2");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return !MINE_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(3600, 4200));
		} else if (CASTLEWARS_AREA.contains(ctx.getLocalPlayer()) && ctx.getBank().isOpen() && !ctx.getBank().containsAll("Ring of dueling(8)", "Games necklace(8)")) {
			MethodProvider.log("14");
			if (ctx.getBank().close()) {
				ctx.getTabs().logout();
				script.stop();
			}
		} else if (CASTLEWARS_AREA.contains(ctx.getLocalPlayer()) && !ctx.getBank().isOpen() && ((ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) == null && !ctx.getInventory().contains("Ring of dueling(8)")) || (ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()) == null && !ctx.getInventory().contains("Games necklace(8)")) || ctx.getInventory().contains("Runite ore")) && ctx.getBank().openClosest()) {
			MethodProvider.log("3");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return ctx.getBank().isOpen();
				}
			}, Calculations.random(3600, 4200));
		} else if (CASTLEWARS_AREA.contains(ctx.getLocalPlayer()) && ctx.getBank().isOpen() && ctx.getInventory().contains("Runite ore") && ctx.getBank().depositAll("Runite ore")) {
			MethodProvider.log("4");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return !ctx.getInventory().contains("Runite ore");
				}
			}, Calculations.random(900, 1200));
		} else if (CASTLEWARS_AREA.contains(ctx.getLocalPlayer()) && ctx.getBank().isOpen() && ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) == null && !ctx.getInventory().contains("Ring of dueling(8)") && ctx.getBank().withdraw("Ring of dueling(8)")) {
			MethodProvider.log("5");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return ctx.getInventory().contains("Ring of dueling(8)");
				}
			}, Calculations.random(1200, 1800));
		} else if (CASTLEWARS_AREA.contains(ctx.getLocalPlayer()) && !ctx.getBank().isOpen() && ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) == null && ctx.getInventory().contains("Ring of dueling(8)") && !ctx.getTabs().isOpen(Tab.INVENTORY) && ctx.getTabs().open(Tab.INVENTORY)) {
			MethodProvider.log("6");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return ctx.getTabs().isOpen(Tab.EQUIPMENT);
				}
			}, Calculations.random(300, 600));
		} else if (CASTLEWARS_AREA.contains(ctx.getLocalPlayer()) && !ctx.getBank().isOpen() && ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) == null && ctx.getInventory().contains("Ring of dueling(8)") && ctx.getTabs().isOpen(Tab.INVENTORY) && ctx.getInventory().get("Ring of dueling(8)").interact("Wear")) {
			MethodProvider.log("7");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) != null;
				}
			}, Calculations.random(900, 1200));
		} else if (CASTLEWARS_AREA.contains(ctx.getLocalPlayer()) && ctx.getBank().isOpen() && ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()) == null && !ctx.getInventory().contains("Games necklace(8)") && ctx.getBank().withdraw("Games necklace(8)")) {
			MethodProvider.log("8");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return ctx.getInventory().contains("Ring of dueling(8)");
				}
			}, Calculations.random(1200, 1800));
		} else if (CASTLEWARS_AREA.contains(ctx.getLocalPlayer()) && !ctx.getBank().isOpen() && ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()) == null && ctx.getInventory().contains("Games necklace(8)") && !ctx.getTabs().isOpen(Tab.INVENTORY) && ctx.getTabs().open(Tab.INVENTORY)) {
			MethodProvider.log("9");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return ctx.getTabs().isOpen(Tab.EQUIPMENT);
				}
			}, Calculations.random(300, 600));
		} else if (CASTLEWARS_AREA.contains(ctx.getLocalPlayer()) && !ctx.getBank().isOpen() && ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()) == null && ctx.getInventory().contains("Games necklace(8)") && ctx.getTabs().isOpen(Tab.INVENTORY) && ctx.getInventory().get("Games necklace(8)").interact("Wear")) {
			MethodProvider.log("10");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()) != null;
				}
			}, Calculations.random(900, 1200));
		} else if (ctx.getBank().isOpen() && ctx.getBank().close()) {
			MethodProvider.log("11");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return !ctx.getBank().isOpen();
				}
			}, Calculations.random(900, 1200));
		} else if (CASTLEWARS_AREA.contains(ctx.getLocalPlayer()) && !ctx.getInventory().contains("Runite ore") && !ctx.getTabs().isOpen(Tab.EQUIPMENT) && ctx.getTabs().open(Tab.EQUIPMENT)) {
			MethodProvider.log("12");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return ctx.getTabs().isOpen(Tab.EQUIPMENT);
				}
			}, Calculations.random(300, 600));
		} else if (CASTLEWARS_AREA.contains(ctx.getLocalPlayer()) && !ctx.getInventory().contains("Runite ore") && ctx.getTabs().isOpen(Tab.EQUIPMENT) && ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()).interact("Burthorpe")) {
			MethodProvider.log("13");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return !CASTLEWARS_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(3600, 4200));
		}
		return Calculations.random(300);
	}

	@Override
	public String getName() {
		return "Banking";
	}

}
