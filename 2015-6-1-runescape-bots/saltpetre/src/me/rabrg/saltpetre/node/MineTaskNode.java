package me.rabrg.saltpetre.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.GameObject;

public class MineTaskNode extends TaskNode {

    private static final Tile CENTER_TILE = new Tile(1680, 3535);

    @Override
    public boolean accept() {
        return !getInventory().isFull();
    }

    @Override
    public int execute() {
        final GameObject rocks = getGameObjects().closest("Saltpetre");
        if (rocks == null && CENTER_TILE.distance() > 5 && !hasRocks() && getWalking().walk(CENTER_TILE)) {
            sleep(150, 1800);
        } else if (rocks == null && CENTER_TILE.distance() <= 5 && !hasRocks() && getWorldHopper().quickHop(hoppableWorld().getID())) {
            sleep(4200, 4800);
        } else if (rocks != null && rocks.distance() > 8 && getWalking().walk(rocks)) {
            sleep(150, 1800);
        } else if (rocks != null && rocks.distance() <= 8 && rocks.interact("Dig")) {
            sleepUntil(() -> getInventory().isFull() || !rocks.exists(), Calculations.random(60000, 90000));
        }
        return 0;
    }

    private boolean validWorld(final World world) {
        return !world.isDeadmanMode() && !world.isF2P() && !world.isPVP() && world.getMinimumLevel() < 1
                && world.getID() != getClient().getCurrentWorld() && world.getRealID() != getClient().getCurrentWorld();
    }

    private World hoppableWorld() {
        return getWorlds().getRandomWorld(this::validWorld);
    }

    private boolean hasRocks() {
        for (int i = 0; i < 10; i++) {
            if (getGameObjects().closest("Saltpetre") != null)
                return true;
        }
        return false;
    }
}
