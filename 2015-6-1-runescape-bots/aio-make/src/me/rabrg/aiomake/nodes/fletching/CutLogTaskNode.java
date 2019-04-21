package me.rabrg.aiomake.nodes.fletching;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.List;

import static me.rabrg.aiomake.AIOMake.*;

public class CutLogTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return !getBank().isOpen() && getInventory().containsAll(buy[0].name, "Knife");
    }

    @Override
    public int execute() {
        if (getInventory().interact("Knife", "Use") && getInventory().interact(buy[0].name, "Use")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    final List<WidgetChild> children = getWidgets().getWidgetChildrenContainingText(bowWidget);
                    if (children.size() > 0)
                        return children.get(0).interact("Make X");
                    return false;
                }
            }, 1200);
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getDialogues().inDialogue();
                }
            }, 1200);
            sleep(300, 500);
            getKeyboard().type(getInventory().count(buy[0].name), true);
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getLocalPlayer().getAnimation() > 0;
                }
            }, 1600);
            if (getLocalPlayer().getAnimation() > 0) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return !getInventory().contains(buy[0].name) || getDialogues().canContinue();
                    }
                }, 60000);
            }
        }
        return Calculations.random(100, 300);
    }
}
