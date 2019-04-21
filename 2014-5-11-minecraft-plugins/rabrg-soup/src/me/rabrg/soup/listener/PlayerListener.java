package me.rabrg.soup.listener;

import me.rabrg.soup.RabrgSoupPlugin;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class PlayerListener implements Listener {

	private final RabrgSoupPlugin plugin;

	public PlayerListener(final RabrgSoupPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteractEvent(final PlayerInteractEvent event) {
		if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getMaterial() == Material.MUSHROOM_SOUP) {
			if (event.getPlayer().getHealth() == event.getPlayer().getMaxHealth()) {
				
			} else if ((event.getPlayer().getHealth() + plugin.getSoupHeal()) > event.getPlayer().getMaxHealth()) {
				event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
				event.getPlayer().setItemInHand(null);
			} else if((event.getPlayer().getHealth() + plugin.getSoupHeal()) < event.getPlayer().getMaxHealth()) {
				event.getPlayer().setHealth(event.getPlayer().getHealth() + plugin.getSoupHeal());
				event.getPlayer().setItemInHand(null);
			}
			event.setCancelled(true);
		}
	}
}
