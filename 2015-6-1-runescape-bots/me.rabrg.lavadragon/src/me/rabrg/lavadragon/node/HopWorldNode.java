package me.rabrg.lavadragon.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.world.World;

public final class HopWorldNode extends Node {

	public HopWorldNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getCombat().isInWild() && ctx.getPlayers().all().size() > 1;
	}

	@Override
	public int execute() {
		if (ctx.getWorldHopper().quickHop(ctx.getWorlds().getRandomWorld(new Filter<World>() {
			@Override
			public boolean match(final World world) {
				return world.isMembers() && !world.isPVP();
			}
		}).getID())) {
			return 900;
		}
		return Calculations.random(300);
	}

	@Override
	public String getName() {
		return "Hopping worlds";
	}

}
