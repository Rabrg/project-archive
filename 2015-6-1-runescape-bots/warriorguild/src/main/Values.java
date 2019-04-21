package main;

import org.dreambot.api.methods.*;
import org.dreambot.api.utilities.*;
import org.dreambot.api.*;

public class Values extends MethodContext
{
    public static final String POTION_SHOP_OWNER = "Lilly";
    public static final String FOOD_SHOP_OWNER = "Lidio";
    public static final String SHOP_INTERACTION = "Trade";
    public static final String COINS_NAME = "Coins";
    public static String BANKER_NAME;
    public static final String FOOD_1_NAME = "Plain pizza";
    public static final String FOOD_2_NAME = "Potato with cheese";
    public static final String POTION_1_NAME = "Strength potion(3)";
    public static String HAS_BANKPIN;
    public static final String POTION_SHOP_TITLE = "Warrior Guild Potion Shop";
    public static final String FOOD_SHOP_TITLE = "Warrior Guild Food Shop";
    public static final Timer timer;
    public static String USER_SETTINGS;
    
    static {
        Values.BANKER_NAME = "Banker";
        Values.HAS_BANKPIN = "false";
        timer = new Timer();
        Values.USER_SETTINGS = "default";
    }
    
    public static void LoadObjectData(final Client aObject1) {
        Values.BANKER_NAME = aObject1.getUsername();
    }
    
    public static final String GetShopForOwner(final String owner) {
        String shop = "";
        if (owner == "Lilly") {
            shop = "Warrior Guild Potion Shop";
        }
        else if (owner == "Lidio") {
            shop = "Warrior Guild Food Shop";
        }
        return shop;
    }
}
