package me.rabrg.hillgiants.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.wrappers.interactive.Player;

public final class HopWorldNode extends Node {

	public HopWorldNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getLocalPlayer().isInCombat() && ctx.getNpcs().closest("Hill Giant") != null && ctx.getPlayers().all(new Filter<Player>() {
			@Override
			public boolean match(final Player player) {
				return !player.getName().equals(ctx.getLocalPlayer().getName()) && player.distance() < 12;
			}
		}).size() > 0;
	}

	@Override
	public int execute() {
		if (ctx.getWorldHopper().quickHop(ctx.getWorlds().getRandomWorld(new Filter<World>() {
			@Override
			public boolean match(final World world) {
				return world.isMembers() && !world.isPVP() && world.getID() != 353 && world.getID() != 361 && world.getID() != 366 && world.getID() != 373;
			}
		}).getID())) {
			return Calculations.random(1200, 1800);
		}
		return Calculations.random(0, 300);
	}

	@Override
	public String toString() {
		return "Hopping worlds";
	}

}
