package me.rabrg.antiskyvault.listener;

import me.rabrg.antiskyvault.Plugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public final class AntiSkyVaultListener implements Listener {

	private final Plugin plugin;

	public AntiSkyVaultListener(final Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteractEvent(final PlayerInteractEvent event) {
		final Block block = event.getClickedBlock();
		if (block == null) {
			return;
		}
		final Location location = block.getLocation();
		if (location.getBlockY() >= plugin.getLimit()) {
			System.out.print("above limit ");
			final Material type = block.getType();
			if(type == Material.CHEST ||type == Material.TRAPPED_CHEST || type == Material.DISPENSER || type == Material.FURNACE) {
				System.out.println("that is a chest ");
				if (!plugin.hasNearbySolidBlocks(location)) {
					System.out.println("that has been broke");
					block.breakNaturally();
					event.getPlayer().sendMessage("[AntiSkyVault] The chest you interacted with has been broken because it has the properties of a skyvault.");
				}
			}
		}
	}
}
