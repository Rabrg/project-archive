package me.rabrg.plankpicker.node;

import me.rabrg.plankpicker.PlankPicker;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.friend.Friend;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public class GiveTaskNode extends TaskNode {

//    public static long last = 0;
    public static long last = System.currentTimeMillis();

    @Override
    public boolean accept() {
        if (System.currentTimeMillis() - last > 1800000)
            for (final Friend friend : getFriends().getFriends()) {
                if (friend != null && friend.isOnline()) {
                    return true;
                }
            }
        return false;
    }

    @Override
    public int execute() {
        if (!getTrade().isOpen() && !getInventory().contains(961) || getBank().isOpen()) {
            if (!PlankPicker.BANK_AREA.contains(getLocalPlayer())) {
                if (getWalking().walk(PlankPicker.BANK_AREA.getRandomTile())) {
                    sleep(300, 2400);
                }
            } else if (!getBank().isOpen()) {
                final GameObject bank = getGameObjects().closest("Bank chest");
                if (bank != null && bank.interact("Use")) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getBank().isOpen();
                        }
                    }, Calculations.random(2400, 3000));
                }
            } else if (getBank().isOpen() && !getInventory().isEmpty() && !getInventory().contains(961) && getBank().depositAllItems()) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getInventory().isEmpty();
                    }
                }, Calculations.random(1200, 1800));
            } else if (getBank().isOpen() && getInventory().isEmpty() && getBank().setWithdrawMode(BankMode.NOTE) && getBank().withdrawAll("Plank")) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getInventory().contains("Plank");
                    }
                }, Calculations.random(1200, 1800));
            } else {
                getBank().close();
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return !getBank().isOpen();
                    }
                }, Calculations.random(1200, 1800));
            }
        } else {
            log("else");
            String name = null;
            for (final Friend friend : getFriends().getFriends()) {
                if (friend != null && friend.isOnline()) {
                    name = friend.getName();
                    if (!friend.isInMyWorld()) {
                        getWorldHopper().hopWorld(friend.getWorld());
                        sleep(5000);
                    }
                    break;
                }
            }
            if (!getTrade().isOpen()) {
                log("Trade");
                if (getTrade().tradeWithPlayer(name)) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getTrade().isOpen();
                        }
                    }, Calculations.random(3000, 6000));
                }
            } else {
                if (getInventory().contains(961))
                    getTrade().addItem(961, 9999999);
                getTrade().acceptTrade();
                sleep(2500, 5000);
                if (!getTrade().isOpen()) {
                    last = System.currentTimeMillis();
                }
            }
        }
        return Calculations.random(0, 300);
    }
}
