package me.rabrg.karambwan.node;

import me.rabrg.karambwan.Karambwan;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;

public final class JiminuaTaskNode extends TaskNode {

    public static final long DRINK_DELAY = 30000;

    public static long lastDrink = Integer.MIN_VALUE;

    private NPC jiminua;

    @Override
    public boolean accept() {
        return getSkills().getRealLevel(Skill.PRAYER) >= 43 && (jiminua = getNpcs().closest("Jiminua")) != null
                && (System.currentTimeMillis() - lastDrink > DRINK_DELAY
                || getShop().isOpen() || getInventory().contains(Karambwan.ANTIPOISON_FILTER)
                || (getSkills().getRealLevel(Skill.PRAYER) >= 43 && getSkills().getBoostedLevels(Skill.PRAYER) > 0
                && !getPrayer().isQuickPrayerActive()));
    }

    @Override
    public int execute() {
        if (getShop().isOpen() && !getInventory().contains(Karambwan.ANTIPOISON_FILTER)
                && getShop().purchaseOne(Karambwan.ANTIPOISON_FILTER)) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getInventory().contains(Karambwan.ANTIPOISON_FILTER);
                }
            }, Calculations.random(1200, 2400));
        } else if (getShop().isOpen() && getInventory().contains(Karambwan.ANTIPOISON_FILTER) && getShop().close()) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return !getShop().isOpen();
                }
            }, Calculations.random(1200, 2400));
        } else if (!getShop().isOpen() && getInventory().contains(Karambwan.ANTIPOISON_FILTER)
                && System.currentTimeMillis() - lastDrink > DRINK_DELAY
                && getInventory().interact(Karambwan.ANTIPOISON_FILTER, "Drink")) {
            lastDrink = System.currentTimeMillis();
            sleep(1200, 2400);
        } else if (getSkills().getRealLevel(Skill.PRAYER) >= 43 && getSkills().getBoostedLevels(Skill.PRAYER) > 0
                && !getPrayer().isQuickPrayerActive() && getPrayer().toggleQuickPrayer(true)) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getPrayer().isQuickPrayerActive();
                }
            }, Calculations.random(1200, 2400));
        } else if (!getShop().isOpen() && jiminua.interact("Trade")
                && !getInventory().contains(Karambwan.ANTIPOISON_FILTER)
                && System.currentTimeMillis() - lastDrink > DRINK_DELAY) {
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getShop().isOpen();
                }
            }, Calculations.random(3000, 4800));
        }
        return Calculations.random(50, 300);
    }
}
