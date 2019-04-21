package me.rabrg.duel.kit;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public interface Kit {

	public String getName();

	public PlayerInventory getPlayerInventory(final PlayerInventory inventory);

	public PotionEffect[] getPotionEffects();

	public KitEffect[] getKitEffects();
}
