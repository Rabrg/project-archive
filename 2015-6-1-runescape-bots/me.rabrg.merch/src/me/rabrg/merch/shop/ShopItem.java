package me.rabrg.merch.shop;

import me.rabrg.merch.Data;

public class ShopItem implements Comparable<ShopItem> {

	public String shopName;
	public String name;
	public int cost, amount;
	public int profit;

	@Override
	public int compareTo(ShopItem o) {
		int thisDif = 0;
		int otherDif = 0;
		for (final Data data : ShopFinder.prices) {
			if (data.name.equals(name)) {
				thisDif = data.buy - cost;
				profit = thisDif;
			}
			if (data.name.equals(o.name)) {
				otherDif = data.buy - o.cost;
			}
		}
		if (thisDif > otherDif) {
			return 1;
		} else if (thisDif < otherDif) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "profit: " + profit + " shop name: " + shopName + " item name: " + name + " cost: " + cost + " amount: " + amount;
	}
}
