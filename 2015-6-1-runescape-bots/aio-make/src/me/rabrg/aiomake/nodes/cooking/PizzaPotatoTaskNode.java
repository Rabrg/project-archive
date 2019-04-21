package me.rabrg.aiomake.nodes.cooking;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.List;

import static me.rabrg.aiomake.AIOMake.*;

public class PizzaPotatoTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return !getBank().isOpen() && getInventory().containsAll(buy[1].name, buy[0].name);
    }

    @Override
    public int execute() {
        if (getInventory().interact(buy[0].name, "Use") && getInventory().interact(buy[1].name, "Use")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    final List<WidgetChild> children = getWidgets().getWidgetChildrenContainingText(sell.name);
                    if (children.size() > 0)
                        return children.get(0).interact("Make All");
                    return false;
                }
            }, 1200);
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getInventory().contains(buy[1].name) ||
                            !getInventory().contains(buy[0].name) || getDialogues().canContinue();
                }
            }, 30000);
        }
        return Calculations.random(100, 300);
    }
}
