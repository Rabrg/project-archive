package me.rabrg.kits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public final class Kit {

	private String name;
	private String description;

	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;

	private List<ItemStack> inventory;

	private List<PotionEffect> potionEffects;

	public Kit(final String name) {
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	public void deserialize(final FileConfiguration config) {
		description = config.getString(name + ".description");
		
		helmet = config.getItemStack(name + ".armor.helmet");
		chestplate = config.getItemStack(name + ".armor.chestplate");
		leggings = config.getItemStack(name + ".armor.leggings");
		boots = config.getItemStack(name + ".armor.boots");
		
		inventory = (List<ItemStack>) config.getList(name + ".inventory");
		
		final List<Map<String, Object>> serializedPotionEffects = (List<Map<String, Object>>) config.getList(name + ".potion-effects");
		for (final Map<String, Object> serializedPotionEffect : serializedPotionEffects) {
			potionEffects.add(new PotionEffect(serializedPotionEffect));
		}
	}

	public void serialize(final FileConfiguration config) {
		config.set(name + ".description", description);
		
		config.set(name + ".armor.helmet", helmet);
		config.set(name + ".armor.chestplate", chestplate);
		config.set(name + ".armor.leggings", leggings);
		config.set(name + ".armor.boots", boots);
		
		config.set(name + ".inventory", inventory);
		
		final List<Map<String, Object>> serializedPotionEffects = new ArrayList<>();
		for (final PotionEffect potionEffect : potionEffects) {
			serializedPotionEffects.add(potionEffect.serialize());
		}
		
		config.set(name + ".potion-effects", serializedPotionEffects);
	}

	public void giveKit(final Player player) {
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.getInventory().setHelmet(helmet);
		player.getInventory().setChestplate(chestplate);
		player.getInventory().setLeggings(leggings);
		player.getInventory().setBoots(boots);
		
		final ItemStack[] inventory_ = new ItemStack[inventory.size()]; // TODO: better way
		inventory.toArray(inventory_);
		player.getInventory().setContents(inventory_);
		
		player.getActivePotionEffects().clear();
		if (potionEffects != null) {
			player.getActivePotionEffects().addAll(potionEffects);
		}
		
		player.sendMessage(ChatColor.AQUA + "You've been given kit " + ChatColor.RED + name + ChatColor.AQUA + "!");
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public ItemStack getHelmet() {
		return helmet;
	}

	public void setHelmet(final ItemStack helmet) {
		this.helmet = helmet;
	}

	public ItemStack getChestplate() {
		return chestplate;
	}

	public void setChestplate(final ItemStack chestplate) {
		this.chestplate = chestplate;
	}

	public ItemStack getLeggings() {
		return leggings;
	}

	public void setLeggings(final ItemStack leggings) {
		this.leggings = leggings;
	}

	public ItemStack getBoots() {
		return boots;
	}

	public void setBoots(final ItemStack boots) {
		this.boots = boots;
	}

	public List<ItemStack> getInventory() {
		return inventory;
	}

	public void setInventory(final List<ItemStack> inventory) {
		this.inventory = inventory;
	}

	public List<PotionEffect> getPotionEffects() {
		return potionEffects;
	}

	public void setPotionEffects(final List<PotionEffect> potionEffects) {
		this.potionEffects = potionEffects;
	}
}
