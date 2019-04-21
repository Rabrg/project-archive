package me.rabrg.aiomake.nodes;

import me.rabrg.aiomake.AIOMake;
import me.rabrg.aiomake.GEItem;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;

import static me.rabrg.aiomake.AIOMake.*;

public class GrandExchangeTaskNode extends TaskNode {

    private String[] buyNames;

    public GrandExchangeTaskNode() {
        buyNames = new String[buy.length];
        for (int i = 0; i < buyNames.length; i++)
            buyNames[i] = buy[i].name;
    }

    @Override
    public boolean accept() {
        return grandExchange;
    }

    @Override
    public int execute() {
        if (getBank().isOpen() && getBank().contains(sell.name) && getBank().setWithdrawMode(BankMode.NOTE)
                && getBank().withdrawAll(sell.name)) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().contains(sell.name);
                }
            }, 1200);
        } else if (getBank().isOpen() && getBank().contains("Coins") && getBank().withdrawAll("Coins")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().contains("Coins");
                }
            }, 1200);
        } else if (getBank().isOpen() && getInventory().contains("Coins") && !getInventory().containsAll(buyNames)
                && getBank().close()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getBank().isOpen();
                }
            }, 1200);
        } else if (!getBank().isOpen() && !getGrandExchange().isOpen() && getInventory().contains("Coins")
                && !getInventory().contains(buy[0].name) && getGrandExchange().open()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getGrandExchange().isOpen();
                }
            }, 4000);
        } else if (getGrandExchange().isOpen() && getInventory().contains(sell.name)
                && getGrandExchange().sellItem(sell.name, getInventory().count(sell.name), (int) (sell.getPrice() * 0.9))) {
            log("" + (int) (sell.getPrice() * 0.95));
            sleep(1800, 2400);
        } else if (getGrandExchange().isOpen() && getGrandExchange().isReadyToCollect()
                && getGrandExchange().collect()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getGrandExchange().isReadyToCollect();
                }
            }, 1600);
        } else if (getGrandExchange().isOpen() && !getInventory().contains(sell.name)
                && !getInventory().containsAll(buyNames) && !getGrandExchange().isReadyToCollect()) {
            for (final String buyName : buyNames) {
                if (getInventory().contains(buyName))
                    continue;
                for (final GEItem item : buy) {
                    if (item.name.equals(buyName) && getGrandExchange().buyItem(buyName, amount, (int) (item.getPrice() * 1.3))) {
                        sleepUntil(new Condition() {
                            @Override
                            public boolean verify() {
                                return getGrandExchange().isReadyToCollect();
                            }
                        }, 500000);
                    }
                }
            }
            sleep(1800, 2400);
        } else if (getGrandExchange().isOpen() && !getInventory().contains(sell.name) && getInventory().containsAll(buyNames)
                && !getGrandExchange().isReadyToCollect() && getGrandExchange().close()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getGrandExchange().isOpen();
                }
            }, 1200);
        } else if (!getGrandExchange().isOpen() && !getInventory().contains(sell.name) && getInventory().containsAll(buyNames)
                && !getBank().isOpen() && getBank().openClosest()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getBank().isOpen();
                }
            }, 1200);
        } else if (getBank().isOpen() && !getInventory().contains(sell.name) && getInventory().containsAll(buyNames)
                && getBank().depositAllItems()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().isEmpty();
                }
            }, 1200);
            if (getInventory().isEmpty())
                grandExchange = false;
        }
        return Calculations.random(100, 300);
    }
}
