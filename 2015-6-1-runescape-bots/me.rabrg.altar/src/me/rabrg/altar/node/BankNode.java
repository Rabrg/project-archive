package me.rabrg.altar.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

public final class BankNode extends Node {

	private static final Tile BANK_TILE = new Tile(2613, 3094);

	private static final Filter<Item> STAMINA_FILTER = new Filter<Item>() {
		@Override
		public boolean match(final Item item) {
			return item != null && item.getName().toLowerCase().contains("stamina");
		}
	};

	private final String bones;
	private final boolean staminaPotions;

	private GameObject bankBooth;
	private long lastDrink = System.currentTimeMillis();

	public BankNode(final String bones, final boolean staminaPotions, final MethodContext ctx) {
		super(ctx);
		this.bones = bones;
		this.staminaPotions = staminaPotions;
	}

	@Override
	public boolean validate() {
		return !ctx.getInventory().contains(bones) && YANILLE_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		if (staminaPotions && ctx.getBank().isOpen() && ctx.getWalking().getRunEnergy() < 60 && (System.currentTimeMillis() - lastDrink) < 60000 && !ctx.getInventory().contains(STAMINA_FILTER) && ctx.getBank().withdraw(STAMINA_FILTER)) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getInventory().contains(STAMINA_FILTER);
				}
			}, Calculations.random(1200, 1800));
		} else if (staminaPotions && ctx.getBank().isOpen() && (System.currentTimeMillis() - lastDrink) < 60000 && ctx.getInventory().contains(STAMINA_FILTER) && ctx.getBank().close()) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !ctx.getBank().isOpen();
				}
			}, Calculations.random(1200, 1800));
		} else if (staminaPotions && !ctx.getBank().isOpen() && (System.currentTimeMillis() - lastDrink) < 60000 && ctx.getInventory().contains(STAMINA_FILTER) && ctx.getInventory().get(STAMINA_FILTER).interact("Drink")) {
			lastDrink = System.currentTimeMillis();
			return Calculations.random(600, 1200);
		} else if (!ctx.getBank().isOpen() && (bankBooth = ctx.getGameObjects().closest("Bank booth")) != null && bankBooth.distance() <= 18 && bankBooth.interact("Bank")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getBank().isOpen();
				}
			}, Calculations.random(6400, 7200));
		} else if (!ctx.getBank().isOpen() && (bankBooth == null || bankBooth.distance() > 18) && ctx.getWalking().walk(BANK_TILE)) {
			return Calculations.random(600, 1800);
		} else if (ctx.getBank().isOpen() && !ctx.getInventory().isEmpty() && ctx.getBank().depositAllItems()) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getInventory().isEmpty();
				}
			}, Calculations.random(1200, 1800));
		} else if (ctx.getBank().isOpen() && ctx.getInventory().isEmpty() && ctx.getBank().withdrawAll(bones)) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getInventory().contains(bones);
				}
			}, Calculations.random(1200, 1800));
		}
		return Calculations.random(300);
	}

	@Override
	public String getName() {
		return "Banking";
	}

}
