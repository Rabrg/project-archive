package me.rabrg.sprint;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerToggleSprintEvent(final PlayerToggleSprintEvent event) {
		hungerCheck(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMoveEvent(final PlayerMoveEvent event) {
		hungerCheck(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerItemConsumeEvent(final PlayerItemConsumeEvent event) {
		if (event.getPlayer().isSprinting()) {
			takeAction(event.getPlayer(), "cws: " + event.getPlayer().getFoodLevel());
		}
	}

	private void hungerCheck(final Player player) {
		if (!player.hasPermission("rabrg.sprint.bypass")) {
			if (player.isSprinting() && player.getFoodLevel() <= 5) {
				takeAction(player, "swlh: " + player.getFoodLevel());
			}
		}
	}

	private void takeAction(final Player player, final String debug) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + player.getName() + " 1d " + "1 day ban automated hack");
		getServer().broadcastMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "SevereAnti" + ChatColor.RED + "Hack" + ChatColor.DARK_RED + "] " + ChatColor.RED + player.getName() + ChatColor.YELLOW + " has been SNIPED by Rabrg for hacking. debug: " + debug);
	}
}
