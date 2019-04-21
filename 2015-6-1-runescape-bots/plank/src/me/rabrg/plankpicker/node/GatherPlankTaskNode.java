package me.rabrg.plankpicker.node;

import me.rabrg.plankpicker.PlankPicker;
import org.dreambot.api.data.GameState;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.GroundItem;

public class GatherPlankTaskNode extends TaskNode {

    private final PlankPicker script;

    public GatherPlankTaskNode(PlankPicker script) {
        this.script = script;
    }

    private int nextRun = -1;

    @Override
    public boolean accept() {
        if (nextRun == -1)
            resetNextRun();
        return !getInventory().isFull();
    }

    @Override
    public int execute() {
        final GroundItem plank = getGroundItems().closest("Plank");
        if (getWalking().getRunEnergy() >= nextRun && !getWalking().isRunEnabled()) {
            getWalking().toggleRun();
            sleep(300, 900);
        }
        log("" + (plank != null));
        if (plank != null && plank.distance() > 10 && getWalking().walk(plank))
            sleep(300, 1800);
        else if (plank == null && PlankPicker.PLANK_AREA.getRandomTile().distance() > 10
                && getWalking().walk(PlankPicker.PLANK_AREA.getRandomTile()))
            sleep(300, 1800);
        else if (plank != null && plank.distance() <= 10 && plank.interact("Take"))
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    sleep(450, 900);
                    return !plank.exists();
                }
            }, Calculations.random(2400, 3000));
        else if (plank == null && PlankPicker.PLANK_AREA.getRandomTile().distance() <= 10) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    final GroundItem item = getGroundItems().closest("Plank");
                    return item != null && item.exists() && item.interact("Take");
                }
            }, Calculations.random(1800, 2400));
            if (getGroundItems().closest("Plank") == null) {
                getWorldHopper().quickHop(script.next());
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getDialogues().chooseOption(2);
                    }
                }, 4200);
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        final GroundItem item = getGroundItems().closest("Plank");
                        return item != null && item.exists();
                    }
                }, Calculations.random(24000, 48000));
            }
        }
        return Calculations.random(0, 300);
    }

    private void resetNextRun() {
        nextRun = Calculations.random(8, 20);
    }
}
