package me.rabrg.miner;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;

@ScriptManifest(category = Category.MINING, name = "Rabrg Miner", author = "Rabrg", version = 1.0)
public final class RabrgMiner extends AbstractScript implements InventoryListener, PaintListener {

    private enum State {
        MINE,
        DROP,
        HOP,
        BANK,
        WALK,
        NOTHING
    }

    private enum Rock {
        GEM_ROCK(7463, 7464);

        private final Integer[] ids;

        Rock(final Integer... ids) {
            this.ids = ids;
        }

        public Integer[] getIds() {
            return ids;
        }
    }

    private Rock rock = Rock.GEM_ROCK;
    private Tile initialTile;
    private GameObject currentRock;

    @Override
    public int onLoop() {
        if (initialTile == null && getClient().isLoggedIn())
            initialTile = getLocalPlayer().getTile(); // not in onStart for if started while logged out
        switch (getState()) {
            case MINE:
                if (currentRock.interact("Mine")) {
                    sleepUntil(() -> !currentRock.exists(), Calculations.random(1800, 2400));
                    sleepUntil(() -> !currentRock.exists() || !getLocalPlayer().isAnimating(),
                            Calculations.random(7200, 12000));
                }
                break;
            case DROP:
                if (getInventory().dropAll("Uncut opal"))
                    sleepUntil(() -> !getInventory().contains("Uncut opal"), 1200);
                break;
            case HOP:
                final int world = hoppableWorld().getID();
                if (getWorldHopper().quickHop(world)) {
                    sleepUntil(() -> getClient().getCurrentWorld() == world, 3000);
                    sleep(2400, 3000);
                }
                break;
            case BANK:
                if (!getBank().isOpen() && getBank().openClosest())
                    sleepUntil(() -> getBank().isOpen(), Calculations.random(300, 2400));
                else if (getBank().isOpen() && !getInventory().isEmpty() && getBank().depositAllItems())
                    sleepUntil(() -> getInventory().isEmpty(), 1200);
                break;
            case WALK:
                if (getWalking().walk(initialTile))
                    sleep(300, 2400);
                break;
        }
        return Calculations.random(0, 900);
    }

    private State getState() {
        if (!getInventory().isFull() && initialTile.distance() < 15
                && (currentRock = getGameObjects().closest(rock.getIds())) != null)
            return State.MINE;
//        else if (getInventory().isFull() && getInventory().contains("Uncut opal"))
//            return State.DROP;
        else if (!getInventory().isFull() && initialTile.distance() < 15 && (currentRock == null || playerMining()))
            return State.HOP;
        else if (getInventory().isFull())
            return State.BANK;
        else if (!getInventory().isFull() && initialTile.distance() >= 15)
            return State.WALK;
        return State.NOTHING;
    }

    @Override
    public void onItemChange(final Item[] items) {

    }

    @Override
    public void onPaint(final Graphics graphics) {

    }

    // world
    private boolean validWorld(final World world) {
        return !world.isDeadmanMode() && !world.isF2P() && !world.isPVP() && world.getMinimumLevel() < 1
                && world.getID() != getClient().getCurrentWorld() && world.getRealID() != getClient().getCurrentWorld();
    }

    private World hoppableWorld() {
        return getWorlds().getRandomWorld(this::validWorld);
    }

    // player
    private boolean playerMining() {
        return getPlayers().all((player) -> !player.getName().equals(getLocalPlayer().getName())
                && player.isAnimating()).size() > 0;
    }
}
