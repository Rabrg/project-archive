package me.rabrg.karambwan.node;

import me.rabrg.karambwan.Karambwan;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public class OpenBankTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return Karambwan.CASTLE_WARS_AREA.contains(getLocalPlayer()) && (getInventory().contains(Karambwan.RAW_KARAMBBWAN)
        || getInventory().contains(Karambwan.POISON_FILTER) || (getCombat().isPoisoned()
                && !getInventory().contains(Karambwan.POISON_FILTER))
                || getInventory().contains(Karambwan.STAMINA_FILTER)) && !getBank().isOpen();
    }

    @Override
    public int execute() {
        final GameObject bank = getGameObjects().closest("Bank chest");
        if (bank != null && bank.interact("Use")) {
            MethodProvider.sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getBank().isOpen();
                }
            }, Calculations.random(3000, 4800));
        }
        return Calculations.random(150, 1200);
    }
}
