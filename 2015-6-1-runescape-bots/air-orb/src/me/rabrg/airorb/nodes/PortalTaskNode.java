package me.rabrg.airorb.nodes;

import me.rabrg.airorb.AirOrb;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

public final class PortalTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return (AirOrb.CLAN_WARS_AREA.contains(getLocalPlayer())
                && (getSkills().getBoostedLevels(Skill.HITPOINTS) < getSkills().getRealLevel(Skill.HITPOINTS)
                || getSkills().getBoostedLevels(Skill.PRAYER) < getSkills().getRealLevel(Skill.PRAYER)
                || getWalking().getRunEnergy() < 80)) || AirOrb.PORTAL_AREA.contains(getLocalPlayer())
                || AirOrb.PORTAL_TILE.distance() < 6
                || getWalking().getRunEnergy() == 100;
    }

    @Override
    public int execute() {
        if (AirOrb.CLAN_WARS_AREA.contains(getLocalPlayer()) && getWalking().getRunEnergy() < 100) {
            if (AirOrb.PORTAL_TILE.distance() > 6 && getWalking().walk(AirOrb.PORTAL_TILE)) {
                sleep(300, 1800);
            } else {
                final GameObject portal = getGameObjects().closest("Free-for-all portal");
                if (portal != null && portal.interact("Enter")) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return !AirOrb.CLAN_WARS_AREA.contains(getLocalPlayer());
                        }
                    }, Calculations.random(2400, 4800));
                }
            }
        } else if (AirOrb.PORTAL_AREA.contains(getLocalPlayer())) {
            final GameObject portal = getGameObjects().closest("Portal");
            if (portal != null && portal.interact("Exit")) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getWalking().getRunEnergy() == 100;
                    }
                }, Calculations.random(3600, 4200));
            }
        } else if (!AirOrb.HOUSE){
            if (!getTabs().isOpen(Tab.EQUIPMENT))
                getTabs().open(Tab.EQUIPMENT);
            final Item amulet = getEquipment().get(AirOrb.AMULET_OF_GLORY_FILTER);
            log("glory");
            if (amulet != null && amulet.interact("Edgeville")) {
                log("glory2");
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return AirOrb.EDGEVILLE_AREA.contains(getLocalPlayer());
                    }
                }, Calculations.random(5400, 6000));
            }
        } else {
            if (!getTabs().isOpen(Tab.INVENTORY))
                getTabs().open(Tab.INVENTORY);
            if (getInventory().interact("Teleport to house", "Break")) {
                sleep(5400, 6000); // FIXME
            }
        }
        return Calculations.random(50, 300);
    }
}
