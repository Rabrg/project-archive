package me.rabrg.airorb.nodes;

import me.rabrg.airorb.AirOrb;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

public final class BankNode extends TaskNode {

    @Override
    public boolean accept() {
        return AirOrb.CLAN_WARS_AREA.contains(getLocalPlayer()) && (getInventory().contains("Air orb")
                || !getInventory().contains("Unpowered orb") || !getInventory().contains("Cosmic rune")
                || getInventory().contains("Amulet of glory")
                || AirOrb.HOUSE && !getInventory().contains("Teleport to house")
                || !AirOrb.HOUSE && !getEquipment().contains(AirOrb.AMULET_OF_GLORY_FILTER)
                || !getEquipment().contains(AirOrb.RING_OF_DUELING_FILTER)
                || !getInventory().isFull()
                || getBank().isOpen());
    }

    @Override
    public int execute() {
        if (getEquipment().contains("Amulet of glory")) {
            if (getInventory().isFull()) {
                if (!getTabs().isOpen(Tab.INVENTORY))
                    getTabs().open(Tab.INVENTORY);
                if (getInventory().contains("Unpowered orb"))
                    getInventory().drop("Unpowered orb");
                else
                    getInventory().drop("Air orb");
            } else {
                final Item unchargedGlory = getEquipment().get("Amulet of glory");
                if (unchargedGlory != null) {
                    if (!getTabs().isOpen(Tab.EQUIPMENT))
                        getTabs().open(Tab.EQUIPMENT);
                    if (unchargedGlory.interact("Remove")) {
                        sleepUntil(new Condition() {
                            @Override
                            public boolean verify() {
                                return getEquipment().get("Amulet of glory") == null;
                            }
                        }, Calculations.random(1200, 2400));
                    }
                }
            }
        } else if (AirOrb.BANK_TILE.distance() > 6) {
            log("walk bank");
            if (getWalking().walk(AirOrb.BANK_TILE)) {
                sleep(300, 1800);
            }
        } else if (getBank().isOpen()) {
            if (getInventory().contains("Air orb") || getInventory().contains("Amulet of glory")) {
                log("deposit all");
                if (getBank().depositAllItems()) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getInventory().isEmpty();
                        }
                    }, Calculations.random(1200, 2400));
                }
            } else if (!getInventory().isFull() && getInventory().count("Cosmic rune") < (AirOrb.HOUSE ? 78 : 81)) {
                log("withdraw cosmic");
                if (getBank().withdraw("Cosmic rune", (AirOrb.HOUSE ? 78 : 81) - getInventory().count("Cosmic rune"))) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getInventory().contains("Cosmic rune");
                        }
                    }, Calculations.random(1200, 2400));
                }
            } else if (!AirOrb.HOUSE && !getEquipment().contains(AirOrb.AMULET_OF_GLORY_FILTER)
                    && !getInventory().contains(AirOrb.AMULET_OF_GLORY_FILTER)) {
                log("withdraw glory");
                if (getBank().withdraw(AirOrb.AMULET_OF_GLORY_FILTER)) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getInventory().contains(AirOrb.AMULET_OF_GLORY_FILTER);
                        }
                    }, Calculations.random(1200, 2400));
                }
            } else if (!getEquipment().contains(AirOrb.RING_OF_DUELING_FILTER)
                    && !getInventory().contains(AirOrb.RING_OF_DUELING_FILTER)) {
                log("withdraw ring");
                if (getBank().withdraw(AirOrb.RING_OF_DUELING_FILTER)) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getInventory().contains(AirOrb.RING_OF_DUELING_FILTER);
                        }
                    }, Calculations.random(1200, 2400));
                }
            } else if (AirOrb.HOUSE && !getInventory().contains("Teleport to house")) {
                log("withdraw house");
                if (getBank().withdraw("Teleport to house")) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getInventory().contains("Teleport to house");
                        }
                    }, Calculations.random(1200, 2400));
                }
            } else if (!getInventory().isFull()) {
                log("withdraw orb");
                if (getBank().withdrawAll("Unpowered orb")) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getInventory().isFull();
                        }
                    }, Calculations.random(1200, 2400));
            }
            } else {
                log("close bank");
                if (getBank().close()) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return !getBank().isOpen();
                        }
                    }, Calculations.random(1200, 2400));
                }
            }
        } else if (!getBank().isOpen()) {
            if (getInventory().contains(AirOrb.AMULET_OF_GLORY_FILTER)) {
                final Item amuletOfGlory = getInventory().get(AirOrb.AMULET_OF_GLORY_FILTER);
                if (amuletOfGlory != null) {
                    if (!getTabs().isOpen(Tab.INVENTORY))
                        getTabs().open(Tab.INVENTORY);
                    amuletOfGlory.interact("Wear");
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return !getInventory().contains(AirOrb.AMULET_OF_GLORY_FILTER);
                        }
                    }, Calculations.random(1200, 2400));
                }
            } else if (getInventory().contains(AirOrb.RING_OF_DUELING_FILTER)) {
                final Item amuletOfGlory = getInventory().get(AirOrb.RING_OF_DUELING_FILTER);
                if (amuletOfGlory != null) {
                    if (!getTabs().isOpen(Tab.INVENTORY))
                        getTabs().open(Tab.INVENTORY);
                    amuletOfGlory.interact("Wear");
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return !getInventory().contains(AirOrb.RING_OF_DUELING_FILTER);
                        }
                    }, Calculations.random(1200, 2400));
                }
            } else {
                final GameObject bank = getGameObjects().closest("Bank chest");
                if (bank != null && bank.interact("Use")) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return getBank().isOpen();
                        }
                    }, Calculations.random(1200, 2400));
                }
            }
        }
        return Calculations.random(50, 300);
    }

}
