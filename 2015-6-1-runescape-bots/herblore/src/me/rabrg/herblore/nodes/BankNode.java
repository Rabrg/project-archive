package me.rabrg.herblore.nodes;

import me.rabrg.herblore.Herblore;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;

public class BankNode extends TaskNode {

    private final Herblore herblore;

    public BankNode(final Herblore herblore) {
        this.herblore = herblore;
    }

    @Override
    public boolean accept() {
        return getBank().isOpen() || !getInventory().contains(herblore.getUnfinishedName())
                || !getInventory().contains(herblore.getSecondaryName());
    }

    @Override
    public int execute() {
        // TODO: stop if no have
        if (getInventory().contains(herblore.getUnfinishedName())
                && getInventory().contains(herblore.getSecondaryName()) && getBank().close()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getBank().isOpen();
                }
            }, Calculations.random(1200, 2400));
        } else if (!getBank().isOpen() && getBank().openClosest()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getBank().isOpen();
                }
            }, Calculations.random(2400, 4800));
        } else if (getBank().isOpen() && getInventory().contains(herblore.getFinishedName())
                && getBank().depositAllItems()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().isEmpty();
                }
            }, Calculations.random(1200, 2400));
        } else if (getBank().isOpen() && !getInventory().contains(herblore.getUnfinishedName())
                && getBank().withdraw(herblore.getUnfinishedName(), 14)) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().contains(herblore.getUnfinishedName());
                }
            }, Calculations.random(1200, 2400));
        } else if (getBank().isOpen() && !getInventory().contains(herblore.getSecondaryName())
                && getBank().withdraw(herblore.getSecondaryName(), 14)) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().contains(herblore.getSecondaryName());
                }
            }, Calculations.random(1200, 2400));
        }
        return Calculations.random(0, 300);
    }
}
