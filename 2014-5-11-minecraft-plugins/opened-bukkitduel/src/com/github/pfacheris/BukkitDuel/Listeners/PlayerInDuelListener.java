package com.github.pfacheris.BukkitDuel.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.pfacheris.BukkitDuel.BukkitDuel;
import com.github.pfacheris.BukkitDuel.Objects.Arena;
import com.github.pfacheris.BukkitDuel.Objects.Duel;

public class PlayerInDuelListener implements Listener {
	BukkitDuel plugin;
	Duel activeDuel;

	// private static final Logger log = Logger.getLogger("Minecraft");

	public PlayerInDuelListener(final BukkitDuel plugin, final Duel duel) {
		this.plugin = plugin;
		this.activeDuel = duel;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageEvent(final EntityDamageEvent event) {
		if (activeDuel.getIsActive()) {
			final Entity player = event.getEntity();
			if (player instanceof Player) {
				final Player p = (Player) player;
				if (p.equals(activeDuel.getInitiator())) {
					if (event instanceof EntityDamageByEntityEvent) {
						final Entity tempEnt = ((EntityDamageByEntityEvent) event).getDamager();
						if (tempEnt instanceof Player) {
							if (!((Player) tempEnt).equals(activeDuel.getChallengee())) {
								event.setCancelled(true);
								((Player) tempEnt).sendMessage("[BukkitDuel] That player is in a duel!");
								return;
							}
						}
					}
					final double damage = event.getDamage();
					final double pHealth = p.getHealth();
					if (damage >= pHealth) {
						event.setCancelled(true);
						BukkitDuel.duelManager.endDuel(activeDuel, false);
					}
				}

				if (p.equals(activeDuel.getChallengee())) {
					if (event instanceof EntityDamageByEntityEvent) {
						final Entity tempEnt = ((EntityDamageByEntityEvent) event).getDamager();
						if (tempEnt instanceof Player) {
							if (!((Player) tempEnt).equals(activeDuel.getInitiator())) {
								event.setCancelled(true);
								((Player) tempEnt).sendMessage("[BukkitDuel] That player is in a duel!");
								return;
							}
						}
					}
					final double damage = event.getDamage();
					final double pHealth = p.getHealth();
					if (damage >= pHealth) {
						event.setCancelled(true);
						BukkitDuel.duelManager.endDuel(activeDuel, true);
					}
				}

			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMoveEvent(final PlayerMoveEvent event) {
		if (activeDuel.getIsActive()) {
			final Player player = event.getPlayer();
			final String playerName = player.getName();

			if (playerName.equals(activeDuel.getInitiator().getName()) || playerName.equals(activeDuel.getChallengee().getName())) {
				final Arena tempArena = activeDuel.getArena();
				final double playerX = player.getLocation().getX();
				final double playerY = player.getLocation().getY();
				final double playerZ = player.getLocation().getZ();

				if (playerX < tempArena.getMinX() || playerX > tempArena.getMaxX() || playerY < tempArena.getMinY() || playerY > tempArena.getMaxY()
						|| playerZ < tempArena.getMinZ() || playerZ > tempArena.getMaxZ()) {
					event.setTo(event.getFrom());
					player.sendMessage(ChatColor.DARK_RED + "You can't leave the arena in a duel! Use /duel quit to forfeit the match!");
				}
			} else {
				final Arena tempArena = activeDuel.getArena();
				final double playerX = player.getLocation().getX();
				final double playerY = player.getLocation().getY();
				final double playerZ = player.getLocation().getZ();

				if (playerX > tempArena.getMinX() || playerX < tempArena.getMaxX() || playerY > tempArena.getMinY() || playerY < tempArena.getMaxY()
						|| playerZ > tempArena.getMinZ() || playerZ < tempArena.getMaxZ()) {
					event.setTo(event.getFrom());
					player.sendMessage(ChatColor.DARK_RED + "You can't enter the arena during another player's duel!");
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreakEvent(final BlockBreakEvent event) {
		if (activeDuel.getIsActive()) {
			final String playerName = event.getPlayer().getName();

			if (playerName.equals(activeDuel.getInitiator().getName()) || playerName.equals(activeDuel.getChallengee().getName())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.DARK_RED + "You can't damage the duel arena!");
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlaceEvent(final BlockPlaceEvent event) {
		if (activeDuel.getIsActive()) {
			final String playerName = event.getPlayer().getName();

			if (playerName.equals(activeDuel.getInitiator().getName()) || playerName.equals(activeDuel.getChallengee().getName())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.DARK_RED + "You can't place blocks in the duel arena!");
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(final PlayerCommandPreprocessEvent event) {
		if (activeDuel.getIsActive()) {
			final String playerName = event.getPlayer().getName();

			if (playerName.equals(activeDuel.getInitiator().getName()) || playerName.equals(activeDuel.getChallengee().getName())) {
				if (event.getMessage().startsWith("/")) {
					if (!event.getMessage().equalsIgnoreCase("/duel quit")) {
						event.setCancelled(true);
						event.getPlayer().sendMessage("Commands are disabled during duels! Use '/duel quit' to forfeit the match!");
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuitEvent(final PlayerQuitEvent event) {
		if (activeDuel.getIsActive()) {
			final String playerName = event.getPlayer().getName();

			if (playerName.equals(activeDuel.getInitiator().getName())) {
				BukkitDuel.duelManager.endDuel(activeDuel, false);
			} else if (playerName.equals(activeDuel.getChallengee().getName())) {
				BukkitDuel.duelManager.endDuel(activeDuel, true);
			}
		}
	}

}