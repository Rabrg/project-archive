package me.rabrg.construction.node;

import me.rabrg.construction.Construction;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

public class NoteNode extends TaskNode {

    private final Construction construction;

    private GameObject portal;

    public NoteNode(final Construction construction) {
        this.construction = construction;
    }

    @Override
    public boolean accept() {
        return (portal = getGameObjects().closest(4525)) == null || getInventory().count(construction.isOak() ? 8778 :960) < (construction.isOak() ? 8 : 2);
    }

    @Override
    public int execute() {
        if (portal != null && portal.interact("Enter")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getGameObjects().closest(4525) == null;
                }
            }, Calculations.random(4800, 9600));
        } else if (portal == null && getInventory().count(construction.isOak() ? 8778 :960) < (construction.isOak() ? 8 : 2)) {
            final NPC phials = getNpcs().closest("Phials");
            if (phials.distance() > 8) {
                if (getWalking().walk(phials)) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return phials.distance() <= 8;
                        }
                    }, Calculations.random(4800, 9600));
                }
            } else {
                final Item planks = getInventory().get(construction.isOak() ? 8779 :960);
                if (planks.useOn(phials)) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getDialogues().chooseOption(3);
                        }
                    }, Calculations.random(4800, 9600));
                    MethodProvider.sleep(900, 1800);
                }
            }
        } else if (portal == null && getInventory().count(construction.isOak() ? 8778 :960) >= 2) {
            portal = getGameObjects().closest("Portal");
            if (portal.interact("Enter")) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getDialogues().chooseOption(2);
                    }
                }, Calculations.random(4800, 9600));
                sleep(900, 1200);
            }
        }
        return Calculations.random(300, 1200);
    }
}
