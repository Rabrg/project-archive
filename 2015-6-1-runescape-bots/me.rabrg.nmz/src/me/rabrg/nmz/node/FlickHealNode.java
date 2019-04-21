package me.rabrg.nmz.node;

import java.awt.Point;
import java.awt.Rectangle;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.tabs.Tab;

public final class FlickHealNode extends Node {

	private static final Rectangle HEAL_PRAYER = new Rectangle(702, 254, 26, 30);

	private long lastFlick = System.currentTimeMillis();

	private long flickDelay = Calculations.random(5000, 30000);

	public FlickHealNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (lastFlick + flickDelay) <= System.currentTimeMillis();
	}

	@Override
	public int execute() {
		if (!ctx.getTabs().isOpen(Tab.PRAYER)) {
			ctx.getTabs().open(Tab.PRAYER);
			MethodProvider.sleep(450, 600);
		}
		if (Calculations.random(1, 10) > 6) {
			ctx.getMouse().move(new Point((int) Calculations.random(HEAL_PRAYER.getMinX(), HEAL_PRAYER.getMaxX()), (int) Calculations.random(HEAL_PRAYER.getMinY(), HEAL_PRAYER.getMaxY())));
		}
		if (ctx.getPrayer().flick(Prayer.RAPID_HEAL, Calculations.random(450, 600))) {
			lastFlick = System.currentTimeMillis();
			flickDelay = Calculations.random(5000, 30000);
		}
		if (Calculations.random(1, 10) > 8) {
			MethodProvider.sleep(125, 375);
			ctx.getMouse().moveMouseOutsideScreen();
		}
		return Calculations.random(450, 600);
	}

	@Override
	public String getName() {
		return "Flicking healing prayer";
	}

}
