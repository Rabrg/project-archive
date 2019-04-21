package me.rabrg.plankpicker.node;

import me.rabrg.plankpicker.PlankPicker;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.GroundItem;

public class GatherPlankTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return !getInventory().isFull();
    }

    @Override
    public int execute() {
        final GroundItem plank = getGroundItems().closest("Plank");
        if (getWalking().getRunEnergy() > 10 && !getWalking().isRunEnabled()) {
            getWalking().toggleRun();
            sleep(300, 900);
        }
        if (plank != null && plank.distance() > 10 && getWalking().walk(plank))
            sleep(300, 1800);
        else if (plank == null && PlankPicker.PLANK_AREA.getRandomTile().distance() > 10
                && getWalking().walk(PlankPicker.PLANK_AREA.getRandomTile()))
            sleep(300, 1800);
        else if (plank != null && plank.distance() <= 10 && plank.interact("Take"))
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    final GroundItem newPlank = getGroundItems().closest("Plank");
                    if (newPlank == null)
                        return true;
                    return !plank.getTile().equals(newPlank.getTile());
                }
            }, Calculations.random(2400, 3000));
        else if (plank == null && PlankPicker.PLANK_AREA.getRandomTile().distance() <= 10) {
            sleep(1800, 2400);
            if (getGroundItems().closest("Plank") == null) {
                if (getWorldHopper().hopWorld(getWorlds().getRandomWorld(new Filter<World>() {
                    @Override
                    public boolean match(final World world) {
                        return !world.isHighRisk() && !world.isF2P() && !world.isDeadmanMode() && world.isMembers()
                                && world.getMinimumLevel() < 1 && world.getID() != 373 && world.getID() != 366
                                && world.getID() != 361 && world.getID() != 353 && world.getID() != 349
                                && !world.isPVP();
                    }
                }))) {
                    sleep(1800, 2400);
                    if (Calculations.random(2) == 1)
                        sleepUntil(new Condition() {
                            @Override
                            public boolean verify() {
                                return getGroundItems().closest("Plank") != null;
                            }
                        }, Calculations.random(40000, 60000));
                }
            }
        }
        return Calculations.random(150, 1200);
    }
//        final GroundItem plank = getGroundItems().closest("Plank");
//        if (!PlankPicker.PLANK_AREA.contains(getLocalPlayer())) {
//            if (getWalking().walk(plank != null ? plank.getTile() : PlankPicker.PLANK_AREA.getRandomTile().translate(0, 1))) {
//                sleep(300, 2400);
//            }
//        } else if (plank != null && plank.interact("Take")) {
//            sleepUntil(new Condition() {
//                @Override
//                public boolean verify() {
//                    return !plank.getTile().equals(getGroundItems().closest("Plank").getTile());
//                }
//            }, Calculations.random(1800, 3000));
//        } else if (plank == null)
//            if (getWalking().getRunEnergy() > 10 && !getWalking().isRunEnabled())
//                getWalking().toggleRun();
//            sleep(1800, 2400);
//            if (Calculations.random(3) == 1 && getGroundItems().closest("Plank") == null
//                    && getWorldHopper().hopWorld(getWorlds().getRandomWorld(new Filter<World>() {
//                @Override
//                public boolean match(final World world) {
//                    return !world.isHighRisk() && !world.isF2P() && !world.isDeadmanMode() && world.isMembers()
//                            && world.getMinimumLevel() < 1 && world.getID() != 373 && world.getID() != 366
//                            && world.getID() != 361 && world.getID() != 353 && world.getID() != 349
//                            && !world.isPVP();
//                }
//            }))) {
//                sleep(1200, 2400);
//                sleepUntil(new Condition() {
//                    @Override
//                    public boolean verify() {
//                        return getGroundItems().closest("Plank") != null;
//                    }
//                }, Calculations.random(40000, 60000));
//            }
//        return Calculations.random(150, 600);
}
