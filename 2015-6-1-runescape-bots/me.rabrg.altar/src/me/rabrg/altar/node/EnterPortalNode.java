package me.rabrg.altar.node;

import java.awt.event.KeyEvent;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class EnterPortalNode extends Node {

	private static final Tile PORTAL_TILE = new Tile(2545, 3096);

	private final String bones;

	private GameObject portal;

	public EnterPortalNode(final String bones, final MethodContext ctx) {
		super(ctx);
		this.bones = bones;
	}

	@Override
	public boolean validate() {
		return YANILLE_AREA.contains(ctx.getLocalPlayer()) && ctx.getInventory().contains(bones);
	}

	@Override
	public int execute() {
		if ((portal = ctx.getGameObjects().closest(15482)) != null && portal.distance() <= 18 && !ctx.getDialogues().inDialogue() && portal.interact("Enter")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return ctx.getDialogues().inDialogue();
				}
			}, Calculations.random(5200, 6400));
		} else if (portal == null || portal.distance() > 18 && !ctx.getDialogues().inDialogue() && ctx.getWalking().walk(PORTAL_TILE)) {
			return Calculations.random(600, 1800);
		} else if (portal != null && ctx.getDialogues().inDialogue() && (ctx.getDialogues().getOptions() != null && ctx.getDialogues().getOptions().length > 1) && ctx.getDialogues().chooseOption(3)) {
			return Calculations.random(1200, 1600);
		} else if (portal != null && ctx.getDialogues().inDialogue() && (ctx.getDialogues().getOptions() == null || ctx.getDialogues().getOptions().length < 1)) {
			ctx.getKeyboard().typeSpecialKey(KeyEvent.VK_ENTER);
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !YANILLE_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(5200, 6400));
		}
		return Calculations.random(300);
	}

	@Override
	public String getName() {
		return "Entering portal";
	}

}
