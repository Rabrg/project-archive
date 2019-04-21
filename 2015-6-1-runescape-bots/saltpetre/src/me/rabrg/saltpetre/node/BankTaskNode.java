package me.rabrg.saltpetre.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.TaskNode;

public class BankTaskNode extends TaskNode {

    private static final Tile BANK_TILE = new Tile(1674, 3561);

    @Override
    public boolean accept() {
        return getInventory().isFull();
    }

    @Override
    public int execute() {
        if (BANK_TILE.distance() > 8 && getWalking().walk(BANK_TILE))
            sleep(150, 1800);
        else if (BANK_TILE.distance() <= 8 && !getBank().isOpen() && getBank().open())
            sleepUntil(() -> getBank().isOpen(), Calculations.random(4800, 7200));
        else if (getBank().isOpen() && getBank().depositAllExcept("Spade"))
            sleepUntil(() -> getInventory().onlyContains("Spade"), 1200);
        return Calculations.random(0, 1200);
    }
}
