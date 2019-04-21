package me.rabrg.unknown.kit;

import java.util.HashMap;
import java.util.Map;

import me.rabrg.unknown.kit.impl.ArcherKit;
import me.rabrg.unknown.kit.impl.SwordsmanKit;
import me.rabrg.unknown.util.InventoryUtility;
import me.rabrg.unknown.util.PlayerUtility;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public final class KitManager {

	private static final Map<String, Kit> kits;

	static {
		kits = new HashMap<String, Kit>();
		
		final Kit archerKit = new ArcherKit();
		kits.put(archerKit.getName().toLowerCase(), archerKit);
		
		final Kit swodrsmanKit = new SwordsmanKit();
		kits.put(swodrsmanKit.getName().toLowerCase(), swodrsmanKit);
	}

	public static void giveKit(final Player player, String kit) {
		kit = kit.toLowerCase();
		if (kits.get(kit) == null) {
			player.sendMessage("That kit doesn't exist!"); // TODO: language
			return;
		}
		giveKit(player, kits.get(kit));
	}

	public static void giveKit(final Player player, final Kit kit) {
		if (!player.hasPermission(kit.getPermission())) {
			player.sendMessage("You don't have permission to use that kit."); // TODO: language
			return;
		}
		
		// TODO: position
		
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		
		final PlayerInventory inventory = player.getInventory();
		inventory.clear();
		inventory.setArmorContents(kit.getArmour());
		
		kit.getInventory(inventory);
		
		InventoryUtility.giveSoup(inventory, kit.getSoupCount());
		PlayerUtility.removeAndAddPotionEffects(player, kit.getPotionEffects());
		
		player.sendMessage("You have chosen the " + ChatColor.GREEN + kit.getName() + ChatColor.WHITE + " kit."); // TODO: language
	}

	public static void listKits(final Player player) {
		final StringBuffer message = new StringBuffer().append("Kits: ");
		for (final Kit kit : kits.values()) {
			if (player.hasPermission(kit.getPermission())) {
				message.append(ChatColor.GREEN);
			} else {
				message.append(ChatColor.RED);
			}
			message.append(kit.getName() + ChatColor.WHITE + ", ");
		}
		player.sendMessage(message.substring(0, message.length() - 2));
	}

	public static void openKitSelection(final Player player) {
		
	}
}
