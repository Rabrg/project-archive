package me.rabrg.normallog.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class Cutter extends Node {

	private GameObject tree;

	public Cutter(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getInventory().isFull() && ctx.getLocalPlayer().getAnimation() < 1 && (tree = ctx.getGameObjects().closest(new Filter<GameObject>() {
			@Override
			public boolean match(final GameObject object) {
				return object != null && object.getName() != null && object.getName().equals("Tree") && object.hasAction("Chop down");
			}
		})) != null;
	}

	@Override
	public int execute() {
		ctx.getWalking().setRunThreshold(Calculations.random(30, 60));
		if (tree.distance() > 10) {
			ctx.getWalking().walk(tree);
		} else if (tree.interact("Chop down")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getLocalPlayer().getAnimation() > 1;
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
