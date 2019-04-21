package me.rabrg;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
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

@ScriptManifest(category = Category.MONEYMAKING, name = "Rabrg Battlestaff", author = "Rabrg", version = 1)
public class KappaStaff2 extends AbstractScript implements PaintListener, InventoryListener {

    static final Tile DOOR_TILE = new Tile(3205, 3432); // 11775 = closed

    private int count;

    @Override
    public int onLoop() {
        if ((getInventory().isFull() || getBank().isOpen()) && !getShop().isOpen()) {
            if (!getBank().isOpen() && getBank().openClosest()) {
                sleepUntil(() -> getBank().isOpen(), 6000);
            } else if (getBank().isOpen() && getInventory().contains("Battlestaff")
                    && getBank().depositAll("Battlestaff")) {
                sleepUntil(() -> !getInventory().contains("Battlestaff"), 1200);
            } else if (getBank().isOpen() && !getInventory().contains("Battlestaff") && getBank().close()) {
                sleepUntil(() -> getBank().isOpen(), 1200);
            }
        } else if ((!getBank().isOpen() && !getInventory().isFull()) || getShop().isOpen()) {
            log("1");
            if (getInventory().count("Coins") < 10000) {
               stop();
            } else if (getInventory().isFull() && getShop().close()) {
                sleepUntil(() -> !getShop().isOpen(), 1200);
            } else if (!getShop().isOpen()) {
                final NPC zaff = getNpcs().closest("Zaff");
                if (zaff != null) {
                    if (!getMap().canReach(zaff)) {
                        final GameObject door = getGameObjects().getTopObjectOnTile(DOOR_TILE);
                        if (door != null && door.getID() == 11775 && door.interact("Open")) {
                            sleepUntil(() -> getMap().canReach(zaff), 9000);
                        }
                    } else if (zaff.interact("Trade")) {
                        sleepUntil(() -> getShop().isOpen(), 9000);
                    }
                } else if (getWalking().walk(DOOR_TILE)) {
                    sleep(600, 1800);
                }
            } else {
                log("2");
                final Item battlestaff = getShop().get("Battlestaff");
                if (battlestaff != null) {
                    log("3");
                    if (battlestaff.getAmount() > 0 && getShop().purchase(battlestaff, battlestaff.getAmount())) {
                        log("4");
                        sleep(600, 900);
                    } else if (battlestaff.getAmount() == 0
                            && getWorldHopper().quickHop(getWorlds().getRandomWorld((world) -> !world.isHighRisk()
                            && !world.isF2P() && !world.isDeadmanMode() && world.isMembers()
                            && world.getMinimumLevel() < 1 && world.getID() != 373 && world.getID() != 366
                            && world.getID() != 361 && world.getID() != 353 && world.getID() != 349
                            && !world.isPVP()).getID())) {
                        sleep(3000, 5000);
                    }
                }
            }
        }
        return Calculations.random(0, 600);
    }

    @Override
    public void onPaint(final Graphics g) {
        g.setColor(Color.RED);
        g.drawString("Time: " + Timer.formatTime(getElapsed()), 5, 100);
        g.drawString("Battlestaves: " + count, 5, 115);
        g.drawString("Battlestaves/h: " + getPerHour(count), 5, 130);
    }

    @Override
    public void onItemChange(final Item[] items) {
        for (final Item item : items) {
            if (item != null && "Battlestaff".equals(item.getName()) && item.getAmount() > 0) {
                count += item.getAmount();
            }
        }
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
