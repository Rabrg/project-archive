package me.rabrg.skywars.controllers;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.utilities.ItemUtils;
import me.rabrg.skywars.utilities.LogUtils;

import com.google.common.collect.Lists;

public class ChestController {

	private static ChestController chestController;
	private final List<ChestItem> chestItemList = Lists.newArrayList();
	private final Random random = new Random();

	public ChestController() {
		load();
	}

	public void load() {
		chestItemList.clear();
		final File chestFile = new File(SkyWars.get().getDataFolder(), "chest.yml");

		if (!chestFile.exists()) {
			SkyWars.get().saveResource("chest.yml", false);
		}

		if (chestFile.exists()) {
			final FileConfiguration storage = YamlConfiguration.loadConfiguration(chestFile);

			if (storage.contains("items")) {
				for (final String item : storage.getStringList("items")) {
					final String[] itemData = item.split(" ", 2);

					final int chance = Integer.parseInt(itemData[0]);
					final ItemStack itemStack = ItemUtils.parseItem(itemData[1].split(" "));

					if (itemStack != null) {
						chestItemList.add(new ChestItem(itemStack, chance));
					}
				}
			}
		}

		LogUtils.log(Level.INFO, getClass(), "Registered %d chest items ...", chestItemList.size());
	}

	public void populateChest(final Chest chest) {
		final Inventory inventory = chest.getBlockInventory();
		int added = 0;

		for (final ChestItem chestItem : chestItemList) {
			if (random.nextInt(100) + 1 <= chestItem.getChance()) {
				inventory.addItem(chestItem.getItem());

				if (added++ > inventory.getSize()) {
					break;
				}
			}
		}
	}

	public class ChestItem {

		private final ItemStack item;
		private final int chance;

		public ChestItem(final ItemStack item, final int chance) {
			this.item = item;
			this.chance = chance;
		}

		public ItemStack getItem() {
			return item;
		}

		public int getChance() {
			return chance;
		}
	}

	public static ChestController get() {
		if (chestController == null) {
			chestController = new ChestController();
		}

		return chestController;
	}
}
