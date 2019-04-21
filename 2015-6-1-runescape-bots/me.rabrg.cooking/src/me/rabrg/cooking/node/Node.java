package me.rabrg.cooking.node;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.wrappers.items.Item;

public abstract class Node {

	static final Filter<Item> RAW_ITEM_FILTER = new Filter<Item>() {
		@Override
		public boolean match(final Item item) {
			return item != null && item.getName() != null && item.getName().startsWith("Raw");
		}
	};

	final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();

	Item getRawItem() {
		return ctx.getInventory().get(RAW_ITEM_FILTER);
	}
}
