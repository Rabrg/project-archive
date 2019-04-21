package me.rabrg.ironminer.nodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;

public class DropTaskNode extends TaskNode{

    @Override
    public boolean accept() {
        return getInventory().isFull();
    }

    @Override
    public int execute() {
        getInventory().dropAllExcept((item) -> item != null && (item.getName().contains("pickaxe") || item.getName().contains("teleport")));
        return Calculations.random(0, 300);
    }
}
