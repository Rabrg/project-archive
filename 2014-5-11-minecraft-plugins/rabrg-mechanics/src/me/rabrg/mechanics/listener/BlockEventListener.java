package me.rabrg.mechanics.listener;

import me.rabrg.mechanics.RabrgMechanicsPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockSpreadEvent;

public final class BlockEventListener implements Listener {
	private final RabrgMechanicsPlugin plugin;

	public BlockEventListener(final RabrgMechanicsPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockFormEvent(final BlockFormEvent event) {
		if (plugin.getConfig().getBoolean("disable-block-form", true)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockSpreadEvent(final BlockSpreadEvent event) {
		if (plugin.getConfig().getBoolean("disable-block-spread", true)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBurnEvent(final BlockBurnEvent event) {
		if (plugin.getConfig().getBoolean("disable-block-burn", true)) {
			event.setCancelled(true);
		}
	}
}
