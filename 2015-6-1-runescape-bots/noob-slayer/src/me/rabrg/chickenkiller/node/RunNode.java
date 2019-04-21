package me.rabrg.chickenkiller.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;

public class RunNode extends TaskNode {

    private int random = Calculations.random(10, 30);

    @Override
    public boolean accept() {
        return getWalking().getRunEnergy() > random && !getWalking().isRunEnabled();
    }

    @Override
    public int execute() {
        if (getWalking().toggleRun())
            random = Calculations.random(10, 30);
        return Calculations.random(1200, 2400);
    }
}
