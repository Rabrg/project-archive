package me.rabrg.aiomake;

public class FlipGEItem {

    public final String name;
    public final int id;
    private long lastPrice;
    private int price;
    public int percent;

    FlipGEItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getPrice() {
        if (price == 0 || System.currentTimeMillis() - lastPrice > 300000) {
            price = RSBuddyGrandExchange.getPrice(id);
            lastPrice = System.currentTimeMillis();
        }
        return price;
    }
}
