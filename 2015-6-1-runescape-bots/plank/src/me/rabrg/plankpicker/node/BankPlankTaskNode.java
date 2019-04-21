package me.rabrg.plankpicker.node;

import me.rabrg.plankpicker.PlankPicker;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public class BankPlankTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return getInventory().isFull();
    }

    @Override
    public int execute() {
        if (!PlankPicker.BANK_AREA.contains(getLocalPlayer())) {
            if (getWalking().walk(PlankPicker.BANK_AREA.getRandomTile())) {
                sleep(300, 2400);
            }
        } else if (!getBank().isOpen()){
            final GameObject bank = getGameObjects().closest("Bank chest");
            if (bank != null && bank.interact("Use")) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getBank().isOpen();
                    }
                }, Calculations.random(2400, 3000));
            }
        } else if (getBank().depositAllItems()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().isEmpty();
                }
            }, Calculations.random(1200, 1800));
        }
        return Calculations.random(0, 300);
    }
}
