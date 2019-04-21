package me.rabrg.nature.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;

import me.rabrg.nature.RabrgNature;

public final class ZamorakMageNode extends Node {

	private static final Tile ZAMORAK_MAGE_TILE = new Tile(3103, 3558);

	private NPC zamorakMage;

	public ZamorakMageNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return WILDERNESS_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		if (ctx.getPlayers().all(new Filter<Player>() {
			@Override
			public boolean match(final Player player) {
				return player != null && player.getName() != null && WILDERNESS_AREA.contains(player) && !player.getName().equals(ctx.getLocalPlayer().getName()) && player.getLevel() >= ctx.getLocalPlayer().getLevel() - 5 && player.getLevel() <= ctx.getLocalPlayer().getLevel() + 5;
			}
		}).size() > 0) {
			if (ctx.getWorldHopper().quickHop(ctx.getWorlds().getRandomWorld(new Filter<World>() {
				@Override
				public boolean match(final World world) {
					return world.isMembers() && !world.isPVP();
				}
			}).getID())) {
				RabrgNature.hops++;
			}
		} else if ((zamorakMage = ctx.getNpcs().closest("Mage of Zamorak")) == null || zamorakMage.distance() > 7) {
			if (zamorakMage == null) {
				if (ctx.getWalking().walk(ZAMORAK_MAGE_TILE.getRandomizedTile(9))) {
					return Calculations.random(500, 1750);
				}
			} else {
				if (ctx.getWalking().walk(zamorakMage)) {
					return Calculations.random(500, 1750);
				}
			}
			return Calculations.random(600, 1800);
		} else if (zamorakMage.interact("Teleport")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !WILDERNESS_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(6400, 7200));
		} else if (!zamorakMage.isOnScreen()) {
			ctx.getCamera().rotateToEntity(zamorakMage);
		}
		return Calculations.random(25, 125);
	}

	@Override
	public String getName() {
		return "zamorak mage";
	}

}
