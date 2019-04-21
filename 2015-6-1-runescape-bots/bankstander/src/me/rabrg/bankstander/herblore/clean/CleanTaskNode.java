package me.rabrg.bankstander.herblore.clean;

import me.rabrg.bankstander.herblore.Herb;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;

public class CleanTaskNode extends TaskNode {

    private final Herb herb;

    public CleanTaskNode(final Herb herb) {
        this.herb = herb;
    }
    @Override
    public boolean accept() {
        return !getBank().isOpen() && getInventory().contains(herb.getGrimyId());
    }

    @Override
    public int execute() {
        getInventory().interact(herb.getGrimyId(), "Clean");
        return Calculations.random(0, 50);
    }
}
