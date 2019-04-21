package me.rabrg.aiomake.nodes.herblore;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.items.Item;

import static me.rabrg.aiomake.AIOMake.*;

public class CleanHerbTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return !getBank().isOpen() && getInventory().contains(buy[0].name);
    }

    @Override
    public int execute() {
        for (final Item item : getInventory().all()) {
            if (item != null && buy[0].name.equals(item.getName()) && item.interact()) {
                sleep(0, 25);
            }
        }
        return Calculations.random(600, 900);
    }
}
