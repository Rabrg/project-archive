package me.rabrg.karambwan.node;

import me.rabrg.karambwan.Karambwan;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public class OpenDoorTaskNode extends TaskNode {

    private GameObject door;

    @Override
    public boolean accept() {
        return Karambwan.BAMBOO_DOOR_TILE.distance() <= 12 && !Karambwan.HUT_AREA.contains(getLocalPlayer())
                && (door = getGameObjects().getTopObjectOnTile(Karambwan.BAMBOO_DOOR_TILE)) != null;
    }

    @Override
    public int execute() {
        if (door.interact("Open")) {
            MethodProvider.sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return Karambwan.HUT_AREA.contains(getLocalPlayer());
                }
            }, Calculations.random(2400, 4800));
        }
        return Calculations.random(150, 1800);
    }
}
