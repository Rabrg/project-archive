package me.rabrg.airorb.nodes;

import me.rabrg.airorb.AirOrb;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class HouseTaskNode extends TaskNode {

    private GameObject glory;

    @Override
    public boolean accept() {
        return (glory = getGameObjects().closest("Amulet of Glory")) != null;
    }

    @Override
    public int execute() {
        if (glory.interact("Edgeville")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return AirOrb.EDGEVILLE_AREA.contains(getLocalPlayer());
                }
            }, Calculations.random(2400, 3600));
        }
        return Calculations.random(50, 300);
    }
}
