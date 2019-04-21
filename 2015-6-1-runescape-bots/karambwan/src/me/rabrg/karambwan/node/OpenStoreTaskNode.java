package me.rabrg.karambwan.node;

import me.rabrg.karambwan.Karambwan;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;

public class OpenStoreTaskNode extends TaskNode {

    private NPC tiadeche;
    @Override
    public boolean accept() {
        return Karambwan.HUT_AREA.contains(getLocalPlayer()) && !getInventory().isFull() && !getShop().isOpen()
                && (tiadeche = getNpcs().closest(Karambwan.TIADECHE)) != null;
    }

    @Override
    public int execute() {
        if (tiadeche.interact("Trade")) {
            MethodProvider.sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getShop().isOpen();
                }
            }, Calculations.random(1800, 3000));
        }
        return Calculations.random(150, 600);
    }
}
