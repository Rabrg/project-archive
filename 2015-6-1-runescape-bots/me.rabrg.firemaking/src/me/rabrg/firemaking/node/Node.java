package me.rabrg.firemaking.node;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.wrappers.items.Item;

public abstract class Node {

	final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();

	static final Filter<Item> LOGS_FILTER = new Filter<Item>() {
		@Override
		public boolean match(final Item item) {
			return item != null && item.getName() != null && item.getName().toLowerCase().contains("logs");
		}
	};
}
