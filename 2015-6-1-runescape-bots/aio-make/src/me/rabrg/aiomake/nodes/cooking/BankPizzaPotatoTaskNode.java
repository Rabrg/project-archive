package me.rabrg.aiomake.nodes.cooking;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;

import static me.rabrg.aiomake.AIOMake.*;

public class BankPizzaPotatoTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return getBank().isOpen() || !getInventory().contains(buy[0].name)
                || !getInventory().contains(buy[1].name);
    }

    @Override
    public int execute() {
        if (!getBank().isOpen() && getBank().openClosest()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getBank().isOpen();
                }
            }, 4000);
        } else if (getBank().isOpen() && getInventory().contains(sell.name) && getBank().depositAllItems()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().isEmpty();
                }
            }, 1200);
        } else if (getBank().isOpen() && !getBank().contains(buy[1].name)
                && !getInventory().contains(buy[1].name)) {
            grandExchange = true;
        } else if (getBank().isOpen() && !getBank().contains(buy[0].name)
                && !getInventory().contains(buy[0].name)) {
            grandExchange = true;
        } else if (getBank().isOpen() && !getInventory().contains(buy[0].name) && getBank().contains(buy[0].name)
                && getBank().withdraw(buy[0].name, 14)) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().contains(buy[0].name);
                }
            }, 1200);
        } else if (getBank().isOpen() && !getInventory().contains(buy[1].name)
                && getBank().contains(buy[1].name)
                && getBank().withdraw(buy[1].name, getInventory().emptySlotCount())) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().contains(buy[1].name);
                }
            }, 1200);
        } else if (getBank().isOpen() && getInventory().contains(buy[1].name)
                && getInventory().contains(buy[0].name) && getBank().close()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getBank().isOpen();
                }
            }, 1200);
        }
        return Calculations.random(100, 300);
    }
}
