package me.rabrg.itemfinder;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.friend.Friend;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.Player;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@ScriptManifest(category = Category.MISC, name = "Item Finder", author = "Rabrg", version = 1.0,
        description = "Remember to contact ryanpizzas on Skype for more private scripts :)")
public class ItemFinderScript extends AbstractScript implements PaintListener {

    private int hopDelay;
    private int[] itemIds;

    private ItemFinderForm form;

    private long lastHop;
    private int players;

    private FileWriter writer;

    private String[] names = new String[13694];

    @Override
    public void onStart() {
        form = new ItemFinderForm(this);
        form.start();

        try {
            writer = new FileWriter(System.getProperty("user.home") + "/item-finder-log.txt", true);

            try (final BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home") + "/items.txt"))) { // TODO: remember to give items.txt
                String line;
                while ((line = reader.readLine()) != null) {
                    names[Integer.parseInt(line.split("ID: ")[1].split(",")[0])] = line.split("Name: ")[1].split(",")[0];
                }
            }
        } catch (final IOException e) {
            log("1e " + e.getMessage());
        }
    }

    void set(final int hopDelay, final int[] itemIds) {
        this.hopDelay = hopDelay;
        this.itemIds = itemIds;
        resetTimer();
    }

    @Override
    public int onLoop() {
        if (form != null && form.isVisible())
            return Calculations.random(128, 256);
        if (System.currentTimeMillis() - hopDelay > lastHop) {
            if (getWorldHopper().quickHop(getWorlds().getRandomWorld(new Filter<World>() {
                @Override
                public boolean match(final World world) {
                    return !world.isHighRisk() && !world.isF2P() && !world.isPVP() && !world.isDeadmanMode() && world.getMinimumLevel() <= 0;
                }
            }).getID())) {
                resetTimer();
            }
        } else {
            for (final Player player : getPlayers().all()) {
                if (player != null && player.getName() != null
                        && !player.getName().equals(getLocalPlayer().getName())) {
                    for (int i : player.getComposite().getApperance()) {
                        for (int j : itemIds) {
                            if (i == j + 512) {
                                if (!getFriends().haveFriend(player.getName()) && getFriends().addFriend(player)) {
                                    sleep(2000);
                                    try {
                                        writer.write("Name: " + player.getName() + '\n');
                                        final Friend friend = getFriends().getFriend(player.getName());
                                        if (friend != null && friend.getOtherName() != null
                                                && !friend.getOtherName().equals(""))
                                            writer.write("Other name: " + friend.getOtherName() + '\n');
                                        writer.write("World: " + getClient().getCurrentWorld() + '\n');
                                        writer.write("Timestamp: " + ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME) + '\n');
                                        for (int k : player.getComposite().getApperance()) {
                                            try {
                                                String name = getName(k - 512);
                                                if (name != null) {
                                                    writer.write("Wearing: " + name + '\n');
                                                }
                                            } catch (final Exception e) {
                                                log("3e " + e.getMessage());
                                            }
                                        }
                                        writer.write("-----\n");
                                        writer.flush();
                                    } catch (final IOException e) {
                                        log("4e " + e.getMessage());
                                    }
                                    players++;
                                }
                                sleep(1200, 2400);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return Calculations.random(600, 2400);
    }

    @Override
    public void onPaint(final Graphics g) {
        g.drawString("Players found: " + players, 6, 322);
        g.drawString("Next hop: " + Timer.formatTime(hopDelay - (System.currentTimeMillis() - lastHop)), 6, 334);
    }

    @Override
    public void stop() {
        super.stop();
        try {
            writer.close();
        } catch (final IOException e) {

        }
    }
    private void resetTimer() {
        lastHop = System.currentTimeMillis();
    }

    private String getName(final int id) {
        return names[id];
    }
}
