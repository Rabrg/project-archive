package me.rabrg.aiomake.nodes.mule;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.Player;

import static me.rabrg.aiomake.AIOMake.giveMule;
import static me.rabrg.aiomake.AIOMake.goldKeep;
import static me.rabrg.aiomake.AIOMake.muleName;

public class GiveMuleTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return giveMule;
    }

    @Override
    public int execute() {
        if (!getBank().isOpen() && !getInventory().contains("Coins") && getBank().openClosest() && !getTrade().isOpen()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getBank().isOpen();
                }
            }, 4000);
        } else if (getBank().isOpen() && !getInventory().contains("Coins") && getBank().depositAllItems()
                && getBank().withdraw("Coins", getBank().count("Coins") - goldKeep) && getBank().close()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getBank().isOpen();
                }
            }, 1200);
            if (!getInventory().contains("Coins") && getBank().count("Coins") <= goldKeep)
                giveMule = false;
        } else if (getBank().isOpen() && getInventory().contains("Coins") && getBank().close()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getBank().isOpen();
                }
            }, 1200);
        } else if (!getBank().isOpen() && getInventory().contains("Coins") && !getTrade().isOpen()) {
            final Player player = getPlayers().closest(muleName);
            if (player != null && player.interact(player.getActions()[2]))
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getTrade().isOpen();
                    }
                }, 1200);
        } else if (getTrade().isOpen() && !getTrade().contains(true, "Coins") && getTrade().addItem("Coins", getInventory().count("Coins"))) {
            sleep(900, 1200);
        } else if (getTrade().isOpen() && getTrade().contains(true, "Coins") && getTrade().acceptTrade()) {
            sleep(900, 1200);
            if (!getTrade().isOpen() && !getInventory().contains("Coins")) {
                giveMule = false;
            }
        }
        return Calculations.random(100, 300);
    }
}
