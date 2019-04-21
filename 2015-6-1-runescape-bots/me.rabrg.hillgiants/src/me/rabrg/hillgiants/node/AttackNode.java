package me.rabrg.hillgiants.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;

public final class AttackNode extends Node {

	private NPC target;

	public AttackNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getInventory().contains(FOOD_FILTER) && !ctx.getLocalPlayer().isInCombat() && (target = ctx.getNpcs().closest(new Filter<NPC>() {
			@Override
			public boolean match(final NPC npc) {
				return (!npc.isInCombat() || (npc.getInteractingCharacter() == ctx.getLocalPlayer())) && npc.getName().equals("Hill Giant");
			}
		})) != null;
	}

	@Override
	public int execute() {
		if (target.interact("Attack")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					MethodProvider.sleep(0, 300);
					return ctx.getLocalPlayer().isInCombat();
				}
			}, Calculations.random(600, 3200));
		}
		return Calculations.random(0, 300);
	}

	@Override
	public String toString() {
		return "Attacking";
	}

}
