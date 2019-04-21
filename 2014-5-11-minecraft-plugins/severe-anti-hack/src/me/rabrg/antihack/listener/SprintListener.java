package me.rabrg.antihack.listener;

import java.util.HashMap;
import java.util.Map;

import me.rabrg.antihack.Plugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public final class SprintListener implements Listener {

	private final Plugin plugin;

	private final Map<Player, Long> lastSprints;

	public SprintListener(final Plugin plugin) {
		this.plugin = plugin;
		this.lastSprints = new HashMap<Player, Long>();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerToggleSprintEvent(final PlayerToggleSprintEvent event) {
		if (event.isSprinting()) {
			lastSprints.put(event.getPlayer(), System.currentTimeMillis());
		} else {
			lastSprints.remove(event.getPlayer());
		}
		hungerCheck(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMoveEvent(final PlayerMoveEvent event) {
		hungerCheck(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerItemConsumeEvent(final PlayerItemConsumeEvent event) {
		// if (event.getPlayer().isSprinting()) {
		// 	if (System.currentTimeMillis() - lastSprints.get(event.getPlayer()) > 500) {
		// 		plugin.takeAction(event.getPlayer(), "consume", "food: " + event.getPlayer().getFoodLevel() + " last sprint toggled: " + (System.currentTimeMillis() - lastSprints.get(event.getPlayer())));
		// 	}
		// }
	}

	private void hungerCheck(final Player player) {
		if (!player.hasPermission("rabrg.sprint.bypass")) {
			if (player.isSprinting() && player.getFoodLevel() == 0) {
				plugin.takeAction(player, "hunger", "food: " + player.getFoodLevel());
			}
		}
	}
}
