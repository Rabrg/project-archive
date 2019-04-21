package me.rabrg.enchantingchamber.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

public final class EnchantDragonstoneNode extends Node {

	private Item dragonstone;

	public EnchantDragonstoneNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return INSIDE_AREA.contains(ctx.getLocalPlayer()) && (dragonstone = ctx.getInventory().get("Dragonstone")) != null;
	}

	@Override
	public int execute() {
		if (!ctx.getMagic().isSpellSelected()) {
			if (!ctx.getTabs().isOpen(Tab.MAGIC)) {
				if (ctx.getTabs().open(Tab.MAGIC)) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return ctx.getTabs().isOpen(Tab.MAGIC);
						}
					}, Calculations.random(1200, 2400));
				}
			} else {
				if (ctx.getMagic().castSpell(Normal.LEVEL_4_ENCHANT)) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return ctx.getMagic().isSpellSelected();
						}
					}, Calculations.random(1200, 2400));
				}
			}
		} else {
			if (dragonstone.interact()) {
				enchants++;
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return ctx.getTabs().isOpen(Tab.MAGIC);
					}
				}, Calculations.random(1200, 2400));
			}
		}
		return Calculations.random(25, 75);
	}

	@Override
	public String getName() {
		return "Enchanting dragonstone";
	}

}
