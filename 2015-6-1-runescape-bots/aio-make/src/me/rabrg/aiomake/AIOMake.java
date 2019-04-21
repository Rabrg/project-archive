package me.rabrg.aiomake;

import org.dreambot.api.methods.friend.Friend;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Locatable;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ScriptManifest(category = Category.MONEYMAKING, name = "Rabrg AIO Make", author = "Rabrg", version = 1.0)
public class AIOMake extends TaskScript implements PaintListener, InventoryListener, MessageListener {

    public static boolean giveMule;
    public static String muleName;
    public static boolean hide;

    public static boolean grandExchange = false;
    public static int amount = 200;
    public static GEItem[] buy;
    public static GEItem sell;

    public static FlipGEItem[] flip;

    public static String bowWidget;

    private AIOMakeGUI gui;

    public static int[] buyUsed;
    public static int goldKeep;
    private int sold;

    private int profit;

    @Override
    public void onStart() {
//        addNodes(new GrandExchangeTaskNode(), new CutLogTaskNode(), new BankCutLogTaskNode());
//        addNodes(new GrandExchangeTaskNode(), new StringBowTaskNode(), new BankStringBowTaskNode());
//        addNodes(new GrandExchangeTaskNode(), new CleanHerbTaskNode(), new BankCleanHerbTaskNode());
//        addNodes(new GrandExchangeTaskNode(), new UnfinishedPotionTaskNode(), new BankUnfinishedPotionTaskNode());
//        addNodes(new GrandExchangeTaskNode(), new ProcessTaskNode(), new BankProcessTaskNode());
//        addNodes(new GrandExchangeTaskNode(), new PizzaPotatoTaskNode(), new BankPizzaPotatoTaskNode());
//        if (giveMule)
//            addNodes(new MuleTaskNode());
        gui = new AIOMakeGUI(this);
        gui.start();
        if (gui.isVisible())
            sleep(100, 200);
    }

    @Override
    public void onPaint(Graphics graphics) {
        if (!hide) {
            graphics.drawString(getLastTaskNode() != null ? getLastTaskNode().getClass().getSimpleName() : "", 15, 30);
            graphics.drawString("Profit: " + profit, 15, 45);
        }
    }

    @Override
    public void onItemChange(Item[] items) {
        if (!hide) {
            for (final Item item : items) {
                if (item != null)
                    log(item.getName());
                if (sell.name.equals(item.getName())) {
                    profit += sell.getPrice() * item.getAmount();
                    for (final GEItem used : buy) {
                        profit -= used.getPrice() * item.getAmount();
                    }
                }
            }
        }
    }

    class LocatableComparator implements Comparator<Locatable> {

        @Override
        public int compare(Locatable o1, Locatable o2) {
            final Tile tile = getLocalPlayer().getTile();
            final double o1Dist = o1.distance(tile);
            final double o2Dist = o1.distance(tile);

            return o1Dist > o2Dist ? 1 : o1Dist < o2Dist ? -1 : 0;
        }
    }

    @Override
    public void onGameMessage(Message message) {
                for (final Friend friend : getFriends().getFriends()) {
                    if (message.getMessage().contains(friend.getName())) {
                        giveMule = true;
                        muleName = message.getMessage().split(" wishes")[0];
                        break;
                    }
                }
    }

    @Override
    public void onPlayerMessage(Message message) {

    }

    @Override
    public void onTradeMessage(Message message) {
        if (message.getMessage().contains(" wishes to trade with you.")) {
            for (final Friend friend : getFriends().getFriends()) {
                if (message.getMessage().contains(friend.getName())) {
                    giveMule = true;
                    muleName = message.getMessage().split(" wishes")[0];
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
