package me.rabrg.tea;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;

@ScriptManifest(author = "Rabrg", category = Category.THIEVING, name = "Tea", version = 0.1)
public final class Tea extends AbstractScript {

	private GameObject teaStall;

	@Override
	public int onLoop() {
		for (final Player player : getPlayers().all()) {
			if (player.getName() != null && !player.getName().equals(getLocalPlayer().getName()) && player.distance() < 12) {
				getWorldHopper().quickHop(getWorlds().getRandomWorld(new Filter<World>() {
					@Override
					public boolean match(final World world) {
						return world.isMembers() && !world.isPVP();
					}
				}).getID());
				return 3600;
			}
		}
		if (getInventory().isFull()) {
			getInventory().dropAll();
		} else {
			if ((teaStall = getGameObjects().closest("Tea stall")) != null) {
				if (teaStall.interact("Steal-from")) {
					return Calculations.random(1024, 1536);
				}
			}
		}
		return Calculations.random(0, 512);
	}

}
