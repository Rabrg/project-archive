package me.matt.nature.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

public final class EscapeNode extends Node {

	private Item glory;

	public EscapeNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) < 21 && (glory = ctx.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot())) != null;
	}

	@Override
	public int execute() {
		if (!ctx.getTabs().isOpen(Tab.EQUIPMENT)) {
			if (ctx.getTabs().open(Tab.EQUIPMENT)) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getTabs().isOpen(Tab.EQUIPMENT);
					}
				}, Calculations.random(750, 900));
			}
		} else if (glory.interact("Edgeville")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !ALTAR_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(6400, 7200));
		}
		return Calculations.random(0, 255);
	}

	@Override
	public String getName() {
		return "escape";
	}

}
