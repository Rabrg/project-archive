package me.rabrg.aiomake.nodes.process;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;

import static me.rabrg.aiomake.AIOMake.*;

public class ProcessTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return !getBank().isOpen() && getInventory().containsAll(buy[0].name, "Pestle and mortar");
    }

    @Override
    public int execute() {
        if (getInventory().interact("Pestle and mortar", "Use") && getInventory().interact(buy[0].name, "Use")) {
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
