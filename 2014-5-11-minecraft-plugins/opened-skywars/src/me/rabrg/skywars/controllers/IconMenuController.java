package me.rabrg.skywars.controllers;

import java.util.HashSet;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.utilities.IconMenu;

import com.google.common.collect.Maps;

public class IconMenuController implements Listener {

	private static IconMenuController instance;
	private final Map<Player, IconMenu> menuMap = Maps.newHashMap();

	public IconMenuController() {
		Bukkit.getPluginManager().registerEvents(this, SkyWars.get());
	}

	public void create(final Player player, final String name, final int size, final IconMenu.OptionClickEventHandler handler) {
		destroy(player);
		menuMap.put(player, new IconMenu(name, size, handler, SkyWars.get()));
	}

	public void show(@Nonnull final Player player) {
		if (menuMap.containsKey(player)) {
			menuMap.get(player).open(player);
		}
	}

	public void setOption(final Player player, final int position, final ItemStack icon, final String name, final String... info) {
		if (menuMap.containsKey(player)) {
			menuMap.get(player).setOption(position, icon, name, info);
		}
	}

	public void destroy(final Player player) {
		if (menuMap.containsKey(player)) {
			menuMap.remove(player).destroy();
			player.getOpenInventory().close();
		}
	}

	public void destroyAll() {
		for (final Player player : new HashSet<Player>(menuMap.keySet())) {
			destroy(player);
		}
	}

	public boolean has(final Player player) {
		return menuMap.containsKey(player);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryClick(final InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player && menuMap.containsKey(event.getWhoClicked())) {
			menuMap.get(event.getWhoClicked()).onInventoryClick(event);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryClose(final InventoryCloseEvent event) {
		if (event.getPlayer() instanceof Player && menuMap.containsKey(event.getPlayer())) {
			destroy((Player) event.getPlayer());
		}
	}

	public static IconMenuController get() {
		if (instance == null) {
			instance = new IconMenuController();
		}

		return instance;
	}
}