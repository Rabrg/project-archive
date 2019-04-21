package me.rabrg.whiteberry;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;

@ScriptManifest(category = Category.MONEYMAKING, name = "Rabrg Whiterberry", author = "Rabrg", version = 1.0)
public class WhiteBerry extends AbstractScript {

    private static final Area WILD_LEVER_AREA = new Area(3146, 3920, 3164, 3950);
    private static final Tile OUTSIDE_LEVER_TILE = new Tile(3158, 3953);

    private static final Tile DEEP_GATE_TOP_TILE = new Tile(3224, 3905);
    private static final Tile DEEP_GATE_BOTTOM_TILE = new Tile(3224, 3900);

    private static final Tile DRAGON_GATE_TOP = new Tile(3202, 3856);
    private static final Tile DRAGON_GATE_BOTTOM = new Tile(3202, 3853);

    @Override
    public int onLoop() {
        // Deep wild w/o
        if (WILD_LEVER_AREA.contains(getLocalPlayer()) && !getInventory().contains("White berries")) {
            final GameObject web = getGameObjects().closest("Web");
            if (web != null && web.distance() < 8 && web.interact("Slash")) {
                sleepUntil(() -> !web.exists(), Calculations.random(300, 900));
            } else if (web != null && web.distance() >= 8 && getWalking().walk(web)) {
                sleepUntil(() -> web.distance() < 8, Calculations.random(300, 1800));
            } else if (web == null && getWalking().walk(OUTSIDE_LEVER_TILE)) {
                sleepUntil(() -> WILD_LEVER_AREA.contains(getLocalPlayer()), Calculations.random(300, 1800));
            }
        // Deep wild gate w/o
        } else if (getLocalPlayer().getY() > 3903 && !WILD_LEVER_AREA.contains(getLocalPlayer())
                && !getInventory().contains("White berries")) {
            if (DEEP_GATE_TOP_TILE.distance() > 8 && getWalking().walk(DEEP_GATE_TOP_TILE)) {
                sleepUntil(() -> DEEP_GATE_TOP_TILE.distance() <= 8, Calculations.random(300, 1800));
            } else if (DEEP_GATE_TOP_TILE.distance() <= 8) {
                final GameObject gate = getGameObjects().closest("Gate");
                if (gate != null && gate.hasAction("Close") && getWalking().walk(DEEP_GATE_BOTTOM_TILE)) {
                    sleepUntil(() -> getLocalPlayer().getY() <= 3903, Calculations.random(300, 1800));
                } else if (gate != null && gate.interact("Open")) {
                    sleepUntil(() -> !gate.exists(), Calculations.random(600, 1800));
                }
            }
        // Lava dragon gate w/o
        } else if (getLocalPlayer().getY() <= 3903 && getLocalPlayer().getLocalY() >= 3856
                && !getInventory().contains("White berries")) {
            final GameObject gate = getGameObjects().closest("Gate");
            if (gate != null && gate.distance() < 8 && gate.hasAction("Close")
                    && getWalking().walk(DRAGON_GATE_BOTTOM)) {
                sleep(300, 1800);
            } else if (gate != null && gate.distance() < 8 && gate.interact("Open")) {
                sleepUntil(() -> !gate.exists(), Calculations.random(600, 1800));
            } else if (gate == null || gate.distance() >= 8 && getWalking().walk(DRAGON_GATE_TOP)) {
                sleep(300, 1800);
            }
        }
        return Calculations.random(0, 600);
    }
}
