package me.rabrg.teleport.runnable;

import me.rabrg.teleport.RabrgTeleportDelayPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.scheduler.BukkitRunnable;

public final class TeleportDelayBukkitRunnable extends BukkitRunnable {
	private final RabrgTeleportDelayPlugin plugin;
	private final Player player;
	private final Location fromLocation;
	private final Location toLocation;
	private int seconds;

	public TeleportDelayBukkitRunnable(final RabrgTeleportDelayPlugin plugin, final Player player, final Location fromLocation, final Location toLocation) {
		this.plugin = plugin;
		this.player = player;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;

		seconds = plugin.getConfig().getInt("teleport-delay");
	}

	@Override
	public void run() {
		if (seconds == plugin.getConfig().getInt("teleport-delay")) {
			player.sendMessage(ChatColor.AQUA + "You will teleport in " + ChatColor.RED + seconds + " second" + (seconds != 1 ? "s" : "") + ChatColor.AQUA + ".");
		} else if (seconds == 0) {
			player.teleport(toLocation, TeleportCause.PLUGIN);
			plugin.removeTeleportDelay(player);
		} else if (seconds <= 3) {
			player.sendMessage(ChatColor.AQUA + "You will teleport in " + ChatColor.RED + seconds + " second" + (seconds != 1 ? "s" : "") + ChatColor.AQUA + ".");
		}
		seconds -= 1;
	}

	public Location getFromLocation() {
		return fromLocation;
	}
}
