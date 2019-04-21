package me.rabrg.airorb.nodes;

import me.rabrg.airorb.AirOrb;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class LadderTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return AirOrb.LADDER_AREA.contains(getLocalPlayer());
    }

    @Override
    public int execute() {
        final GameObject ladder = getGameObjects().closest("Ladder");
        if (ladder == null || ladder.distance() > 6) {
            getWalking().walk(AirOrb.LADDER_TILE);
            sleep(300, 1800);
        } else if (ladder.interact("Climb-up")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !AirOrb.LADDER_AREA.contains(getLocalPlayer());
                }
            }, Calculations.random(2400, 3600));
        }
        return Calculations.random(50, 300);
    }
}