package me.rabrg.unknown.kit;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;

public interface Kit {

	public Permission getPermission();

	public Material getIcon();

	public String getName();

	public String getDescription();

	public ItemStack[] getArmour();

	public Inventory getInventory(final Inventory inventory);

	public int getSoupCount();

	public PotionEffect[] getPotionEffects();
}
