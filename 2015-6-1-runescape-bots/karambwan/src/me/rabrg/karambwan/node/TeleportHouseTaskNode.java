package me.rabrg.karambwan.node;

import me.rabrg.karambwan.Karambwan;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

public class TeleportHouseTaskNode extends TaskNode {

    private Item teleportTab;

    @Override
    public boolean accept() {
        return Karambwan.CASTLE_WARS_AREA.contains(getLocalPlayer())
                && getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) != null
                && (teleportTab = getInventory().get("Teleport to house")) != null && getInventory().contains("Coins")
                && !getInventory().isFull();
    }

    @Override
    public int execute() {
        if (!getTabs().isOpen(Tab.INVENTORY)) {
            getTabs().open(Tab.INVENTORY);
        }
        if (teleportTab.interact("Break")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !Karambwan.CASTLE_WARS_AREA.contains(getLocalPlayer());
                }
            }, Calculations.random(1800, 2400));
            sleep(1800, 2400);
        }
        return Calculations.random(150, 1800);
    }
}
