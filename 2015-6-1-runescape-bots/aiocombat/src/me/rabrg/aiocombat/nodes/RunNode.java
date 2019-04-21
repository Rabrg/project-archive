package me.rabrg.aiocombat.nodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;

public final class RunNode extends TaskNode {

    private int nextRun = -1;

    @Override
    public boolean accept() {
        if (nextRun == -1)
            resetNextRun();
        return !getWalking().isRunEnabled() && getWalking().getRunEnergy() >= nextRun;
    }

    @Override
    public int execute() {
        getWalking().toggleRun();
        return Calculations.random(900, 1200);
    }

    private void resetNextRun() {
        nextRun = Calculations.random(10, 40);
    }
}
