package me.rabrg.aiomake.nodes.mule;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.friend.Friend;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

public class MuleTaskNode extends TaskNode {

    private boolean[] traded;

    @Override
    public boolean accept() {
        return true;
    }

    @Override
    public int execute() {
        final Item item = getInventory().getItemInSlot(2);
        final NPC npc = getNpcs().closest("NPC name");
        if (item != null && npc != null && item.useOn(npc)) {

        }
        if (traded == null) {
            traded = new boolean[getFriends().getFriends().length];
            sleep(21600000, 28800000);
//            sleep(30000, 60000);
            log("NULL FIXED");
        }
        for (int i = 0; i < traded.length + 1; i++) {
            log("LOOP " + i);
            if (i == traded.length) {
                traded = new boolean[getFriends().getFriends().length];
                sleep(21600000, 28800000);
//                sleep(30000, 60000);
                log("RESET TIMER");
                break;
            }
            if (!traded[i]) {
                log("NOT TRADED " + i);
                final Friend friend = getFriends().getFriends()[i];
                if (!friend.isInMyWorld()) {
                    getWorldHopper().hopWorld(friend.getWorld());
                    sleep(5000);
                } else if (!getTrade().isOpen() && getTrade().tradeWithPlayer(friend.getName())) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getTrade().isOpen();
                        }
                    }, 30000);
                } else if (getTrade().isOpen()) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            getTrade().acceptTrade();
                            sleep(3000, 5000);
                            return !getTrade().isOpen();
                        }
                    }, 30000);
                    if (!getTrade().isOpen()) {
                        traded[i] = true;
                    }
                }
                break;
            }
        }
        return Calculations.random(100, 300);
    }
}
