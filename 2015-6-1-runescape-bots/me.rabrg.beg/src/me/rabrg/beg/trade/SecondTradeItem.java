package me.rabrg.beg.trade;
public class SecondTradeItem {
	private String name;
	private boolean stackable;
	private int amount;

	public SecondTradeItem(String name) {
		this.name = name;
		this.stackable = false;
		this.amount = 1;
	}

	public SecondTradeItem(String name, int amount) {
		this.name = name;
		this.amount = amount;
		this.stackable = true;
	}

	public String getName() {
		return this.name;
	}

	public int getAmount() {
		return this.amount;
	}

	public boolean isStackable() {
		return this.stackable;
	}

	public String toString() {
		return "SecondWindowTradeItem = [Name: " + name + " ,Amount: " + amount
				+ " ,Stackable: " + stackable + "]";
	}
}