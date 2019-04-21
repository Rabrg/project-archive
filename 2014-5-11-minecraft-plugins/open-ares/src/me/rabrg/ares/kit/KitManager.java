package me.rabrg.ares.kit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import me.rabrg.ares.Plugin;
import me.rabrg.ares.kit.impl.FishermanKit;
import me.rabrg.ares.kit.impl.MeleeKit;

public final class KitManager {

	private static final List<Kit> kits = new ArrayList<Kit>();

	public static void initiate() {
		FishermanKit fisherman = new FishermanKit();
		Bukkit.getPluginManager().registerEvents(fisherman, Plugin.get());
		kits.add(fisherman);
		
		MeleeKit melee = new MeleeKit();
		// Bukkit.getPluginManager().registerEvents(melee, Plugin.get());
		kits.add(melee);
	}

	public static Kit getKitByName(final String name) {
		for (final Kit kit : kits) {
			if (kit.getName().equalsIgnoreCase(name)) {
				return kit;
			}
		}
		return null;
	}
}
