package me.rabrg.beg.trade;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.util.Condition;
import org.tbot.wrappers.WidgetChild;

public class Trade {
    private final static int TRADE_SCREEN_ONE_PARENT = 335;
    private final static int TRADE_SCREEN_ONE_CHILD_MY_ITEMS = 24;
    private final static int TRADE_SCREEN_ONE_CHILD_THEIR_ITEMS = 27;
    private final static int TRADE_SCREEN_ONE_PLAYER_NAME = 8;
    private final static int TRADE_SCREEN_ONE_ACCEPT_BUTTON = 11;

    private final static int TRADE_SCREEN_TWO_PARENT = 334;
    private final static int TRADE_SCREEN_TWO_CHILD_MY_ITEMS = 34; // 39
    private final static int TRADE_SCREEN_TWO_CHILD_THEIR_ITEMS = 35;
    private final static int TRADE_SCREEN_TWO_PLAYER_NAME = 36;
    private final static int TRADE_SCREEN_TWO_ACCEPT_BUTTON = 16;

    /**
     * @return Returns true when the first trade screen is open.
     */

    // 31 and all children
    // mine = 25 and all children

    public static boolean isFirstTradeOpen() {
        WidgetChild wc = Widgets.getWidget(TRADE_SCREEN_ONE_PARENT, TRADE_SCREEN_ONE_CHILD_MY_ITEMS);
        return wc != null && wc.isValid() && wc.isVisible();
    }

    /**
     * @return Returns true when the second trade screen is open.
     */

    public static boolean isSecondTradeOpen() {
        WidgetChild wc = Widgets.getWidget(TRADE_SCREEN_TWO_PARENT, TRADE_SCREEN_TWO_CHILD_THEIR_ITEMS);
        return wc != null && wc.isValid() && wc.isVisible();
    }

    /**
     * @return Returns a list of trade items from the first trade screen of the
     *         right hand side.
     */

    public static List<TradeItem> getPartnerItems() {
        List<TradeItem> list = new ArrayList<TradeItem>();
        if (isFirstTradeOpen()) {
            WidgetChild parent = Widgets.getWidget(TRADE_SCREEN_ONE_PARENT, TRADE_SCREEN_ONE_CHILD_THEIR_ITEMS);
            if (parent != null && parent.isValid() && parent.isVisible()) {
                for (int i = 0; i < parent.getChildren().length - 1; i++) {
                    WidgetChild grandchild = parent.getChild(i);
                    if (grandchild != null) {
                        if (grandchild.getItemID() > 0) {
                            list.add(new TradeItem(grandchild.getItemID(), grandchild.getItemStackSize(), i, grandchild));
                        }
                    }

                }
            }
        }
        return list;
    }

    /**
     * @return Returns a list of trade items from the first trade screen of the
     *         left hand side.
     */

    public static List<TradeItem> getLocalItems() {
        List<TradeItem> list = new ArrayList<TradeItem>();
        if (isFirstTradeOpen()) {
            WidgetChild parent = Widgets.getWidget(TRADE_SCREEN_ONE_PARENT, TRADE_SCREEN_ONE_CHILD_MY_ITEMS);
            if (parent != null && parent.isValid() && parent.isVisible()) {
                for (int i = 0; i < parent.getChildren().length - 1; i++) {
                    WidgetChild grandchild = parent.getChild(i);
                    if (grandchild != null) {
                        if (grandchild.getItemID() > 0) {
                            list.add(new TradeItem(grandchild.getItemID(), grandchild.getItemStackSize(), i, grandchild));
                        }
                    }

                }
            }
        }
        return list;
    }

    /**
     * @return Returns The name of the player who you are in with either first
     *         or second trade screen.
     */

    public static String getTradePartnerName() {
        if (isFirstTradeOpen()) {
            WidgetChild wc = Widgets.getWidget(TRADE_SCREEN_ONE_PARENT, TRADE_SCREEN_ONE_PLAYER_NAME);
            if (wc != null && wc.getText() != null) {
                String text = wc.getText();
                if (text != null)
                    return (String) text.split("has [a-z0-9_-]{0,2} free inventory slots.")[0].subSequence(0, text.split("has [a-z0-9_-]{0,2} free inventory slots.")[0].length() - 1);
            }
        } else if (isSecondTradeOpen()) {
            WidgetChild wc = Widgets.getWidget(TRADE_SCREEN_TWO_PARENT, TRADE_SCREEN_TWO_PLAYER_NAME);
            if (wc != null) {
                String text = wc.getText();
                if (text != null)
                    return text.split("<br>")[1];
            }
        }
        return null;
    }

    /**
     * @return Returns True when you have accepted the first screen.
     */

    public static boolean hasAcceptedFirstScreen() {
        if (isFirstTradeOpen()) {
            WidgetChild wc = Widgets.getWidget(TRADE_SCREEN_ONE_PARENT, 56);
            if (wc != null && wc.getText() != null) {
                return wc.getText().equalsIgnoreCase("Waiting for other player...");
            }
        }
        return false;
    }

    /**
     * @return Returns True when you have accepted the second screen.
     */

    public static boolean hasAcceptedSecondScreen() {
        if (isSecondTradeOpen()) {
            WidgetChild wc = Widgets.getWidget(TRADE_SCREEN_TWO_PARENT, 33);
            if (wc != null && wc.getText() != null) {
                return wc.getText().equalsIgnoreCase("Waiting for other player...");
            }
        }
        return false;
    }

