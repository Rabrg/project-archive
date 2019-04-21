package me.rabrg.aiocombat.nodes;

import me.rabrg.aiocombat.AIOCombat;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;

public final class BankNode extends TaskNode {

    private final AIOCombat script;

    public BankNode(AIOCombat script) {
        this.script = script;
    }

    @Override
    public boolean accept() {
        return !getInventory().contains(script.getFoodName());
    }

    @Override
    public int execute() {
        if (!getBank().isOpen()) {
            getBank().openClosest();
            sleep(300, 1800);
        } else if (!getInventory().isEmpty() && getBank().depositAllItems()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().isEmpty();
                }
            }, Calculations.random(1200, 2400));
        } else if (getInventory().isEmpty() && getBank().withdraw(script.getFoodName(), script.getFoodAmount())) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().contains(script.getFoodName());
                }
            }, Calculations.random(1200, 2400));
        }
        return Calculations.random(0, 300);
    }
}
