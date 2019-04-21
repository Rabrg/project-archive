package me.rabrg.airorb.nodes;

import me.rabrg.airorb.AirOrb;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;

public final class DeathTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return AirOrb.LUMBRIDGE_AREA.contains(getLocalPlayer());
    }

    @Override
    public int execute() {
        if (getInventory().interact(AirOrb.AMULET_OF_GLORY_FILTER, "Wear")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getInventory().contains(AirOrb.AMULET_OF_GLORY_FILTER);
                }
            }, Calculations.random(1200, 2400));
        } else if (getInventory().interact(AirOrb.RING_OF_DUELING_FILTER, "Wear")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getInventory().contains(AirOrb.RING_OF_DUELING_FILTER);
                }
            }, Calculations.random(1200, 2400));
        } else if (getInventory().interact("Staff of air", "Wield")
                || getInventory().interact("Air battlestaff", "Wield")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getInventory().contains("Staff of air") && !getInventory().contains("Air battlestaff");
                }
            }, Calculations.random(1200, 2400));
        } else if (getEquipment().interact(EquipmentSlot.RING, "Clan Wars")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !AirOrb.LUMBRIDGE_AREA.contains(getLocalPlayer());
                }
            }, Calculations.random(5400, 6000));
            getWorldHopper().hopWorld(getWorlds().getRandomWorld(new Filter<World>() {
                @Override
                public boolean match(final World world) {
                    return !world.isHighRisk() && !world.isF2P() && !world.isDeadmanMode() && world.isMembers()
                            && world.getMinimumLevel() < 1 && world.getID() != 373 && world.getID() != 366
                            && world.getID() != 361 && world.getID() != 353 && world.getID() != 349
                            && !world.isPVP();
                }
            }));
            sleep(2400, 4800);
        }
        return Calculations.random(50, 300);
    }
}
