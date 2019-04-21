package me.rabrg.merch.model;

public final class ItemPriceDif implements Comparable<ItemPriceDif>{

	public String name;
	public int days;
	public double difference;
	
	@Override
	public int compareTo(ItemPriceDif o) {
		if (difference > o.difference)
			return 1;
		else if (difference < o.difference)
			return -1;
		return 0;
	}

	@Override
	public String toString() {
		return "name: " + name + " difference: " + difference + " days:" + days;
	}
}
