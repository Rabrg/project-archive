package me.rabrg.bankstander.herblore.clean;

import me.rabrg.bankstander.herblore.Herb;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;

public class BankCleanTaskNode extends TaskNode {

    private final Herb herb;

    public BankCleanTaskNode(final Herb herb) {
        this.herb = herb;
    }

    @Override
    public boolean accept() {
        return getBank().isOpen() || !getInventory().contains(herb.getGrimyId());
    }

    @Override
    public int execute() {
        if (!getInventory().contains(herb.getGrimyId()) && !getBank().isOpen() && getBank().open())
            sleepUntil(() -> getBank().isOpen(), Calculations.random(3600, 6000));
        else if (!getInventory().isEmpty() && !getInventory().contains(herb.getCleanId()) && getBank().isOpen()
                && getBank().depositAllItems())
            sleepUntil(() -> !getInventory().contains(herb.getCleanId()), 1200);
        else if (getInventory().isEmpty() && !getInventory().contains(herb.getCleanId()) && getBank().isOpen()
                && getBank().withdrawAll(herb.getGrimyId()))
            sleepUntil(() -> !getInventory().contains(herb.getCleanId()), 1200);
        else if (getInventory().contains(herb.getGrimyId()) && getBank().isOpen() && getBank().close())
            sleepUntil(() -> !getBank().isOpen(), 1200);
        return Calculations.random(0, 900);
    }
}
