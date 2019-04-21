package me.rabrg.skywars.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

// https://forums.bukkit.org/threads/icon-menu.108342/
public class IconMenu {

	private final String name;
	private final int size;
	private OptionClickEventHandler handler;
	private Plugin plugin;
	private String[] optionNames;
	private ItemStack[] optionIcons;

	public IconMenu(final String name, final int size, final OptionClickEventHandler handler, final Plugin plugin) {
		this.name = name;
		this.size = size;
		this.handler = handler;
		this.plugin = plugin;
		this.optionNames = new String[size];
		this.optionIcons = new ItemStack[size];
	}

	public IconMenu setOption(final int position, final ItemStack icon, final String name, final String[] info) {
		this.optionNames[position] = name;
		this.optionIcons[position] = ItemUtils.name(icon, name, info);
		return this;
	}

	public void open(final Player player) {
		final Inventory inventory = Bukkit.createInventory(player, this.size, this.name);
		for (int iii = 0; iii < this.optionIcons.length; iii++) {
			if (this.optionIcons[iii] != null) {
				inventory.setItem(iii, this.optionIcons[iii]);
			}
		}
		player.openInventory(inventory);
	}

	public void destroy() {
		this.handler = null;
		this.plugin = null;
		this.optionNames = null;
		this.optionIcons = null;
	}

	public void onInventoryClick(final InventoryClickEvent event) {
		if (!event.getInventory().getTitle().equals(name)) {
			return;
		}

		event.setCancelled(true);

		final int slot = event.getRawSlot();
		if (!(slot >= 0 && slot < size && optionNames[slot] != null)) {
			return;
		}

		final OptionClickEvent clickEvent = new OptionClickEvent((Player) event.getWhoClicked(), slot, optionNames[slot]);
		handler.onOptionClick(clickEvent);

		if (clickEvent.willClose()) {
			final Player player = (Player) event.getWhoClicked();

			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {
					player.closeInventory();
				}
			}, 1L);
		}

		if (clickEvent.willDestroy()) {
			destroy();
		}
	}

	public String getName() {
		return this.name;
	}

	public class OptionClickEvent {

		private final Player player;
		private final int position;
		private final String name;
		private boolean close;
		private boolean destroy;

		public OptionClickEvent(final Player player, final int position, final String name) {
			this.player = player;
			this.position = position;
			this.name = name;
			this.close = false;
			this.destroy = false;
		}

		public Player getPlayer() {
			return this.player;
		}

		public int getPosition() {
			return this.position;
		}

		public String getName() {
			return this.name;
		}

		public boolean willClose() {
			return this.close;
		}

		public boolean willDestroy() {
			return this.destroy;
		}

		public void setWillClose(final boolean close) {
			this.close = close;
		}

		public void setWillDestroy(final boolean destroy) {
			this.destroy = destroy;
		}
	}

	public static abstract interface OptionClickEventHandler {

		public abstract void onOptionClick(IconMenu.OptionClickEvent event);
	}
}