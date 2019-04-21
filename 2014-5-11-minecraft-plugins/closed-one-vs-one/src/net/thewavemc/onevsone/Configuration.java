package net.thewavemc.onevsone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Configuration {
	public static boolean isConfigured = false;
	public static String noperms = null;
	public static Location spawn = null;
	public static Location spotOne = null;
	public static Location spotTwo = null;
	public static ItemStack inviteItem = null;
	public static ItemStack[] inventoryContents = new ItemStack[36];
	public static ItemStack[] armorContents = new ItemStack[4];
	public static List<String> disabledCommands = new ArrayList();
	public static boolean restore = true;
	public static Location exit = null;

	public static void loadConfig(final OneVsOne plugin) {
		plugin.saveDefaultConfig();

		noperms = plugin.getConfig().getString("noperms");

		spawn = loadLocationFromConfig(plugin, "arena.spawn");
		spotOne = loadLocationFromConfig(plugin, "arena.spotone");
		spotTwo = loadLocationFromConfig(plugin, "arena.spottwo");

		inviteItem = getInviteItem(Material.valueOf(plugin.getConfig().getString("arena.invite-item")));

		final List<ItemStack> itemsList = (List) plugin.getConfig().get("arena.inventory.items");
		for (int i = 0; i < itemsList.size(); i++) {
			inventoryContents[i] = itemsList.get(i);
		}
		final List<ItemStack> armorList = (List) plugin.getConfig().get("arena.inventory.armor");
		for (int i = 0; i < armorList.size(); i++) {
			armorContents[i] = armorList.get(i);
		}
		disabledCommands = plugin.getConfig().getStringList("arena.disabled-commands");

		restore = plugin.getConfig().getBoolean("restore");
		if (!restore) {
			exit = loadLocationFromConfig(plugin, "exit");
		}
		checkConfigured();
	}

	public static String checkConfigured() {
		if (spawn == null) {
			return "The spawn location isn't configured correctly!";
		}
		if (spotOne == null) {
			return "The first spot isn't configured correctly!";
		}
		if (spotTwo == null) {
			return "The second spot isn't configured correctly!";
		}
		if (inviteItem == null) {
			return "The invite-item isn't configured correctly!";
		}
		if (inventoryContents == null) {
			return "The inventory(items) isn't configured correctly!";
		}
		if (armorContents == null) {
			return "The inventory(armor) isn't configured correctly!";
		}
		if (!restore && exit == null) {
			return "The exit location isn't configured correctly!";
		}
		isConfigured = true;
		return ChatColor.GREEN + "Configured and ready for use!";
	}

	public static void saveLocationToConfig(final OneVsOne plugin, final String path, final Location location) {
		plugin.getConfig().set(path + ".world", location.getWorld().getName());
		plugin.getConfig().set(path + ".x", Double.valueOf(location.getX()));
		plugin.getConfig().set(path + ".y", Double.valueOf(location.getY()));
		plugin.getConfig().set(path + ".z", Double.valueOf(location.getZ()));
		plugin.getConfig().set(path + ".yaw", Float.valueOf(location.getYaw()));
		plugin.getConfig().set(path + ".pitch", Float.valueOf(location.getPitch()));
	}

	public static Location loadLocationFromConfig(final OneVsOne plugin, final String path) {
		Location loc;
		try {
			loc = new Location(Bukkit.getWorld(plugin.getConfig().getString(path + ".world")), plugin.getConfig().getDouble(path + ".x"),
					plugin.getConfig().getDouble(path + ".y"), plugin.getConfig().getDouble(path + ".z"), (float) plugin.getConfig().getDouble(path + ".yaw"), (float) plugin
							.getConfig().getDouble(path + ".pitch"));
		} catch (final Exception e) {
			return null;
		}
		return loc;
	}

	public static ItemStack getInviteItem(final Material m) {
		if (m != null) {
			final ItemStack i = new ItemStack(m);
			final ItemMeta iMeta = i.getItemMeta();
			iMeta.setDisplayName(ChatColor.GREEN + "Invite Item");
			iMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Right click player to use!" }));
			i.setItemMeta(iMeta);
			return i;
		}
		final ItemStack i = new ItemStack(Material.STICK);
		final ItemMeta iMeta = i.getItemMeta();
		iMeta.setDisplayName(ChatColor.GREEN + "Invite Item");
		iMeta.setLore(Arrays.asList(new String[] { ChatColor.GREEN + "Right click player to use!" }));
		i.setItemMeta(iMeta);
		return i;
	}
}
