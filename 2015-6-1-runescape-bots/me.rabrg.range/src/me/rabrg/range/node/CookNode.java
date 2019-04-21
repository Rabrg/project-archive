package me.rabrg.range.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;

public final class CookNode extends Node {

	private Item rawFood;
	private GameObject range;
	private Widget widget;
	private WidgetChild widgetChild;

	public CookNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (ctx.getDialogues().inDialogue() || ctx.getLocalPlayer().getAnimation() != 896) && (rawFood = getRawItem()) != null && (range = ctx.getGameObjects().closest("Range")) != null;
	}

	@Override
	public int execute() {
		if ((widget = ctx.getWidgets().getWidget(307)) != null && widget.isVisible() && (widgetChild = widget.getChild(3)) != null && widgetChild.isVisible()) {
			widgetChild.interact("Cook All");
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getLocalPlayer().getAnimation() == 896;
				}
			}, Calculations.random(800, 1200));
		} else {
			if (Calculations.random(0, 1) > .66) {
				ctx.getCamera().rotateToEntity(range);
			}
			if (rawFood.useOn(range)) {
				MethodProvider.sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return (widget = ctx.getWidgets().getWidget(307)) != null && widget.isVisible() && (widgetChild = widget.getChild(3)) != null && widgetChild.isVisible();
					}
				}, Calculations.random(8400, 9000));
			}
		}
		return Calculations.random(600, 1800);
	}

	@Override
	public String getName() {
		return "Interacting with range";
	}

}
