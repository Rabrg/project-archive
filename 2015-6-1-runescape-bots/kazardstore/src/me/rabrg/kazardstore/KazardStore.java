package me.rabrg.kazardstore;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;

@ScriptManifest(category = Category.MONEYMAKING, name = "Rabrg Kazard Buyer", author = "Rabrg", version = 1.0)
public class KazardStore extends AbstractScript implements InventoryListener, PaintListener {

    private int profit;

    @Override
    public int onLoop() {
        if (getInventory().isFull() && getShop().isOpen() && getShop().close()) {
            sleepUntil(() -> !getShop().isOpen(), Calculations.random(900, 1800));
        } else if (getInventory().isFull() && !getBank().isOpen()) {
            final GameObject bankChest = getGameObjects().closest("Bank chest");
            if (bankChest != null && bankChest.interact("Use"))
                sleepUntil(() -> getBank().isOpen(), Calculations.random(4800, 7200));
        } else if (getBank().isOpen() && getInventory().getEmptySlots() != 27 && getBank().depositAllExcept("Coins")) {
            sleepUntil(() -> getInventory().emptySlotCount() == 27, Calculations.random(900, 1800));
        } else if (getBank().isOpen() && !getInventory().isFull() && getBank().close()) {
            sleepUntil(() -> !getBank().isOpen(), Calculations.random(900, 1800));
        } else if (!getInventory().isFull() && !getShop().isOpen()) {
            final NPC shopKeeper = getNpcs().closest("Shop keeper");
            if (shopKeeper != null && shopKeeper.interact("Trade"))
                sleepUntil(() -> getShop().isOpen(), Calculations.random(4800, 7200));
        } else if (!getInventory().isFull() && getShop().isOpen()) {
            final Item paste = getShop().get("Swamp paste");
            final Item flour = getShop().get("Pot of flour");
            final Item pickaxe = getShop().get("Bronze pickaxe");
            if (paste != null && paste.getAmount() > 410 && getShop().purchase("Swamp paste", paste.getAmount() - 400)) {
                sleepUntil(() -> shopZero("Swamp paste", 400), 1200);
            } else if (flour != null && flour.getAmount() > 0) {
                if (getShop().purchase("Pot of flour", Math.min(flour.getAmount(), getInventory().getEmptySlots())))
                    sleepUntil(() -> getInventory().isFull() || shopZero("Pot of flour", 0), Calculations.random(900, 1800));
            } else if (pickaxe != null && pickaxe.getAmount() > 0) {
                if (getShop().purchase("Bronze pickaxe", Math.min(pickaxe.getAmount(), getInventory().getEmptySlots())))
                    sleepUntil(() -> getInventory().isFull() || shopZero("Bronze pickaxe", 0), Calculations.random(900, 1800));
            } else if (flour != null && flour.getAmount() <= 1 && pickaxe != null && pickaxe.getAmount() <= 1) {
                if (!getInventory().isFull() && getWorldHopper().quickHop(hoppableWorld().getID()))
                    sleep(3000, 4800);
            }
        }
        return Calculations.random(0, 900);
    }

    @Override
    public void onItemChange(final Item[] items) {
        for (final Item item : items) {
            if (item != null && "Pot of flour".equals(item.getName()) && item.getAmount() > 0)
                profit += item.getAmount() * 192;
            else if (item != null && "Bronze pickaxe".equals(item.getName()) && item.getAmount() > 0)
                profit += item.getAmount() * 76;
            else if (item != null && "Swamp paste".equals(item.getName()) && item.getAmount() > 0)
                profit += item.getAmount() * 91;
            else if (item != null && "Coins".equals(item.getName()))
                profit += item.getAmount();
        }
    }

    @Override
    public void onPaint(final Graphics g) {
        g.drawString("Time: " + Timer.formatTime(getElapsed()), 5, 100);
        g.drawString("Profit: " + profit, 5, 115);
        g.drawString("Profit/h: " + getPerHour(profit), 5, 130);
    }

    private boolean validWorld(final World world) {
        return !world.isDeadmanMode() && !world.isF2P() && !world.isPVP() && world.getMinimumLevel() < 1
                && world.getID() != getClient().getCurrentWorld() && world.getRealID() != getClient().getCurrentWorld();
    }

    private World hoppableWorld() {
        return getWorlds().getRandomWorld(this::validWorld);
    }

    private boolean shopZero(final String name, final int amount) {
        final Item item = getShop().get(name);
        return item != null && item.getAmount() <= amount;
    }

    private long startTime = System.currentTimeMillis();

    public long getElapsed() {
        return System.currentTimeMillis() - startTime;
    }

    private int getPerHour(double value) {
        if (getElapsed() > 0) {
            return (int) (value * 3600000d / getElapsed());
        } else {
            return 0;
        }
    }
}
