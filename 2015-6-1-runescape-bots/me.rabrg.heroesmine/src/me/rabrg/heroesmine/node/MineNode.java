package me.rabrg.heroesmine.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

public final class MineNode extends Node {

	private static final Tile ROCKS_TILE = new Tile(2938, 9884);

	private GameObject rocks;

	public MineNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return MINE_AREA.contains(ctx.getLocalPlayer()) && !ctx.getInventory().isFull();
	}

	@Override
	public int execute() {
		MethodProvider.log("0");
		if (ctx.getInventory().contains(UNCUT_GEM_FILTER) && ctx.getInventory().get(UNCUT_GEM_FILTER).interact("Drop")) {
			MethodProvider.log("1");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(300);
					return !ctx.getInventory().contains(UNCUT_GEM_FILTER);
				}
			}, Calculations.random(2400, 3000));
			return Calculations.random(300);
		} else if ((rocks = ctx.getGameObjects().closest(14175, 14180)) != null) {
			MethodProvider.log("2");
			if (!rocks.interact("Mine")) {
				ctx.getWalking().walk(rocks);
				ctx.getCamera().rotateToEntity(rocks);
			} else {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						MethodProvider.sleep(300);
						return ctx.getLocalPlayer().getAnimation() > 0;
					}
				}, Calculations.random(2400, 4800));
				if (ctx.getLocalPlayer().getAnimation() > 0) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							MethodProvider.sleep(300);
							rocks = ctx.getGameObjects().closest(10798, 10796);
							return (rocks != null && rocks.distance() == 1) || ctx.getLocalPlayer().getAnimation() < 1;
						}
					}, Calculations.random(16000, 24000));
				}
			}
			return Calculations.random(300);
		} else if (ROCKS_TILE.distance() >= 8 && ctx.getWalking().walk(ROCKS_TILE)) {
			MethodProvider.log("3");
			return Calculations.random(600, 1800);
		} else if (rocks == null && ctx.getWorldHopper().quickHop(ctx.getWorlds().getRandomWorld(new Filter<World>() {
					@Override
					public boolean match(final World world) {
						return world.isMembers() && !world.isPVP() && world.getID() != 353 && world.getID() != 361 && world.getID() != 366 && world.getID() != 373;
					}
				}).getID())) {
			MethodProvider.log("4");
			return 1200;
		}
		return Calculations.random(300);
	}

	@Override
	public String getName() {
		return "Mining";
	}

	private static final Filter<Item> UNCUT_GEM_FILTER = new Filter<Item>() {
		@Override
		public boolean match(final Item item) {
			return item != null && item.getName().toLowerCase().contains("uncut");
		}
	};
}
