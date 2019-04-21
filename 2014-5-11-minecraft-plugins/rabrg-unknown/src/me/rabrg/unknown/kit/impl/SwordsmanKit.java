package me.rabrg.unknown.kit.impl;

import me.rabrg.unknown.kit.Kit;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;

public final class SwordsmanKit implements Kit {

	@Override
	public Permission getPermission() {
		return new Permission("rabrg.unknown.kit.swordsman");
	}

	@Override
	public Material getIcon() {
		return Material.DIAMOND_SWORD;
	}

	@Override
	public String getName() {
		return "Swordsman";
	}

	@Override
	public String getDescription() {
		return "Force the enemy to taste your blade!";
	}

	@Override
	public ItemStack[] getArmour() {
		return new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
	}

	@Override
	public Inventory getInventory(final Inventory inventory) {
		inventory.setItem(0, new ItemStack(Material.DIAMOND_SWORD));
		return inventory;
	}

	@Override
	public int getSoupCount() {
		return 17;
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

}
