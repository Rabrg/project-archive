package me.rabrg.karambwan.node;

import me.rabrg.karambwan.Karambwan;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;

public class PoisonTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return !Karambwan.CASTLE_WARS_AREA.contains(getLocalPlayer()) && getCombat().isPoisoned();
    }

    @Override
    public int execute() {
        Toolkit.getDefaultToolkit().beep();
        if (!getTabs().isOpen(Tab.EQUIPMENT) && getTabs().open(Tab.EQUIPMENT)) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getTabs().isOpen(Tab.EQUIPMENT);
                }
            }, Calculations.random(1200, 1800));
        }
        final Item ring = getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot());
        if (ring != null && ring.interact("Castle Wars")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return Karambwan.CASTLE_WARS_AREA.contains(getLocalPlayer());
                }
            }, Calculations.random(4200, 4800));
        }
        return Calculations.random(150, 600);
    }
}
