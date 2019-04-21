package me.rabrg.karambwan.node;

import me.rabrg.karambwan.Karambwan;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

/**
 * Created by Ryan Greene on 3/5/2016.
 */
public class BankTaskNode extends TaskNode {

    private long lastStamina;

    private Item inventoryRing;

    @Override
    public boolean accept() {
        return getBank().isOpen()
                || (inventoryRing = getInventory().get(Karambwan.RING_OF_DUELING)) != null
                || getInventory().contains(Karambwan.POISON_FILTER) || getInventory().contains("Salmon") || getInventory().contains(Karambwan.STAMINA_FILTER);
    }

    @Override
    public int execute() {
        if (getBank().isOpen()) {
            if (getInventory().contains(Karambwan.RAW_KARAMBBWAN)) {
                Karambwan.fishies += getInventory().count(Karambwan.RAW_KARAMBBWAN);
                if (getBank().depositAll(Karambwan.RAW_KARAMBBWAN)) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return !getInventory().contains(Karambwan.RAW_KARAMBBWAN);
                        }
                    }, Calculations.random(1200, 2400));
                }
            } else if (getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) == null
                    && !getInventory().contains(Karambwan.RING_OF_DUELING)
                    && getBank().withdraw(Karambwan.RING_OF_DUELING)) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getInventory().contains(Karambwan.RING_OF_DUELING);
                    }
                }, Calculations.random(1200, 2400));
            } else if (getSkills().getBoostedLevels(Skill.HITPOINTS) < 19 && !getInventory().contains("Salmon")
                    && getBank().withdraw("Salmon")) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getInventory().contains("Salmon");
                    }
                }, Calculations.random(1200, 2400));
            } else if (getWalking().getRunEnergy() < 50 && !getInventory().contains(Karambwan.STAMINA_FILTER)
                    && System.currentTimeMillis() - lastStamina > 60000
                    && getBank().withdraw(Karambwan.STAMINA_FILTER)) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getInventory().contains(Karambwan.STAMINA_FILTER);
                    }
                }, Calculations.random(1200, 2400));
            } else if (getCombat().isPoisoned() && !getInventory().contains(Karambwan.POISON_FILTER)
                    && getBank().withdraw(Karambwan.POISON_FILTER)) {
                sleep(1200, 2400);
            } else if (!getCombat().isPoisoned() && getInventory().contains(Karambwan.POISON_FILTER)) {
                getBank().depositAll(Karambwan.POISON_FILTER);
            } else if (System.currentTimeMillis() - lastStamina < 60000 && getInventory().contains(Karambwan.STAMINA_FILTER)) {
                getBank().depositAll(Karambwan.STAMINA_FILTER);
            } else if (getBank().close()) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return !getBank().isOpen();
                    }
                }, Calculations.random(1200, 2400));
            }
        }
        if (!getBank().isOpen()
                && getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) == null
                && inventoryRing != null && (getTabs().isOpen(Tab.INVENTORY) || getTabs().open(Tab.INVENTORY))
                && inventoryRing.interact("Wear")) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) != null;
                }
            }, Calculations.random(1200, 2400));
        }
        if (!getBank().isOpen() && getInventory().contains(Karambwan.STAMINA_FILTER)
                && System.currentTimeMillis() - lastStamina > 60000) {
            getTabs().open(Tab.INVENTORY);
            final Item salmon = getInventory().get(Karambwan.STAMINA_FILTER);
            if (salmon.interact("Drink")) {
                lastStamina = System.currentTimeMillis();
                sleep(1200, 2400);
            }
        }
        if (!getBank().isOpen() && getInventory().contains("Salmon")) {
            getTabs().open(Tab.INVENTORY);
            final Item salmon = getInventory().get("Salmon");
            if (salmon.interact("Eat"))
                sleep(1200, 2400);
        }

        if (!getBank().isOpen() && getCombat().isPoisoned() && getInventory().contains(Karambwan.POISON_FILTER)) {
            getTabs().open(Tab.INVENTORY);
            final Item salmon = getInventory().get(Karambwan.POISON_FILTER);
            if (salmon.interact("Drink"))
                sleep(1200, 2400);
        }
        return Calculations.random(150, 600);
    }
}
