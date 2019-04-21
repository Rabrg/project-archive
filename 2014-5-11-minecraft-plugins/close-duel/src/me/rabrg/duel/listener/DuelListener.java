package me.rabrg.duel.listener;

import me.rabrg.duel.Plugin;
import me.rabrg.duel.arena.ArenaManager;
import me.rabrg.duel.player.DuelPlayer;
import me.rabrg.duel.player.DuelPlayerManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public final class DuelListener implements Listener {

	@SuppressWarnings("unused")
	private final Plugin plugin;

	public DuelListener(final Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent  event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			final DuelPlayer damager = DuelPlayerManager.getPlayer((Player) event.getDamager());
			final DuelPlayer damaged = DuelPlayerManager.getPlayer((Player) event.getEntity());
			
			if (damager.getDueling() != damaged) {
				damager.getPlayer().sendMessage("[Duel] You aren't dueling this player!");
				event.setCancelled(true);
				return;
			}
			
			if (event.getDamage() >= damaged.getPlayer().getHealth()) {
				event.setCancelled(true);
				ArenaManager.endArena(damager, damaged);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDeathEvent(final EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			final DuelPlayer dead = DuelPlayerManager.getPlayer((Player) event.getEntity());
			
			if (dead.getDueling() != null) {
				ArenaManager.endArena(dead.getDueling(), dead);
			}
		}
	}
}
