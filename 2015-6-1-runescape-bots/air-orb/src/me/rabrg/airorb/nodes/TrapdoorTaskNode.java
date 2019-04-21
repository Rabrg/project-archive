package me.rabrg.airorb.nodes;

import me.rabrg.airorb.AirOrb;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class TrapdoorTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return AirOrb.EDGEVILLE_AREA.contains(getLocalPlayer());
    }

    @Override
    public int execute() {
        if (!getWalking().isRunEnabled() && getWalking().toggleRun()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getWalking().isRunEnabled();
                }
            }, Calculations.random(1200, 2400));
        }
        final GameObject trapdoor = getGameObjects().closest("Trapdoor");
        if (trapdoor == null || trapdoor.distance() > 8) {
            getWalking().walk(AirOrb.TRAPDOOR_TILE);
            sleep(300, 1800);
        } else if (!trapdoor.hasAction("Climb-down") && trapdoor.interact("Open")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    final GameObject trapdoor_ = getGameObjects().closest("Trapdoor");
                    return trapdoor_ != null && trapdoor_.hasAction("Climb-down");
                }
            }, Calculations.random(2400, 3600));
        } else if (trapdoor.interact("Climb-down")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !AirOrb.EDGEVILLE_AREA.contains(getLocalPlayer());
                }
            }, Calculations.random(2400, 3600));
        } else {
            getCamera().rotateToEntity(trapdoor);
        }
        return Calculations.random(50, 300);
    }
}
