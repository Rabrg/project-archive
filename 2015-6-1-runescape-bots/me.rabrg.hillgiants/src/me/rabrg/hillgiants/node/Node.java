package me.rabrg.hillgiants.node;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.items.Item;

public abstract class Node {

	static final Tile ATTACK_TILE = new Tile(2442, 3213);
	static final Tile BANK_TILE = new Tile(2443, 3083);

	static final Filter<Item> FOOD_FILTER = new Filter<Item>() {
		@Override
		public boolean match(final Item item) {
			return item != null && item.getName().equals("Salmon");
		}
	};

	final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String toString();
}
