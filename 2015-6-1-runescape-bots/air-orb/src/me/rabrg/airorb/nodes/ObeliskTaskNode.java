package me.rabrg.airorb.nodes;

import me.rabrg.airorb.AirOrb;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;

public final class ObeliskTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return AirOrb.OBELISK_AREA.contains(getLocalPlayer());
    }

    @Override
    public int execute() {
        if (!getTabs().isOpen(Tab.MAGIC))
            getTabs().open(Tab.MAGIC);

        if (getPlayers().all(new Filter<Player>() {
            @Override
            public boolean match(final Player player) {
                final org.dreambot.api.wrappers.interactive.Character o = player.getInteractingCharacter();
                return o != null && o.getName() != null && o.getName().equals(getLocalPlayer().getName());
            }
        }).size() > 0) {
            if (!getTabs().isOpen(Tab.EQUIPMENT))
                getTabs().open(Tab.EQUIPMENT);
            final Item ring = getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot());
            if (ring != null && ring.interact("Clan Wars")) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return !AirOrb.OBELISK_AREA.contains(getLocalPlayer());
                    }
                }, Calculations.random(5400, 6000));
                sleep(5400, 6000);
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
        } else if (getInventory().contains("Unpowered orb")) {
            final GameObject obelisk = getGameObjects().closest("Obelisk of Air");
            if (obelisk != null) {
                getMagic().castSpellOn(Normal.CHARGE_AIR_ORB, obelisk);
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        final WidgetChild child = getWidgets().getChildWidget(309, 6);
                        return child != null && child.isVisible() && child.interact();
                    }
                }, Calculations.random(1200, 2400));
                sleep(375, 425);
            }
        } else {
            if (!getTabs().isOpen(Tab.EQUIPMENT))
                getTabs().open(Tab.EQUIPMENT);
            final Item ring = getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot());
            if (ring != null && ring.interact("Clan Wars")) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return !AirOrb.OBELISK_AREA.contains(getLocalPlayer());
                    }
                }, Calculations.random(5400, 6000));
            }
        }
        return Calculations.random(200, 300);
    }
}
