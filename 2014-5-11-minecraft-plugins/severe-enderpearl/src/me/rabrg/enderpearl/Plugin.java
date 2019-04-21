package me.rabrg.enderpearl;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityTeleportEvent(final PlayerTeleportEvent  event) {
		if (event.getCause() == TeleportCause.ENDER_PEARL) {
			if (event.getFrom().distance(event.getTo()) <= 5) {
				event.getPlayer().sendMessage("You can't enderpearl to blocks closer than 5 blocks away.");
				event.setCancelled(true);
				return;
			}
			final Block block = event.getTo().getBlock();
			for (int x = -3; x < 3; x++) {
				for (int y = -3; y < 3; y++) {
					for (int z = -3; z < 3; z++) {
						@SuppressWarnings("deprecation")
						final int id = block.getRelative(x, y, z).getTypeId();
						if (id == 64 || id == 71) {
							event.getPlayer().sendMessage("You can't enderpearl onto doors.");
							event.setCancelled(true);
							return;
						}
					}
				}
			}
		}
	}
}
