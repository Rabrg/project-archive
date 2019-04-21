package me.rabrg.bedrock;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlaceEvent(final BlockPlaceEvent event) {
		if (event.getBlock().getType() == Material.SAPLING && event.getBlock().getY() <= 10) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("You can't place saplings at bedrock levels");
		}
	}
}
