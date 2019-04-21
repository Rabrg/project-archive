package me.rabrg.karambwan.node;

import me.rabrg.karambwan.Karambwan;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

public class BuyTaskNode extends TaskNode {

    @Override
    public boolean accept() {
        return getShop().isOpen() && !getInventory().isFull();
    }

    @Override
    public int execute() {
        for (int i = 0; i < 2; i++) {
            final Item karambwan = getShop().get(Karambwan.RAW_KARAMBBWAN);
            if (karambwan == null || karambwan.getAmount() == 0) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        final Item karambwan = getShop().get(Karambwan.RAW_KARAMBBWAN);
                        return karambwan != null && karambwan.getAmount() > 0;
                    }
                }, Calculations.random(6000, 6600));
            }
            if (karambwan != null && karambwan.interact("Buy 10"))
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        final Item karambwan = getShop().get(Karambwan.RAW_KARAMBBWAN);
                        return karambwan == null || karambwan.getAmount() == 0;
                    }
                }, Calculations.random(3000, 4800));
            sleep(150, 300);
            if (getInventory().isFull())
                break;
        }
        if (!getInventory().isFull())
            getWorldHopper().quickHop(getWorlds().getRandomWorld(new Filter<World>() {
                @Override
                public boolean match(World world) {
                    return !world.isHighRisk() && !world.isF2P() && !world.isDeadmanMode() && world.isMembers()
                            && world.getMinimumLevel() < 1 && world.getID() != 373 && world.getID() != 366
                            && world.getID() != 361 && world.getID() != 353 && world.getID() != 349
                            && !world.isPVP();
                }
            }).getID());
        return Calculations.random(150, 600);
    }
}
