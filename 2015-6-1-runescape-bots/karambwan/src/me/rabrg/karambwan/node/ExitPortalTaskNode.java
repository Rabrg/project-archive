package me.rabrg.karambwan.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public class ExitPortalTaskNode extends TaskNode {

    private GameObject portal;

    @Override
    public boolean accept() {
        return isPortal();
    }

    @Override
    public int execute() {
        if (portal.interact("Enter")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !isPortal();
                }
            }, Calculations.random(1200, 2400));
        }
        return Calculations.random(150, 1800);
    }

    private boolean isPortal() {
        return (portal = getGameObjects().closest("Portal")) != null && portal.hasAction("Lock");
    }
}
