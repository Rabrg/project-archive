package me.rabrg.ironminer.nodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.TaskNode;

public class ChangeWorldTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return getPlayers().all().size() > 1;
    }

    @Override
    public int execute() {
        if (getWorldHopper().quickHop(getWorlds().getRandomWorld(new Filter<World>() {
            @Override
            public boolean match(World world) {
                return !world.isHighRisk() && !world.isF2P() && !world.isDeadmanMode() && world.isMembers()
                        && world.getMinimumLevel() < 1 && world.getID() != 373 && world.getID() != 366
                        && world.getID() != 361 && world.getID() != 353 && world.getID() != 349
                        && !world.isPVP();
            }
        }).getID())) {
            sleep(2048, 4096);
        }
        return Calculations.random(0, 300);
    }
}
