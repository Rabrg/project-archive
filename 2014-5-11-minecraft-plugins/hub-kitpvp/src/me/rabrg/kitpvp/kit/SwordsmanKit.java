package me.rabrg.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;

public final class SwordsmanKit extends Kit {

	@Override
	public String getName() {
		return "Swordsman";
	}

	@Override
	public Permission getPermission() {
		return new Permission("rabrg.kitpvp.kit.swordsman");
	}

	@Override
	public PlayerInventory getPlayerInventory(final PlayerInventory inventory) {
		inventory.setHelmet(new ItemStack(Material.IRON_HELMET));
		inventory.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		inventory.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		inventory.setBoots(new ItemStack(Material.IRON_BOOTS));
		
		inventory.setItem(0, new ItemStack(Material.DIAMOND_SWORD));
		for(int slot = 1; slot < 18; slot++) {
			inventory.setItem(slot, new ItemStack(Material.MUSHROOM_SOUP));
		}
		return inventory;
	}

}
