package me.matt.nature.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

import me.matt.nature.RabrgNature;
import me.matt.nature.node.Node;

public final class BankNode extends Node {

	private static final Filter<Item> STAMINA_FILTER = new Filter<Item>() {
		@Override
		public boolean match(final Item item) {
			return item != null && item.getName() != null && item.getName().startsWith("Stamina");
		}
	};

	private static final int MINIMUM_SLEEP = 751;
	private static final int MAXIMUM_SLEEP = 901;

	private boolean drink;

	public BankNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return EDGEVILLE_AREA.contains(ctx.getLocalPlayer()) && (((ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) + 15) <= ctx.getSkills().getRealLevel(Skill.HITPOINTS)) || ctx.getInventory().contains("Monkfish") || ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()) == null || ctx.getInventory().contains("Amulet of glory(6)") || ctx.getInventory().contains("Vial") || ctx.getInventory().contains(STAMINA_FILTER) || drink || ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) == null || ctx.getInventory().contains("Ring of life") || !ctx.getInventory().isFull() || (!smallFilled && usingSmall) || (!mediumFilled && usingMedium) || (!largeFilled && usingLarge) || (!giantFilled && usingGiant));
	}

	@Override
	public int execute() {
		final int count = ctx.getInventory().count("Pure essence");
		final Item amulet = ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot());
		final Item ring = ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot());
		if (amulet != null && amulet.getName() != null && amulet.getName().equals("Amulet of glory")) {
			MethodProvider.log("1");
			if (ctx.getInventory().isFull()) {
				if (ctx.getTabs().open(Tab.INVENTORY)) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return ctx.getTabs().isOpen(Tab.INVENTORY);
						}
					}, Calculations.random(750, 900));
				} else if (ctx.getInventory().drop("Pure essence")) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return !ctx.getInventory().isFull();
						}
					}, Calculations.random(750, 900));
				}
				MethodProvider.sleep(900, 1200);
			} else if (!ctx.getTabs().isOpen(Tab.EQUIPMENT)) {
				if (ctx.getTabs().open(Tab.EQUIPMENT)) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return ctx.getTabs().isOpen(Tab.EQUIPMENT);
						}
					}, Calculations.random(750, 900));
				}
			} else if (amulet.interact("Remove")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()) == null;
					}
				}, Calculations.random(750, 900));
			}
		} else if (!ctx.getBank().isOpen() && !ctx.getTabs().isOpen(Tab.INVENTORY)) {
			MethodProvider.log("2");
			if (ctx.getTabs().open(Tab.INVENTORY)) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getTabs().isOpen(Tab.INVENTORY);
					}
				}, Calculations.random(750, 900));
			}
		} else if (!ctx.getBank().isOpen() && amulet == null && ctx.getInventory().contains("Amulet of glory(6)")) {
			MethodProvider.log("3");
			if (ctx.getInventory().get("Amulet of glory(6)").interact("Wear")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()) != null;
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (!ctx.getBank().isOpen() && ctx.getInventory().contains(STAMINA_FILTER) && drink) {
			MethodProvider.log("3453");
			final Item pot = ctx.getInventory().get(STAMINA_FILTER);
			if (pot.interact("Drink")) {
				drink = false;
				if (!ctx.getWalking().isRunEnabled()) {
					ctx.getWalking().toggleRun();
				}
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getInventory().get(pot.getID()) == null;
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (!ctx.getBank().isOpen() && ring == null && ctx.getInventory().contains("Ring of life")) {
			MethodProvider.log("1234");
			if (ctx.getInventory().get("Ring of life").interact("Wear")) {
				RabrgNature.ringOfLifes++;
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) != null;
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (!ctx.getBank().isOpen() && ctx.getInventory().contains("Monkfish")) {
			MethodProvider.log("4");
			final int monkfishCount = ctx.getInventory().count("Monkfish");
			if (ctx.getInventory().get("Monkfish").interact("Eat")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return monkfishCount != ctx.getInventory().count("Monkfish");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (!ctx.getBank().isOpen() && !giantFilled && usingGiant && count >= 12) {
			MethodProvider.log("6");
			giantFilled = true;
			if (ctx.getInventory().get("Giant pouch").interact("Fill")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return count != ctx.getInventory().count("Pure essence");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (!ctx.getBank().isOpen() && !largeFilled && usingLarge && count >= 9) {
			MethodProvider.log("7");
			if (ctx.getInventory().get("Large pouch").interact("Fill")) {
				largeFilled = true;
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return count != ctx.getInventory().count("Pure essence");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (!ctx.getBank().isOpen() && !mediumFilled && usingMedium && count >= 6) {
			MethodProvider.log("8");
			if (ctx.getInventory().get("Medium pouch").interact("Fill")) {
				mediumFilled = true;
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return count != ctx.getInventory().count("Pure essence");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (!ctx.getBank().isOpen() && !smallFilled && usingSmall && count >= 3) {
			MethodProvider.log("9");
			if (ctx.getInventory().get("Small pouch").interact("Fill")) {
				smallFilled = true;
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return count != ctx.getInventory().count("Pure essence");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (!ctx.getBank().isOpen()) {
			MethodProvider.log("10");
			if (ctx.getBank().openClosest()) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getBank().isOpen();
					}
				}, Calculations.random(4800, 7200));
			}
		} else if (ctx.getBank().isOpen() && ctx.getInventory().contains("Nature rune")) {
			MethodProvider.log("11");
			if (ctx.getBank().depositAll("Nature rune")) {
				if (ctx.getWalking().getRunEnergy() <= 60) {
					drink = true;
					ctx.getWalking().setRunThreshold(Calculations.random(20, 60));
				}
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !ctx.getInventory().contains("Nature rune");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (ctx.getBank().isOpen() && ctx.getInventory().contains("Amulet of glory")) {
			MethodProvider.log("12");
			if (ctx.getBank().depositAll("Amulet of glory")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !ctx.getInventory().contains("Amulet of glory");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (ctx.getBank().isOpen() && (ctx.getInventory().contains(STAMINA_FILTER) || ctx.getInventory().contains("Vial")) && !drink) {
			MethodProvider.log("12234");
			if (ctx.getBank().depositAll(STAMINA_FILTER) || ctx.getBank().depositAll("Vial")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !ctx.getInventory().contains(STAMINA_FILTER) && !ctx.getInventory().contains("Vial");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (ctx.getBank().isOpen() && ctx.getInventory().contains("Vial")) {
			MethodProvider.log("114");
			if (ctx.getBank().deposit("Vial", 1)) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !ctx.getInventory().contains("Vial");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		} else if (ctx.getBank().isOpen() && amulet == null && !ctx.getInventory().contains("Amulet of glory(6)")) {
			MethodProvider.log("13");
			if (ctx.getInventory().isFull()) {
				ctx.getBank().depositAll("Pure essence");
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !ctx.getInventory().contains("Pure essence");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			} else if (ctx.getBank().withdraw("Amulet of glory(6)")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getInventory().contains("Amulet of glory(6)");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			} else if (!ctx.getBank().contains("Amulet of glory(6)")) {
				ctx.getBank().close();
				MethodProvider.sleep(1200, 1800);
				ctx.getTabs().logout();
			}
		} else if (ctx.getBank().isOpen() && ctx.getInventory().get(STAMINA_FILTER) == null && !ctx.getInventory().contains(STAMINA_FILTER) && drink) {
			MethodProvider.log("13213");
			if (ctx.getInventory().isFull()) {
				ctx.getBank().depositAll("Pure essence");
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !ctx.getInventory().contains("Pure essence");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			} else if (ctx.getBank().withdraw(STAMINA_FILTER)) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getInventory().contains(STAMINA_FILTER);
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			} else if (!ctx.getBank().contains(STAMINA_FILTER)) {
				ctx.getBank().close();
				MethodProvider.sleep(1200, 1800);
				ctx.getTabs().logout();
			}
		} else if (ctx.getBank().isOpen() && ring == null && !ctx.getInventory().contains("Ring of life")) {
			MethodProvider.log("13457");
			if (ctx.getInventory().isFull()) {
				ctx.getBank().depositAll("Pure essence");
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !ctx.getInventory().contains("Pure essence");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			} else if (ctx.getBank().withdraw("Ring of life")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getInventory().contains("Ring of life");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			} else if (!ctx.getBank().contains("Ring of life")) {
				ctx.getBank().close();
				MethodProvider.sleep(1200, 1800);
				ctx.getTabs().logout();
			}
		} else if (ctx.getBank().isOpen() && ((ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) + 15) <= ctx.getSkills().getRealLevel(Skill.HITPOINTS)) && !ctx.getInventory().contains("Monkfish")) {
			MethodProvider.log("14");
			if (ctx.getInventory().isFull()) {
				ctx.getBank().depositAll("Pure essence");
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !ctx.getInventory().contains("Pure essence");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			} else if (ctx.getBank().withdraw("Monkfish", (int) Math.ceil(((double) ctx.getSkills().getRealLevel(Skill.HITPOINTS)) - ctx.getSkills().getBoostedLevels(Skill.HITPOINTS)) / 15)) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getInventory().contains("Monkfish");
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			} else if (!ctx.getBank().contains("Monkfish")) {
				ctx.getBank().close();
				MethodProvider.sleep(1200, 1800);
				ctx.getTabs().logout();
			}
		} else if (ctx.getBank().isOpen() && ctx.getInventory().getEmptySlots() > 0) {
			MethodProvider.log("16");
			if (ctx.getBank().withdrawAll("Pure essence")) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getInventory().isFull();
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			} else if (!ctx.getBank().contains("Pure essence")) {
				ctx.getBank().close();
				MethodProvider.sleep(1200, 1800);
				ctx.getTabs().logout();
			}
		}else if (ctx.getBank().isOpen() && ctx.getInventory().getEmptySlots() == 0) {
			MethodProvider.log("17");
			if (ctx.getBank().close()) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return !ctx.getBank().isOpen();
					}
				}, Calculations.random(MINIMUM_SLEEP, MAXIMUM_SLEEP));
			}
		}
		return Calculations.random(25, 125);
	}

	@Override
	public String getName() {
		return "bank";
	}
}
