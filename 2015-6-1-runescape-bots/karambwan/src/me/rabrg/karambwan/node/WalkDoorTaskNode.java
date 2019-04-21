package me.rabrg.karambwan.node;

import me.rabrg.karambwan.Karambwan;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.TaskNode;

public class WalkDoorTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return Karambwan.KARAMAJA_AREA.contains(getLocalPlayer()) && Karambwan.BAMBOO_DOOR_TILE.distance() > 12;
    }

    @Override
    public int execute() {
        if (getLocalPlayer().getTile().equals(new Tile(2761, 3156)) || getLocalPlayer().getTile().equals(new Tile(2760, 3155))) {
            getWalking().walk(getLocalPlayer().getTile().getRandomizedTile(3));
            sleep(600, 1200);
        }
        if (getWalking().walk(Karambwan.HUT_OUTSIDE_TILE))
            sleep(300, 1800);
        return Calculations.random(150, 1800);
    }
}
