package me.rabrg.plankpicker.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public class AgilityTaskNode extends TaskNode {

    private static final Area AGILITY = new Area(2546, 3564, 2551, 3573);

    @Override
    public boolean accept() {
        return AGILITY.contains(getLocalPlayer());
    }

    @Override
    public int execute() {
        final GameObject gate = getGameObjects().closest("Gate");
        if (gate != null && gate.interact("Open")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !AGILITY.contains(getLocalPlayer());
                }
            }, Calculations.random(5000, 75000));
        }
        return 0;
    }
}
