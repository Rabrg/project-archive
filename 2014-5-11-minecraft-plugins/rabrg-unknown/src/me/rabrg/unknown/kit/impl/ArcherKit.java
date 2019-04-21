package me.rabrg.unknown.kit.impl;

import me.rabrg.unknown.kit.Kit;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class ArcherKit implements Kit {

	@Override
	public Permission getPermission() {
		return new Permission("rabrg.unknown.kit.archer");
	}

	@Override
	public Material getIcon() {
		return Material.BOW;
	}

	@Override
	public String getName() {
		return "Archer";
	}

	@Override
	public String getDescription() {
		return "Rain death upon your enemies!";
	}

	@Override
	public ItemStack[] getArmour() {
		return new ItemStack[] { new ItemStack(Material.GOLD_BOOTS), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
	}

	@Override
	public Inventory getInventory(final Inventory inventory) {
		inventory.setItem(0, new ItemStack(Material.IRON_SWORD));
		inventory.setItem(8, new ItemStack(Material.BOW));
		inventory.setItem(9, new ItemStack(Material.ARROW, 24));
		return inventory;
	}

	@Override
	public int getSoupCount() {
		return 15;
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return new PotionEffect[] { new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true) };
	}

}
