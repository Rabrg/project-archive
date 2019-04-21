package me.rabrg.venenatis;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.wrappers.interactive.Player;

import me.rabrg.venenatis.node.EscapeNode;
import me.rabrg.venenatis.node.Node;

public final class Venenatis extends AbstractScript {

	private final Node[] nodes = { new EscapeNode(this) };

	public final Filter<Player> ATTACKABLE_PLAYER_FILTER = new Filter<Player>() {
		@Override
		public boolean match(final Player player) {
			return !player.getName().equals(getLocalPlayer().getName()) && player.getLevel() >= getLocalPlayer().getLevel() - 30 && player.getLevel() <= getLocalPlayer().getLevel() + 30;
		}
	};

	public long lastCombat = System.currentTimeMillis() - 10000;

	@Override
	public int onLoop() {
		if (getLocalPlayer().isInCombat()) {
			lastCombat = System.currentTimeMillis();
		}
		for (final Node node : nodes) {
			if (node.validate()) {
				node.execute();
			}
		}
		return Calculations.random(5, 25);
	}

}
