package me.rabrg.karambwan;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.randoms.RandomSolver;
import org.dreambot.api.utilities.impl.Condition;

import java.awt.*;

public final class WelcomeScreenRandomSolver extends RandomSolver {

    private static final Rectangle BUTTON = new Rectangle(273, 296, 497, 381);

    private Widget widget;

    public WelcomeScreenRandomSolver() {
        super(null);
    }

    @Override
    public boolean shouldExecute() {
        return (widget = getWidgets().getWidget(388)) != null && widget.isVisible();
    }

    @Override
    public int onLoop() {
        if (getMouse().click(BUTTON))
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return (widget = getWidgets().getWidget(388)) == null || !widget.isVisible();
                }
            }, Calculations.random(1800, 2400));
        return Calculations.random(0, 150);
    }
}
