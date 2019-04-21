package me.rabrg.willowlog.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class Cutter extends Node {

	private GameObject lastTree;
	private GameObject tree;

	public Cutter(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getInventory().isFull() && (tree = ctx.getGameObjects().closest(new Filter<GameObject>() {
			@Override
			public boolean match(final GameObject object) {
				return object != null && object.getName() != null && object.getName().equals("Willow") && object.hasAction("Chop down");
			}
		})) != null && (ctx.getLocalPlayer().getAnimation() < 1 || !lastTree.getTile().equals(tree.getTile()));
	}

	@Override
	public int execute() {
		ctx.getWalking().setRunThreshold(Calculations.random(30, 60));
		if (tree.interact("Chop down")) {
			lastTree= tree;
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getLocalPlayer().getAnimation() > 1 || (tree == null || !tree.exists());
				}
			}, Calculations.random(5000, 7500));
		}
		return Calculations.random(0, 255);
	}

	@Override
	public String getName() {
		return "cut";
	}

}
