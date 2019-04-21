package com.github.pfacheris.BukkitDuel.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class Stakes {

	private int wagerMoneyAmount;
	private final Inventory wagerItems;
	private int wagerExperienceAmount;
	private int wagerPowerAmount;

	public Stakes(final Player player) {
		this.wagerMoneyAmount = 0;
		this.wagerItems = Bukkit.createInventory(player, InventoryType.CHEST);
		this.wagerExperienceAmount = 0;
		this.wagerPowerAmount = 0;
	}

	public int getWagerMoneyAmount() {
		return this.wagerMoneyAmount;
	}

	public void setWagerMoneyAmount(final int newAmount) {
		this.wagerMoneyAmount = newAmount;
	}

	public Inventory getWagerItems() {
		return wagerItems;
	}

	public int getWagerExperienceAmount() {
		return this.wagerExperienceAmount;
	}

	public void setWagerExperienceAmount(final int newAmount) {
		this.wagerExperienceAmount = newAmount;
	}

	public int getWagerPowerAmount() {
		return this.wagerPowerAmount;
	}

	public void setWagerPowerAmount(final int newAmount) {
		this.wagerPowerAmount = newAmount;
	}

}
