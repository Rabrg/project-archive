package me.rabrg.shrimp.node;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.wrappers.interactive.NPC;

public abstract class Node {

	static final Filter<NPC> FISHING_SPOT_FILTER = new Filter<NPC>() {
		@Override
		public boolean match(final NPC npc) {
			return npc != null && npc.getName() != null && npc.getName().equals("Fishing spot") && npc.hasAction("Net");
		}
	};

	final MethodContext ctx;

	public Node(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();
}
