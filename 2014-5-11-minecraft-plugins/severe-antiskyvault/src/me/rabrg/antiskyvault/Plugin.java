package me.rabrg.antiskyvault;

import me.rabrg.antiskyvault.listener.AntiSkyVaultListener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

	private int limit;

	private int radius;

	private int count;

	@Override
	public void onEnable() {
		limit = getConfig().getInt("limit", 160);
		radius = getConfig().getInt("radius", 15);
		count = getConfig().getInt("count", 5);
		saveConfig();
		getServer().getPluginManager().registerEvents(new AntiSkyVaultListener(this), this);
	}

	@Override
	public void onDisable() {
		saveConfig();
	}

	public boolean hasNearbySolidBlocks(final Location location) {
		int count_ = 0;
		for (int x = -(radius); x <= radius; x++) {
			for (int y = -(radius); y <= radius; y++) {
				for (int z = -(radius); z <= radius; z++) {
					final Material type = location.getBlock().getRelative(x, y, z).getType();
					if(type != Material.CHEST && type != Material.TRAPPED_CHEST && type != Material.DISPENSER && type != Material.ENDER_CHEST && type != Material.GLASS && type != Material.WORKBENCH && type != Material.FURNACE && type != Material.AIR) {
						if(++count_ >= count) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public int getLimit() {
		return limit;
	}
}
