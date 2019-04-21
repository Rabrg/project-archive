package me.rabrg.blackknight.node;

import org.dreambot.api.wrappers.interactive.Character;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;

public final class TargetBlackKnightNode extends Node {

	private NPC target;

	public TargetBlackKnightNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return !ctx.getLocalPlayer().isInCombat() && (target = ctx.getNpcs().closest(new Filter<NPC>() {
			@Override
			public boolean match(final NPC n) {
				if (n == null || n.getActions() == null || n.getActions().length <= 0)
					return false;
				if (n.getName() == null || (!n.getName().equals("Black Knight")))
					return false;
				if (!ctx.getMap().canReach(n))
					return false;
				if (n.isInCombat()) {
					final Character c = n.getInteractingCharacter();
					if (c == null)
						return false;
					if (c.getName() == null)
						return false;
					if (c.getName().equals(ctx.getLocalPlayer().getName()))
						return true;
					return false;
				}
				return true;
			}
		})) != null && ctx.getSkills().getBoostedLevels(Skill.PRAYER) >= nextRecharge;
	}

	@Override
	public int execute() {
		if (target.interact("Attack")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getLocalPlayer().isInCombat();
				}
				
			}, Calculations.random(600, 3600));
			if (Calculations.random(0, 1) >= .69) {
				MethodProvider.sleep(5, 1250);
				ctx.getMouse().moveMouseOutsideScreen();
			}
		}
		return Calculations.random(75, 1200);
	}

	@Override
	public String getName() {
		return "Targeting black knight";
	}

}
