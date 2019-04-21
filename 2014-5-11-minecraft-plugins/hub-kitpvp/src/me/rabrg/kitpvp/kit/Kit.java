package me.rabrg.kitpvp.kit;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;

public abstract class Kit {

	public abstract String getName();

	public abstract Permission getPermission();

	public abstract PlayerInventory getPlayerInventory(final PlayerInventory inventory);
}
