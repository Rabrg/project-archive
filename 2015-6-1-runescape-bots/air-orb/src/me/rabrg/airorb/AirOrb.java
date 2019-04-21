package me.rabrg.airorb;

import me.rabrg.airorb.nodes.*;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;

@ScriptManifest(category =  Category.MONEYMAKING, name = "Rabrg Air Orbs", author = "Rabrg", version = 0.1)
public final class AirOrb extends TaskScript implements PaintListener, InventoryListener {

    public static final Area EDGEVILLE_AREA = new Area(3050, 3468, 3106, 3520);
    public static final Area TUNNEL_GATE_AREA = new Area(3093, 9867, 3103, 9915);
    public static final Area WILDERNESS_ENTRANCE_GATE_AREA = new Area(3104, 9907, 3149, 9917);
    public static final Area WILDERNESS_TUNNEL_AREA = new Area(3104, 9918, 3134, 9944);
    public static final Area LADDER_AREA = new Area(3080, 9945, 3110, 9975);
    public static final Area OBELISK_AREA = new Area(3084, 3566, 3091, 3575);
    public static final Area CLAN_WARS_AREA = new Area(3350, 3148, 3402, 3185);
    public static final Area PORTAL_AREA = new Area(3320, 4759, 3334, 4751);
    public static final Area LUMBRIDGE_AREA = new Area(3217, 3211, 3226, 3228);

    public static final Tile TRAPDOOR_TILE = new Tile(3094, 3470);
    public static final Tile TUNNEL_GATE_TILE = new Tile(3101, 9909);
    public static final Tile TUNNEL_GATE_EXIT_TILE = new Tile(3110, 9909);
    public static final Tile WILDERNESS_ENTRANCE_GATE_TILE = new Tile(3132, 9911);
    public static final Tile WILDERNESS_TUNNEL_GATE_TILE = new Tile(3107, 9942);
    public static final Tile WILDERNESS_TUNNEL_GATE_EXIT_TILE = new Tile(3105, 9948);
    public static final Tile LADDER_TILE = new Tile(3088, 9970);
    public static final Tile BANK_TILE = new Tile(3369, 3169);
    public static final Tile PORTAL_TILE = new Tile(3353, 3163);

    public static final Filter<Item> AMULET_OF_GLORY_FILTER = new Filter<Item>() {
        @Override
        public boolean match(final Item item) {
            return item != null && item.getName() != null && item.getName().startsWith("Amulet of glory(");
        }
    };

    public static final Filter<Item> RING_OF_DUELING_FILTER = new Filter<Item>() {
        @Override
        public boolean match(final Item item) {
            return item != null && item.getName() != null && item.getName().startsWith("Ring of dueling(");
        }
    };

    // cba to pass instance around
    public static boolean HOUSE = true;

    private int airOrbs = 0;

    @Override
    public void onStart() {
        addNodes(new HouseTaskNode(), new TrapdoorTaskNode(), new TunnelGateTaskNode(), new WildernessEntranceGateTaskNode(),
                new WildernessTunnelGateTaskNode(), new LadderTaskNode(), new ObeliskTaskNode(),
                new BankNode(), new PortalTaskNode(), new DeathTaskNode());
    }

    @Override
    public void onPaint(final Graphics g) {
        g.drawString(getLastTaskNode() != null ? getLastTaskNode().getClass().getSimpleName() : "", 16, 32);
        g.drawString("Air orbs: " + airOrbs, 16, 48);
        g.drawString("Air orbs/h: " + getPerHour(airOrbs), 16, 64);
        g.drawString("Profit: " + (airOrbs * 900), 16, 80);
        g.drawString("Profit/h: " + getPerHour(airOrbs * 900), 16, 96);
    }

    @Override
    public void onItemChange(final Item[] items) {
        for (final Item item : items) {
            if (item != null && item.getName() != null && item.getName().equals("Air orb") && item.getAmount() > 0)
                airOrbs += item.getAmount();
        }
    }

    private long startTime = System.currentTimeMillis(); // TODO when started

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
