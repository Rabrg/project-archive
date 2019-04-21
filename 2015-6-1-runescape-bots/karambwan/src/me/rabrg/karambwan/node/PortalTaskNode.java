package me.rabrg.karambwan.node;

import me.rabrg.karambwan.Karambwan;
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
        log("" + Karambwan.CLAN_WARS_AREA.contains(getLocalPlayer()) + " " + Karambwan.PORTAL_AREA.contains(getLocalPlayer())
        + " " + (Karambwan.PORTAL_TILE.distance() < 6));
        return (Karambwan.CLAN_WARS_AREA.contains(getLocalPlayer())
                && (getSkills().getBoostedLevels(Skill.HITPOINTS) < getSkills().getRealLevel(Skill.HITPOINTS)
                || getSkills().getBoostedLevels(Skill.PRAYER) < getSkills().getRealLevel(Skill.PRAYER)
                || getWalking().getRunEnergy() < 80) || getSkills().getBoostedLevels(Skill.PRAYER) < 40) || Karambwan.PORTAL_AREA.contains(getLocalPlayer())
                || (Karambwan.PORTAL_TILE.distance() < 6);
    }

    @Override
    public int execute() {
        if (Karambwan.CLAN_WARS_AREA.contains(getLocalPlayer()) && getWalking().getRunEnergy() < 100) {
            if (Karambwan.PORTAL_TILE.distance() > 6 && getWalking().walk(Karambwan.PORTAL_TILE)) {
                sleep(300, 1800);
            } else {
                final GameObject portal = getGameObjects().closest("Free-for-all portal");
                if (portal != null && portal.interact("Enter")) {
                    sleepUntil(new Condition() {
                        @Override
                        public boolean verify() {
                            return !Karambwan.CLAN_WARS_AREA.contains(getLocalPlayer());
                        }
                    }, Calculations.random(2400, 4800));
                }
            }
        } else if (Karambwan.PORTAL_AREA.contains(getLocalPlayer())) {
            final GameObject portal = getGameObjects().closest("Portal");
            if (portal != null && portal.interact("Exit")) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getWalking().getRunEnergy() == 100;
                    }
                }, Calculations.random(3600, 4200));
            }
        } else {
            if (!getTabs().isOpen(Tab.INVENTORY)) {
                getTabs().open(Tab.INVENTORY);
            }
            final Item teleportTab = getInventory().get("Teleport to house");
            if (teleportTab.interact("Break")) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return !Karambwan.CASTLE_WARS_AREA.contains(getLocalPlayer());
                    }
                }, Calculations.random(1800, 2400));
                sleep(1800, 2400);
            }
        }
        return Calculations.random(50, 300);
    }
}
