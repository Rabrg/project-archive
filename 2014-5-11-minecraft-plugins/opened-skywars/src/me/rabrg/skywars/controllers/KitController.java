package me.rabrg.skywars.controllers;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.game.GameState;
import me.rabrg.skywars.player.GamePlayer;
import me.rabrg.skywars.utilities.FileUtils;
import me.rabrg.skywars.utilities.IconMenu;
import me.rabrg.skywars.utilities.ItemUtils;
import me.rabrg.skywars.utilities.LogUtils;
import me.rabrg.skywars.utilities.Messaging;

import com.flobi.WhatIsIt.WhatIsIt;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class KitController {

	private static final String PERMISSION_NODE = "skywars.kit.";
	private static final int INVENTORY_SLOTS_PER_ROW = 9;
	private static final int MAX_INVENTORY_SIZE = 54;
	private static final String KIT_MENU_NAME = new Messaging.MessageFormatter().format("kit.window-title");

	private static KitController instance;
	private final Map<String, Kit> kitMap = Maps.newHashMap();

	public KitController() {
		load();
	}

	public void load() {
		kitMap.clear();
		final File dataDirectory = SkyWars.get().getDataFolder();
		final File kitsDirectory = new File(dataDirectory, "kits");

		if (!kitsDirectory.exists()) {
			if (!kitsDirectory.mkdirs()) {
				return;
			}

			FileUtils.saveResource(SkyWars.get(), "example.yml", new File(kitsDirectory, "Example.yml"), false);
		}

		final File[] kits = kitsDirectory.listFiles();
		if (kits == null) {
			return;
		}

		for (final File kit : kits) {
			if (!kit.getName().endsWith(".yml")) {
				continue;
			}

			final String name = kit.getName().replace(".yml", "");

			if (!name.isEmpty() && !kitMap.containsKey(name)) {
				kitMap.put(name, new Kit(name, YamlConfiguration.loadConfiguration(kit)));
			}
		}

		LogUtils.log(Level.INFO, getClass(), "Registered %d kits ...", kitMap.size());
	}

	public boolean hasPermission(final Player player, final Kit kit) {
		return player.isOp() || player.hasPermission(PERMISSION_NODE + kit.getName().toLowerCase());
	}

	public boolean isPurchaseAble(final Kit kit) {
		return kit.getPoints() > 0;
	}

	public boolean canPurchase(final GamePlayer gamePlayer, final Kit kit) {
		return kit.getPoints() > 0 && gamePlayer.getScore() >= kit.getPoints();
	}

	public void populateInventory(final Inventory inventory, final Kit kit) {
		for (final ItemStack itemStack : kit.getItems()) {
			inventory.addItem(itemStack);
		}
	}

	public Kit getByName(final String name) {
		return kitMap.get(name);
	}

	public void openKitMenu(final GamePlayer gamePlayer) {
		final List<Kit> availableKits = Lists.newArrayList(kitMap.values());

		int rowCount = INVENTORY_SLOTS_PER_ROW;
		while (rowCount < availableKits.size() && rowCount < MAX_INVENTORY_SIZE) {
			rowCount += INVENTORY_SLOTS_PER_ROW;
		}

		IconMenuController.get().create(gamePlayer.getBukkitPlayer(), KIT_MENU_NAME, rowCount, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(final IconMenu.OptionClickEvent event) {
				if (!gamePlayer.isPlaying()) {
					event.getPlayer().sendMessage(new Messaging.MessageFormatter().format("error.not-in-game"));
					return;
				}

				if (gamePlayer.getGame().getState() != GameState.WAITING) {
					event.getPlayer().sendMessage(new Messaging.MessageFormatter().format("error.can-not-pick-kit"));
					return;
				}

				final Kit kit = KitController.get().getByName(ChatColor.stripColor(event.getName()));
				if (kit == null) {
					return;
				}

				if (isPurchaseAble(kit)) {
					if (!canPurchase(gamePlayer, kit)) {
						event.getPlayer().sendMessage(new Messaging.MessageFormatter().format("error.not-enough-score"));
						return;
					}

					gamePlayer.setScore(gamePlayer.getScore() - kit.getPoints());

				} else if (!hasPermission(event.getPlayer(), kit)) {
					event.getPlayer().sendMessage(new Messaging.MessageFormatter().format("error.no-permission-kit"));
					return;
				}

				event.setWillClose(true);
				event.setWillDestroy(true);

				populateInventory(event.getPlayer().getInventory(), kit);
				gamePlayer.setChosenKit(true);

				event.getPlayer().sendMessage(new Messaging.MessageFormatter().format("success.enjoy-kit"));
			}
		});

		for (int iii = 0; iii < availableKits.size(); iii++) {
			if (iii >= MAX_INVENTORY_SIZE) {
				break;
			}

			final Kit kit = availableKits.get(iii);
			final List<String> loreList = Lists.newLinkedList();
			boolean canPurchase = false;

			if (isPurchaseAble(kit)) {
				loreList.add("\247r\2476Price\247f: \247" + (gamePlayer.getScore() >= kit.getPoints() ? 'a' : 'c') + kit.getPoints());
				loreList.add(" ");

				if (canPurchase(gamePlayer, kit)) {
					canPurchase = true;
				}

			} else if (!hasPermission(gamePlayer.getBukkitPlayer(), kit)) {
				loreList.add(new Messaging.MessageFormatter().format("kit.lores.no-permission"));
				loreList.add(" ");

			} else {
				canPurchase = true;
			}

			loreList.addAll(kit.getLores());

			IconMenuController.get().setOption(gamePlayer.getBukkitPlayer(), iii, kit.getIcon(), "\247r\247" + (canPurchase ? 'a' : 'c') + kit.getName(),
					loreList.toArray(new String[loreList.size()]));
		}

		IconMenuController.get().show(gamePlayer.getBukkitPlayer());
	}

	public class Kit {

		private final String name;
		private final int points;
		private final List<ItemStack> items = Lists.newArrayList();

		private final ItemStack icon;
		private final List<String> lores;

		@SuppressWarnings("deprecation")
		public Kit(final String name, final FileConfiguration storage) {
			this.name = name;

			for (final String item : storage.getStringList("items")) {
				final ItemStack itemStack = ItemUtils.parseItem(item.split(" "));

				if (itemStack != null) {
					items.add(itemStack);
				}
			}

			points = storage.getInt("points", 0);

			final String icon = storage.getString("icon.material", "STONE");
			final short data = (short) storage.getInt("icon.data", 0);
			Material material;

			try {
				material = Material.getMaterial(Integer.parseInt(icon));
			} catch (final NumberFormatException nfe) {
				material = Material.getMaterial(icon);
			}

			if (material == null) {
				material = Material.STONE;
			}

			this.icon = new ItemStack(material, 1, data);

			lores = Lists.newLinkedList();
			if (storage.contains("details")) {
				for (final String string : storage.getStringList("details")) {
					lores.add("\247r" + ChatColor.translateAlternateColorCodes('&', string));
				}
			}

			lores.add("\247r\247eContents\247f:");
			for (final ItemStack itemStack : items) {
				lores.add("\247r\247c" + WhatIsIt.itemName(itemStack));
			}
		}

		public Collection<ItemStack> getItems() {
			return items;
		}

		public String getName() {
			return name;
		}

		public int getPoints() {
			return points;
		}

		public ItemStack getIcon() {
			return icon;
		}

		public List<String> getLores() {
			return lores;
		}
	}

	public static KitController get() {
		if (instance == null) {
			instance = new KitController();
		}

		return instance;
	}
}
