package me.rabrg.aiocombat.nodes;

import me.rabrg.aiocombat.AIOCombat;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.GroundItem;

public final class DropNode extends TaskNode {

    private final AIOCombat script;

    private GroundItem item;

    public DropNode(final AIOCombat script) {
        this.script = script;
    }

    @Override
    public boolean accept() {
        return !getInventory().isFull() && (item = getGroundItems().closest(new Filter<GroundItem>() {
            @Override
            public boolean match(GroundItem groundItem) {
                return groundItem != null && groundItem.getName() != null && script.getDrops().contains(groundItem.getName())
                        && groundItem.distance() < 20;
            }
        })) != null;
    }

    @Override
    public int execute() {
        if (item.interact("Take")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !item.exists();
                }
            }, Calculations.random(5000, 7000));
        }
        return Calculations.random(0, 300);
    }
}
