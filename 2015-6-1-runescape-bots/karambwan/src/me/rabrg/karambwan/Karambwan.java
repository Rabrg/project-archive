package me.rabrg.karambwan;

import me.rabrg.karambwan.node.*;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;

@ScriptManifest(category = Category.MONEYMAKING, name = "Karambwan", author = "Rabrg", version = 0.1)
public class Karambwan extends TaskScript implements PaintListener {

    public static final Area CASTLE_WARS_AREA = new Area(2430, 3080, 2630, 3280);
    public static final Area KARAMAJA_AREA = new Area(2700, 3000, 2900, 3300);
    public static final Area HUT_AREA = new Area(2778, 3055, 2782, 3059);

    public static final Tile BAMBOO_DOOR_TILE = new Tile(2782, 3057);
    public static final Tile HUT_OUTSIDE_TILE = new Tile(2784, 3062);

    public static final String TIADECHE = "Tiadeche";
    public static final String RAW_KARAMBBWAN = "Raw karambwan";
    public static final String RING_OF_DUELING = "Ring of dueling(8)";

    public static int fishies;

    @Override
    public void onStart() {
        addNodes(new PoisonTaskNode(), new OpenBankTaskNode(), new BankTaskNode(), new BuyTaskNode(), new ExitPortalTaskNode(),
                new OpenStoreTaskNode(), new OpenDoorTaskNode(), new TeleportCastleTaskNode(),
                new TeleportHouseTaskNode(), new WalkDoorTaskNode());
    }

    @Override
    public void onPaint(final Graphics g) {
        g.drawString(getLastTaskNode().getClass().getSimpleName(), 15, 50);
        g.drawString("Fishies\t: " + fishies, 15, 65);
        g.drawString("Fishies/h\t: " + getPerHour(fishies), 15, 80);
        g.drawString("Profit\t: " + fishies * 1000, 15, 95);
        g.drawString("Profit/h\t: " + getPerHour(fishies * 1000), 15, 110);
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

    public static final Filter<Item> POISON_FILTER = new Filter<Item>() {
        @Override
        public boolean match(Item item) {
            return item != null && item.getName() != null && item.getName().contains("poison");
        }
    };

    public static final Filter<Item> STAMINA_FILTER = new Filter<Item>() {
        @Override
        public boolean match(Item item) {
            return item != null && item.getName() != null && item.getName().contains("Stamina");
        }
    };
}
