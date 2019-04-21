package me.rabrg.herblore.nodes;

import me.rabrg.herblore.Herblore;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.List;

public final class CreatePotionNode extends TaskNode {

    private final Herblore herblore;

    public CreatePotionNode(final Herblore herblore) {
        this.herblore = herblore;
    }

    @Override
    public boolean accept() {
        return !getBank().isOpen() && getInventory().contains(herblore.getUnfinishedName())
                && getInventory().contains(herblore.getSecondaryName());
    }

    @Override
    public int execute() {
        final WidgetChild child = getWidgetChild();
        if (child != null && child.interact("Make All")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getDialogues().canContinue() || !getInventory().contains(herblore.getUnfinishedName())
                            || !getInventory().contains(herblore.getSecondaryName());
                }
            }, Calculations.random(18000, 24000));
        } else if (getInventory().interact(herblore.getUnfinishedName(), "Use")
                && getInventory().interact(herblore.getSecondaryName(), "Use")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getWidgetChild() != null;
                }
            }, Calculations.random(1200, 2400));
        }
        return Calculations.random(0, 300);
    }

    private WidgetChild getWidgetChild() {
        final List<WidgetChild> children = getWidgets().getWidgetChildrenContainingText(herblore.getFinishedName());
        if (children != null && children.size() > 0)
            return children.get(0);
        return null;
    }
}
