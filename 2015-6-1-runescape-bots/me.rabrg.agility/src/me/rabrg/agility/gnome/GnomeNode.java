package me.rabrg.agility.gnome;

import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Area;

import me.rabrg.agility.Node;

public abstract class GnomeNode extends Node {

	static final Area LOG_BALANCE_AREA = new Area(2469, 3436, 2490, 3445, 0);
	static final Area OBSTACLE_NET_UP_AREA = new Area(2469, 3426, 2476, 3430, 0);
	static final Area TREE_BRANCH_UP_AREA = new Area(2471, 3422, 2476, 3424, 1);
	static final Area BALANCING_ROPE_AREA = new Area(2472, 3418, 2477, 3421, 2);
	static final Area TREE_BRANCH_DOWN_AREA = new Area(2483, 3417, 2488, 3421, 2);
	static final Area OBSTACLE_NET_ACROSS_AREA = new Area(2482, 3417, 2490, 3425, 0);
	static final Area OBSTACLE_PIPE_AREA = new Area(2482, 3427, 2489, 3431, 0);

	public GnomeNode(final MethodContext ctx) {
		super(ctx);
	}

	public abstract boolean validate();

	public abstract int execute();

	public abstract String getName();

}
