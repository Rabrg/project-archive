package me.rabrg.league.util;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public final class InventoryUtilities {

	private InventoryUtilities() {
		
	}

	public static void giveSelectionInventory(final Player player) {
		final PlayerInventory inventory = player.getInventory();
		inventory.setItem(0, createItem(Material.STICK, "Casual stick", "Right click to join the casual queue."));
		inventory.setItem(8, createItem(Material.BLAZE_ROD, "Ranked stick", "Right click to join the ranked queue."));
	}

	public static void openQueueInventory(final Player player, final boolean ranked) {
		// final String queue = ranked ? "ranked" : "casual";
		// final Inventory inventory = createInventory("Choose the " + queue + " queue you'd like to join", 1, new ItemStack(Material.GLASS), new ItemStack(Material.IRON_SWORD));
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
}
