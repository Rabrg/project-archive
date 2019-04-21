package me.rabrg.cooldowns;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommandPreprocessEvent(final PlayerCommandPreprocessEvent event) {
		if (event.getMessage().contains("fix all")) {
			final String name = event.getPlayer().getName().toLowerCase();
			final long time = System.currentTimeMillis();
			final long next = getConfig().getLong("fixall." + name);
			if (time > next) {
				getConfig().set("fixall." + name, time + 259200000);
			} else {
				event.setCancelled(true);
				event.getPlayer().sendMessage("You can't use this command for another " + (next - time) / 1000 + " seconds.");
			}
		}
	}
}
