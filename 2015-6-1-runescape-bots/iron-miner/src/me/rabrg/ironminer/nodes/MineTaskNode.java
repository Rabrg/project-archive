package me.rabrg.ironminer.nodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public class MineTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return !getInventory().isFull() && !getLocalPlayer().isAnimating();
    }

    @Override
    public int execute() {
        final GameObject rocks = getGameObjects().closest(7488, 7455, 7486, 7485, 7484, 7453); // TODO: remove last 2
        if (rocks != null && rocks.distance() < 1.5 && rocks.interact("Mine")) {
//            final GameObject rocks_ = getGameObjects().closest(new Filter<GameObject>() {
//                @Override
//                public boolean match(GameObject gameObject) {
//                    return gameObject != null && !gameObject.getTile().equals(rocks.getTile())
//                            && (gameObject.getID() == 7488 || rocks.getID() == 7455);
//                }
//            });
//            if (rocks_ != null)
//                getMouse().move(rocks_);
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    final GameObject rocks_ = getGameObjects().getTopObjectOnTile(rocks.getTile());
                    return rocks_ != null && rocks_.getID() != rocks.getID() || getDialogues().continueDialogue();
                }
            }, 8192);
        } else {
//            getInventory().dropAllExcept(1273, 1269, 1275, 1271);
        }
        return Calculations.random(0, 300);
    }
}
