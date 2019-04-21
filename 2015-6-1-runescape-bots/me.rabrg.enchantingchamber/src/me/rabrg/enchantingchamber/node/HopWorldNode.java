package me.rabrg.enchantingchamber.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.utilities.impl.Condition;

public final class HopWorldNode extends Node {

	private World world;

	public HopWorldNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return OUTSIDE_AREA.contains(ctx.getLocalPlayer()) && needHop && (world = ctx.getWorlds().getRandomWorld(new Filter<World>() {
			@Override
			public boolean match(final World world) {
				return !world.isF2P() && !world.isPVP();
			}
		})) != null;
	}

	@Override
	public int execute() {
		MethodProvider.log("hopping");
		if (ctx.getWorldHopper().hopWorld(world)) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return world.getID() == ctx.getClient().getCurrentWorld();
				}
			}, Calculations.random(3800, 4400));
			needHop = false;
		}
		return Calculations.random(1800, 2400);
	}

	@Override
	public String getName() {
		return "Hopping worlds";
	}

}

