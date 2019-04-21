package me.rabrg.merch.model;

public final class ItemHistory {

	private long ts;
	private int buyingPrice;
	private int buyingCompleted;
	private int sellingPrice;
	private int sellingCompleted;
	private int overallPrice;
	private int overallCompleted;

	public long getTimeStamp() {
		return ts / 1000;
	}

	public int getBuyingPrice() {
		return buyingPrice;
	}

	public int getBuyingCompleted() {
		return buyingCompleted;
	}

	public int getSellingPrice() {
		return sellingPrice;
	}

	public int getSellingCompleted() {
		return sellingCompleted;
	}

	public int getOverallPrice() {
		return overallPrice;
	}

	public int getOverallCompleted() {
		return overallCompleted;
	}
}
