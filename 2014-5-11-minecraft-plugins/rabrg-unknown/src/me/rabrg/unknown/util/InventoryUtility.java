package me.rabrg.unknown.util;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class InventoryUtility {

	private InventoryUtility() {
		
	}

	public static Inventory createInventory(final String name, final int rows, final ItemStack... items) {
		final Inventory inventory = Bukkit.createInventory(null, rows * 9, name);
		inventory.addItem(items);
		return inventory;
	}

	public static ItemStack createItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material);
		final ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	public static void giveSoup(final Inventory inventory, final int amount) {
		int given = 0;
		for (int slot = 0; slot < inventory.getSize(); slot++) {
			if (inventory.getItem(slot) == null || inventory.getItem(slot).getType() == Material.AIR) {
				inventory.setItem(slot, new ItemStack(Material.MUSHROOM_SOUP));
				if (++given == amount) {
					break;
				}
			}
		}
	}
}
