package me.rabrg.plankpicker.node;

import me.rabrg.plankpicker.PlankPicker;
import org.dreambot.api.data.GameState;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;

public class CollectorTaskNode extends TaskNode {

    private final PlankPicker script;

    public CollectorTaskNode(PlankPicker script) {
        this.script = script;
    }

    @Override
    public boolean accept() {
        script.getRandomManager().enableSolver(RandomEvent.LOGIN);
        log("accepted");
        return true;
    }

    @Override
    public int execute() {
        if (getClient().getGameState() == GameState.LOGGED_IN)
            script.getRandomManager().disableSolver(RandomEvent.LOGIN);
        if (getTrade().isOpen()) {
            getTrade().acceptTrade();
            sleep(500, 3000);
        } else {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getTrade().isOpen();
                }
            }, script.getSleep());
        }
        return Calculations.random(0, 300);
    }
}
