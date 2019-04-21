package me.rabrg.airorb.nodes;

import me.rabrg.airorb.AirOrb;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class TunnelGateTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return AirOrb.TUNNEL_GATE_AREA.contains(getLocalPlayer());
    }

    @Override
    public int execute() {
        final GameObject gate = getGameObjects().closest("Gate");
        if (gate == null || gate.distance() > 6) {
            getWalking().walk(AirOrb.TUNNEL_GATE_TILE);
            sleep(300, 1800);
        } else if (!gate.hasAction("Close") && gate.interact("Open")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getMap().canReach(AirOrb.TUNNEL_GATE_EXIT_TILE);
                }
            }, Calculations.random(2400, 3600));
        } else {
            getWalking().walk(AirOrb.WILDERNESS_ENTRANCE_GATE_TILE);
            sleep(300, 1800);
        }
        return Calculations.random(50, 300);
    }
}

