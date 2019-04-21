package me.rabrg.lavadrag;

import org.dreambot.api.input.event.impl.InteractionEvent;
import org.dreambot.api.input.event.impl.InteractionSetting;
import org.dreambot.api.input.mouse.destination.impl.EntityDestination;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.GroundItem;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@ScriptManifest(category = Category.MONEYMAKING, name = "Lava Dragon", author = "Rabrg", version = 1)
public class LavaDragon extends AbstractScript implements PaintListener {

    private static final Tile STAND_TILE = new Tile(3212, 3852);

    private static final List<String> LOOT_LIST = Arrays.asList("Lava dragon bones", "Black dragonhide", "Lava scale",
            "Rune dart", "Rune javelin", "Rune knife", "Runite bolts", "Death rune", "Blood rune", "Law rune",
            "Onyx bolt tips", "Dragon javelin heads", "Rune longsword", "Lava battlestaff", "Rune axe",
            "Adamant platebody", "Rune kiteshield", "Rune med helm", "Rune full helm", "Grimy ranarr weed",
             "Fire orb", "Draconic visage", "Looting bag", "Adamantite bar", "Dragon spear", "Shield left half",
            "Dragon med helm", "Dragonstone", "rune sq shield", "Rune 2h sword", "Rune battleaxe", "Rune spear",
            "Nature rune", "Runite bar", "Loop half of key", "Tooth half of key");

    private enum State {
        CHANGE_WORLD,
        LOOT,
        MOVE_TILE,
        GRIND_SCALE,
        ATTACK_DRAGON,
        NOTHING
    }

    private NPC dragon;
    private GroundItem loot;

    @Override
    public int onLoop() {
        switch (getState()) {
            case CHANGE_WORLD:
                final int world = hoppableWorld().getID();
                log("Hopping from " + getClient().getCurrentWorld() + " to " + world);
                if (getWorldHopper().quickHop(world)) {
                    sleepUntil(() -> getClient().getCurrentWorld() == world, 4200);
                    sleep(300); // sleep additional to allow player list update
                }
                break;
            case LOOT:
                if (!loot.isOnScreen() && getCamera().mouseRotateToEntity(loot))
                    sleepUntil(() -> loot.isOnScreen(), 1200);
                else if (loot.isOnScreen() && loot.interact("Take"))
                    sleepUntil(() -> !loot.exists(), Calculations.random(1200, 1800));
                break;
            case MOVE_TILE:
                if (getWalking().walk(STAND_TILE))
                    sleepUntil(() -> getLocalPlayer().getTile().equals(STAND_TILE), Calculations.random(150, 900));
                break;
            case GRIND_SCALE:
                if (getInventory().isItemSelected())
                    getInventory().deselect();
                else if (getInventory().interact("Pestle and mortar", "Use")
                        && getInventory().interact("Lava scale", "Use"))
                    sleepUntil(() -> !getInventory().contains("Lava scale"), 12000);
                break;
            case ATTACK_DRAGON:
                final EntityDestination destination = new EntityDestination(getClient(),dragon);
                final InteractionEvent event = new InteractionEvent(destination);
                if (!dragon.isOnScreen() && getCamera().mouseRotateToEntity(dragon))
                    sleepUntil(() -> dragon.isOnScreen(), 1200);
                else if (dragon.isOnScreen() && event.interact("Attack", InteractionSetting.EMPTY_SETTING))
                    sleepUntil(() -> getLocalPlayer().getInteractingCharacter() != null, 600);
                break;
        }
        return Calculations.random(0, 300);
    }

    private State getState() {
        if (getCombat().isInWild() && shouldHop())
            return State.CHANGE_WORLD;
        else if ((loot = loot()) != null && !getInventory().isFull())
            return State.LOOT;
        else if (STAND_TILE.distance() < 12 && getMap().canReach(STAND_TILE)
                && !getLocalPlayer().getTile().equals(STAND_TILE))
            return State.MOVE_TILE;
        else if (getInventory().contains("Lava scale"))
            return State.GRIND_SCALE;
        else if ((getLocalPlayer().getInteractingCharacter() == null || getDialogues().canContinue())
                && (dragon = dragon()) != null)
            return State.ATTACK_DRAGON;
        return State.NOTHING;
    }

    @Override
    public void onPaint(final Graphics g) {
        if (loot != null)
            getClient().getViewportTools().drawModel((Graphics2D) g, loot.getGridX(), loot.getGridY(), loot.getZ(), loot.getModel());
    }

    // wilderness level
    private int getWildernessLevel() {
        final int api = getCombat().getWildernessLevel();
        return api == 0 ? Integer.parseInt(getWidgets().getWidgetChild(90, 27).getText().split(" ")[1]) : api;
    }

    // player verification
    private boolean shouldHop() {
        return getPlayers().all(this::validPlayer).size() > 1; // local player = 1
    }

    private boolean validPlayer(final Player other) {
        final int level = other.getLevel();
        return level >= minLevel() && level <= maxLevel() && level > 15;
    }

    private int minLevel() {
        return getLocalPlayer().getLevel() - getWildernessLevel();
    }

    private int maxLevel() {
        return getLocalPlayer().getLevel() + getWildernessLevel();
    }

    // world
    private boolean validWorld(final World world) {
        return !world.isDeadmanMode() && !world.isF2P() && !world.isPVP() && world.getMinimumLevel() < 1
                && world.getID() != getClient().getCurrentWorld() && world.getRealID() != getClient().getCurrentWorld();
    }

    private World hoppableWorld() {
        return getWorlds().getRandomWorld(this::validWorld);
    }

    // dragon
    private NPC dragon() {
        return getNpcs().closest((npc) -> npc != null && "Lava dragon".equals(npc.getName())
                && npc.distance(STAND_TILE) < 12);
    }

    // loot
    private GroundItem loot() {
        return getGroundItems().closest((item) -> item != null && LOOT_LIST.contains(item.getName())
                && item.distance() < 12);
    }
}
