package me.rabrg.ares.kit;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;

public interface Kit {

	public String getName();

	public Permission getPermission();

	public PlayerInventory getPlayerInventory(final PlayerInventory inventory);

	public PotionEffect[] getPotionEffects();

	public KitEffect[] getKitEffects();
}
