package me.rabrg.karambwan.node;

import me.rabrg.karambwan.Karambwan;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

import static me.rabrg.karambwan.node.JiminuaTaskNode.DRINK_DELAY;
import static me.rabrg.karambwan.node.JiminuaTaskNode.lastDrink;

public class DropTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return getInventory().contains("Vial") || System.currentTimeMillis() - lastDrink < DRINK_DELAY
                && getInventory().contains(Karambwan.ANTIPOISON_FILTER);
    }

    @Override
    public int execute() {
        Item vial = getInventory().get("Vial");
        if (vial == null)
            vial = getInventory().get(Karambwan.ANTIPOISON_FILTER);
        if (vial != null && vial.interact("drop"))
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getInventory().contains("Vial") && !getInventory().contains(Karambwan.ANTIPOISON_FILTER);
                }
            }, Calculations.random(1200, 2400));
        return Calculations.random(0, 1200);
    }
}