    public static boolean isOpen() {
        return isFirstTradeOpen() || isSecondTradeOpen();
    }

    /**
     * @return Returns True when your partner has accepted the first screen.
     */

    public static boolean partnerHasAcceptedFirstScreen() throws InterruptedException {
        if (isFirstTradeOpen()) {
            WidgetChild wc = Widgets.getWidget(TRADE_SCREEN_ONE_PARENT, 56);
            if (wc != null && wc.getText() != null) {
                return wc.getText().equalsIgnoreCase("Other player has accepted.");
            }
        }
        return false;
    }

    /**
     * @return Returns True when your partner has accepted the second screen.
     */

    public static boolean partnerHasAcceptedSecondScreen() {
        if (isSecondTradeOpen()) {
            WidgetChild wc = Widgets.getWidget(TRADE_SCREEN_TWO_PARENT, 33);
            if (wc != null && wc.getText() != null) {
                return wc.getText().equalsIgnoreCase("Other player has accepted.");
            }
        }
        return false;
    }

    /**
     * @return Returns a list of trade items from the second trade screen of the
     *         right hand side.
     */

    public static List<SecondTradeItem> getPartnersSecondTradeItems() throws ParseException {
        String text = getRawSecondaryItemText(true);
        ArrayList<SecondTradeItem> list = new ArrayList<SecondTradeItem>();
        if (text == null || !text.contains("<br>")) {
            return list;
        }

        String items[] = text.split("<br>");
        for (String s : items) {
            String[] itemData = s.split("<col=[0-9A-Fa-f]{6}>");
            if (itemData.length == 1) {
                list.add(new SecondTradeItem(itemData[0]));

            } else if (itemData.length == 3) {
                list.add(new SecondTradeItem(itemData[0], parseQuantity(itemData[2])));

            } else {
                try {
                    throw new Exception("Sorry please check the item");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return list;
    }

    /**
     * @return Returns a list of trade items from the second trade screen of the
     *         left hand side.
     */

    public static List<SecondTradeItem> getLocalSecondTradeItems() throws ParseException {
        String text = getRawSecondaryItemText(false);
        ArrayList<SecondTradeItem> list = new ArrayList<SecondTradeItem>();
        if (text == null || !text.contains("<br>")) {
            System.out.println("No line breaks check text: " + text);
            return list;
        }

        String items[] = text.split("<br>");
        for (String s : items) {
            String[] itemData = s.split("<col=[0-9A-Fa-f]{6}>");
            if (itemData.length == 1) {
                list.add(new SecondTradeItem(itemData[0]));

            } else if (itemData.length == 3) {
                list.add(new SecondTradeItem(itemData[0], parseQuantity(itemData[2])));

            } else {
                try {
                    throw new Exception("Sorry please check the item");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return list;
    }

    /**
     * @param partner
     *            refers to the left or the right of the trade screen right
     *            being true.
     * @return Returns trade widgets text concatenated.
     */

    private static String getRawSecondaryItemText(boolean partner) {
        String text = "";
        if (isSecondTradeOpen()) {
            WidgetChild wc = partner ? Widgets.getWidget(TRADE_SCREEN_TWO_CHILD_THEIR_ITEMS).getChild(TRADE_SCREEN_TWO_CHILD_MY_ITEMS) : Widgets.getWidget(TRADE_SCREEN_TWO_PARENT).getChild(TRADE_SCREEN_TWO_CHILD_MY_ITEMS);
            if (wc != null && wc.isVisible()) {
                for (WidgetChild w : wc.getChildren()) {
                    if (w != null && w.getText() != null) {
                        text += w.getText();
                        text += "<br>";
                        // fk do i need to add new line into my shit now...
                    }
                }
            }
            return text;
        }
        return text;
    }

    /**
     * @return Returns a parsed integer from the second trade window.
     */
    private static int parseQuantity(String text) throws ParseException {
        if (text == null) {
            return -1;
        }

        if (text.contains("(") && text.contains(")")) {
            text = text.substring(text.indexOf('(') + 1, text.indexOf(')'));
            NumberFormat.getIntegerInstance(java.util.Locale.US).parse(text).intValue();
        }

        return NumberFormat.getIntegerInstance(java.util.Locale.US).parse(text).intValue();
    }

    public static boolean acceptFirstScreen() {
        if (Trade.isFirstTradeOpen()) {
            WidgetChild wc = Widgets.getWidget(TRADE_SCREEN_ONE_PARENT, TRADE_SCREEN_ONE_ACCEPT_BUTTON);
            if (wc != null && !hasAcceptedFirstScreen()) {
                if (wc.click()) {
                    Time.sleepUntil(new Condition() {

                        @Override
                        public boolean check() {
                            // TODO Auto-generated method stub
                            return Trade.isSecondTradeOpen();
                        }
                    }, 900);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean acceptSecondScreen() {
        if (Trade.isSecondTradeOpen()) {
            WidgetChild wc = Widgets.getWidget(TRADE_SCREEN_TWO_PARENT, TRADE_SCREEN_TWO_ACCEPT_BUTTON);
            if (wc != null && !hasAcceptedSecondScreen()) {
                return wc.click();
            }
        }
        return false;
    }
}