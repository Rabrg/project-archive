package me.rabrg.ares.util;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class InventoryUtility {

	public static final int INVENTORY_SLOTS = 36;

	public static final ItemStack[] AIR_ARMOR_SET = createArmorSet(Material.AIR, Material.AIR, Material.AIR, Material.AIR);

	public static final ItemStack[] DIAMOND_ARMOR_SET = createArmorSet(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS);

	public static final ItemStack[] IRON_ARMOR_SET = createArmorSet(Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS);

	public static final ItemStack[] CHAINMAIL_ARMOR_SET = createArmorSet(Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS);

	public static final ItemStack[] GOLD_ARMOR_SET = createArmorSet(Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS);

	public static final ItemStack[] LEATHER_ARMOR_SET = createArmorSet(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS);

	public static ItemStack[] createArmorSet(final Material helmet, final Material chestplate, final Material leggings, final Material boots) {
		return new ItemStack[] { new ItemStack(boots), new ItemStack(leggings), new ItemStack(chestplate), new ItemStack(helmet)};
	}

	public static ItemStack[] enchantArmorSet(final ItemStack[] armorSet, final Enchantment enchantment, final int enchantmentLevel) {
		final ItemStack[] armorSet_ = armorSet;
		for (int slot = 0; slot < armorSet_.length; slot++) {
			armorSet_[slot] = createItem(armorSet_[slot], enchantment, enchantmentLevel);
		}
		return armorSet_;
	}

	public static ItemStack createItem(final Material material, Enchantment enchantment, final int enchantmentLevel) {
		final ItemStack item = new ItemStack(material);
		item.addEnchantment(enchantment, enchantmentLevel);
		return item;
	}

	public static ItemStack createItem(final ItemStack item, Enchantment enchantment, final int enchantmentLevel) {
		item.addEnchantment(enchantment, enchantmentLevel);
		return item;
	}

	public static ItemStack createItem(final ItemStack item, Map<Enchantment, Integer> enchantments) {
		item.addEnchantments(enchantments);
		return item;
	}

	public static void fillInventoryWithSoup(final PlayerInventory inventory) {
		for (int slot = 0; slot < INVENTORY_SLOTS; slot++) {
			if (inventory.getItem(slot) == null) {
				inventory.setItem(slot, new ItemStack(Material.MUSHROOM_SOUP));
			}
		}
	}
}
