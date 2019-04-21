package me.rabrg.aiomake;

import org.dreambot.api.methods.MethodProvider;

public enum GEItem {
    CHOCOLATE_BAR("Chocolate bar", 1973),
    CHOCOLATE_DUST("Chocolate dust", 1975),
    UNICORN_HORN("Unicorn horn", 237),
    UNICORN_DUST("Unicorn horn dust", 235),

    LOGS("Logs", 1511),
    OAK_LOGS("Oak logs", 1521),
    WILLOW_LOGS("Willow logs", 1519),
    MAPLE_LOGS("Maple logs", 1517),
    YEW_LOGS("Yew logs", 1515),
    MAGIC_LOGS("Magic logs", 1513),

    NORMAL_LONGBOW_U("Longbow (u)", 49),
    OAK_LONGBOW_U("Oak longbow (u)", 56),
    WILLOW_LONGBOW_U("Willow longbow (u)", 58),
    MAPLE_LONGBOW_U("Maple longbow (u)", 62),
    YEW_LONGBOW_U("Yew longbow (u)", 66),
    MAGIC_LONGBOW_U("Magic longbow (u)", 70),

    NORMAL_LONGBOW("Longbow", 839),
    OAK_LONGBOW("Oak longbow", 845),
    WILLOW_LONGBOW("Willow longbow", 847),
    MAPLE_LONGBOW("Maple longbow", 851),
    YEW_LONGBOW("Yew longbow", 855),
    MAGIC_LONGBOW("Magic longbow", 859),

    GRIMY_GUAM("Grimy guam leaf", 199),
    GRIMY_MARRENTILL("Grimy marrentill", 201),
    GRIMY_TARROMIN("Grimy tarromin", 203),
    GRIMY_HARRALANDER("Grimy harralander", 205),
    GRIMY_RANARR_WEED("Grimy ranarr weed", 207),
    GRIMY_TOADFLAX("Grimy toadflax weed", 3049),
    GRIMY_IRIT_LEAF("Grimy irit leaf", 209),
    GRIMY_AVANTOE("Grimy avantoe", 211),
    GRIMY_KWUARM("Grimy kwuarm", 213),
    GRIMY_SNAPDRAGON("Grimy snapdragon", 3051),
    GRIMY_CADANTINE("Grimy cadantine", 215),
    GRIMY_LANTADYME("Grimy lantadyme", 2485),
    GRIMY_DWARF_WEED("Grimy dwarf weed", 217),
    GRIMY_TORSTOL("Grimy torstol", 219),

    GUAM("Guam leaf", 249),
    MARRENTILL("Marrentill", 251),
    TARROMIN("Tarromin", 253),
    HARRALANDER("Harralander", 255),
    RANARR_WEED("Ranarr weed", 257),
    TOADFLAX("Toadflax weed", 2998),
    IRIT_LEAF("Irit leaf", 259),
    AVANTOE("Avantoe", 261),
    KWUARM("Kwuarm", 263),
    SNAPDRAGON("Snapdragon", 3000),
    CADANTINE("Cadantine", 265),
    LANTADYME("Lantadyme", 2481),
    DWARF_WEED("Dwarf weed", 267),
    TORSTOL("Torstol", 269),

    GUAM_POTION_UNF("Guam potion (unf)", 91),
    MARRENTILL_POTION_UNF("Marrentill potion (unf)", 93),
    TARROMIN_POTION_UNF("Tarromi potion (unf)n", 95),
    HARRALANDER_POTION_UNF("Harralander potion (unf)", 97),
    RANARR_WEED_POTION_UNF("Ranarr potion (unf)", 99),
    TOADFLAX_POTION_UNF("Toadflax weed potion (unf)", 3002),
    IRIT_LEAF_POTION_UNF("Irit leaf potion (unf)", 101),
    AVANTOE_POTION_UNF("Avantoe potion (unf)", 103),
    KWUARM_POTION_UNF("Kwuarm potion (unf)", 105),
    SNAPDRAGON_POTION_UNF("Snapdragon potion (unf)", 3004),
    CADANTINE_POTION_UNF("Cadantine potion (unf)", 107),
    LANTADYME_POTION_UNF("Lantadyme potion (unf)", 2483),
    DWARF_WEED_POTION_UNF("Dwarf weed potion (unf)", 109),
    TORSTOL_POTION_UNF("Torstol potion (unf)", 111),

    VIAL_OF_WATER("Vial of water", 227),

    BOW_STRING("Bow string", 1777),

    PLAIN_PIZZA("Plain pizza", 2289),
    ANCHOVIES("Anchovies", 319),
    ANCHOVY_PIZZA("Anchovy pizza", 2297),
    COOKED_MEAT("Cooked meat", 2142),
    MEAT_PIZZA("Meat pizza", 2293),
    PINEAPPLE_CHUNKS("Pineapple chunks", 2116),
    PINEAPPLE_PIZZA("Pineapple pizza", 2301),

    BUTTER_POTATO("Potato with butter", 6703),
    TUNA_AND_CORN("Tuna and corn", 7068),
    TUNA_POTATO("Tuna potato", 7060);

    public final String name;
    public final int id;
    private int price;

    GEItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getPrice() {
        if (price == 0)
            price = RSBuddyGrandExchange.getPrice(id);
        if (name.equals("Vial of water"))
            return 10;
        return price;
    }
}
