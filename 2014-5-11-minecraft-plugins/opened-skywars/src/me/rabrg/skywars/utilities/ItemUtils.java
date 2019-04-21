package me.rabrg.skywars.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.rabrg.skywars.SkyWars;

import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.MetaItemStack;

public class ItemUtils {

	public static ItemStack parseItem(final String[] args) {
		if (args.length < 1) {
			return null;
		}

		final IEssentials essentials = SkyWars.getEssentials();
		ItemStack itemStack = null;

		try {
			itemStack = essentials.getItemDb().get(args[0]);

			if (args.length > 1 && Integer.parseInt(args[1]) > 0) {
				itemStack.setAmount(Integer.parseInt(args[1]));
			}

			if (args.length > 2) {
				final MetaItemStack metaItemStack = new MetaItemStack(itemStack);
				metaItemStack.parseStringMeta(null, true, args, 2, essentials);
				itemStack = metaItemStack.getItemStack();
			}

		} catch (final Exception ignored) {

		}

		return itemStack;
	}

	public static ItemStack name(final ItemStack itemStack, final String name, final String... lores) {
		final ItemMeta itemMeta = itemStack.getItemMeta();

		if (!name.isEmpty()) {
			itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		}

		if (lores.length > 0) {
			final List<String> loreList = new ArrayList<String>(lores.length);

			for (final String lore : lores) {
				loreList.add(ChatColor.translateAlternateColorCodes('&', lore));
			}

			itemMeta.setLore(loreList);
		}

		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
}
