package me.rabrg.kitpvp.kit;

import java.util.HashMap;
import java.util.Map;

import me.rabrg.kitpvp.KitPvP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public final class KitManager {

	private final KitPvP kitPvP;

	private final Map<String, Kit> kits;

	public KitManager(final KitPvP kitPvP) {
		this.kitPvP = kitPvP;
		this.kits = new HashMap<String, Kit>();
		
		final SwordsmanKit swordsmanKit = new SwordsmanKit();
		kits.put(swordsmanKit.getName().toLowerCase(), swordsmanKit);
	}

	public void giveKit(final Player player, final String kitname) {
		if(kitPvP.getSpawn().isInside(player.getLocation())) {
			final Kit kit = kits.get(kitname);
			if(kit != null) {
				if(player.hasPermission(kit.getPermission())) {
					final PlayerInventory inventory = player.getInventory();
					player.getInventory().clear();
					kit.getPlayerInventory(inventory);
					player.sendMessage("");
				} else {
					player.sendMessage("");
				}
			} else {
				player.sendMessage("");
			}
		} else {
			player.sendMessage("");
		}
	}

	public String getKits(final Player player) {
		final StringBuffer stringBuffer = new StringBuffer();
		for(final Kit kit : kits.values()) {
			if(player.hasPermission(kit.getPermission())) {
				stringBuffer.append(ChatColor.GREEN);
			} else {
				stringBuffer.append(ChatColor.RED);
			}
			stringBuffer.append(kit.getName() + ChatColor.WHITE + ", ");
		}
		System.out.println(stringBuffer.toString());
		return stringBuffer.toString().substring(0, stringBuffer.length() - 2);
	}
}
