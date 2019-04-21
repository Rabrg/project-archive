package me.rabrg.plankpicker;

import me.rabrg.plankpicker.node.*;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.friend.Friend;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.awt.*;
import java.io.*;

@ScriptManifest(category = Category.MONEYMAKING, name = "Rabrg Plank", author = "Rabrg", version = 1.0)
public class PlankPicker extends TaskScript implements PaintListener, InventoryListener, MessageListener {

    public static final Area PLANK_AREA = new Area(2548, 3573, 2556, 3578);
    public static final Area BANK_AREA = new Area(2531, 3570, 2537, 3577);

    private int currentWorld = 0;
    private int[] worlds;
    private boolean mule;
    private String rawWorlds;
    private int min, max;

    private int planks;

    @Override
    public void onStart() {
        getRandomManager().disableSolver(RandomEvent.LOGIN);
        log(System.getProperty("scripts.path"));
        final PlankPickerGUIForm gui = new PlankPickerGUIForm(this);
        gui.start();
        while (gui.isVisible())
            sleep(100, 200);

        if (!mule) {
            addNodes(new GiveTaskNode(), new AgilityTaskNode(), new GatherPlankTaskNode(this), new BankPlankTaskNode());
            getRandomManager().enableSolver(RandomEvent.LOGIN);
        } else {

            addNodes(new CollectorTaskNode(this));
            sleep(getSleep());
        }
    }

    void setup(final boolean mule, final int[] worlds, final String rawWorlds, final int min, final int max) {
        this.mule = mule;
        this.worlds = worlds;
        this.rawWorlds = rawWorlds;
        this.min = min;
        this.max = max;
        saveWorlds();
    }

    @Override
    public void onPaint(final Graphics g) {
        g.drawString(getLastTaskNode().getClass().getSimpleName(), 15, 50);
        g.drawString("Planks\t: " + planks, 15, 65);
        g.drawString("Planks/h\t: " + getPerHour(planks), 15, 80);
        g.drawString("Profit\t: " + planks * 428, 15, 95);
        g.drawString("Profit/h\t: " + getPerHour(planks * 428), 15, 110);

        if (mule) {
            g.drawString("Wake/h\t: " + Timer.formatTime(wake), 15, 110);
        }
    }

    @Override
    public void onItemChange(Item[] items) {
        for (final Item item : items) {
            if (item != null && "Plank".equals(item.getName()))
                if (item.getAmount() == 1 && !mule)
                    planks += item.getAmount();
                else if (item.getAmount() > 1 && mule)
                    planks += item.getAmount();
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

    String loadWorlds() {
        final StringBuilder builder = new StringBuilder();
        try {
            final File file = new File(System.getProperty("scripts.path") + "plank_worlds.txt");
            if(!file.exists())
                file.createNewFile();
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null)
                builder.append(line + '\n');
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    void saveWorlds() {
        try {
            final File file = new File(System.getProperty("scripts.path") + "plank_worlds.txt");
            if(!file.exists())
                file.createNewFile();
            final BufferedWriter reader = new BufferedWriter(new FileWriter(file));
            reader.write(rawWorlds);
            reader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public int next() {
        int value = worlds[currentWorld++];
        if (currentWorld == worlds.length)
            currentWorld = 0;
        return value;
    }

    private int wake;

    public int getSleep() {
        return (wake = Calculations.random(min, max) * 60 * 1000);
    }

    @Override
    public void onGameMessage(Message message) {

    }

    @Override
    public void onPlayerMessage(Message message) {

    }

    @Override
    public void onTradeMessage(Message message) {
        if (!getTrade().isOpen() && message.getMessage().contains(" wishes to trade with you.")) {
            for (final Friend friend : getFriends().getFriends()) {
                if (message.getMessage().contains(friend.getName())) {
                    final Player player = getPlayers().closest(friend.getName());
                    if (player != null && player.interact("Trade with"))
                        break;
                }
            }
        }
    }

    @Override
    public void onPrivateInMessage(Message message) {

    }

    @Override
    public void onPrivateOutMessage(Message message) {

    }
}
