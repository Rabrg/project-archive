package net.thewavemc.onevsone.listeners;

import net.thewavemc.onevsone.Configuration;
import net.thewavemc.onevsone.OneVsOne;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerLifeEvents implements Listener {
	OneVsOne plugin;

	public PlayerLifeEvents(final OneVsOne plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent evt) {
		if (evt.getEntity() instanceof Player && evt.getDamager() instanceof Player) {
			final Player player = (Player) evt.getEntity();
			final Player damager = (Player) evt.getDamager();
			if (!this.plugin.getDuelManager().isInDuel(damager) && this.plugin.getDuelManager().isOneVsOne(damager)) {
				evt.setCancelled(true);
				return;
			}
			if (!this.plugin.getDuelManager().isInDuel(player) && this.plugin.getDuelManager().isOneVsOne(player)) {
				evt.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler
	public void onPlayerDeathEvent(final PlayerDeathEvent evt) {
		final Player p1 = evt.getEntity();
		if (this.plugin.getDuelManager().isOneVsOne(p1)) {
			evt.getDrops().clear();
			evt.setDroppedExp(0);
			if (this.plugin.getDuelManager().isInDuel(p1)) {
				this.plugin.getDuelManager().endDuel(p1);
				evt.setDeathMessage(null);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawnEvent(final PlayerRespawnEvent evt) {
		if (this.plugin.getDuelManager().isOneVsOne(evt.getPlayer())) {
			evt.setRespawnLocation(Configuration.spawn);
			evt.getPlayer().getInventory().setItem(4, Configuration.inviteItem);
		}
	}
}
