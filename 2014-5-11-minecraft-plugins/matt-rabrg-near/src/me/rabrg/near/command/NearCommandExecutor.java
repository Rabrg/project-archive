package me.rabrg.near.command;

import java.util.UUID;

import me.rabrg.near.RabrgNearPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class NearCommandExecutor implements CommandExecutor {
	private final RabrgNearPlugin plugin;

	public NearCommandExecutor(final RabrgNearPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (sender instanceof Player) {
			String highestPermission = null;
			if (sender.hasPermission("near.support")) {
				highestPermission = "near.support";
			}
			if (sender.hasPermission("near.vip")) {
				highestPermission = "near.vip";
			}
			if (sender.hasPermission("near.hero")) {
				highestPermission = "near.hero";
			}
			if (sender.hasPermission("near.elite")) {
				highestPermission = "near.elite";
			}
			if (sender.hasPermission("near.platinum")) {
				highestPermission = "near.platinum";
			}
			if (sender.hasPermission("near.legendary")) {
				highestPermission = "near.legendary";
			}
			if (sender.hasPermission("near.titan")) {
				highestPermission = "near.titan";
			}
			if (sender.hasPermission("near.god")) {
				highestPermission = "near.god";
			}
			if (sender.hasPermission("near.mythical")) {
				highestPermission = "near.mythical";
			}
			if (highestPermission != null) {
				final UUID uuid = ((Player) sender).getUniqueId();
				final boolean cooldownBypass = sender.hasPermission("near.cooldownbypass");
				if (System.currentTimeMillis() >= plugin.getCooldown(uuid) || cooldownBypass) {
					final int range = plugin.getConfig().getInt(highestPermission + ".range");
					final Location location = ((Player) sender).getLocation();
					final StringBuffer nearPlayerNames = new StringBuffer();
					for (final Player player : plugin.getServer().getOnlinePlayers()) {
						if (!player.getWorld().getName().equals(((Player) sender).getWorld().getName())) {
							continue;
						}
						if (!player.getName().equals(sender.getName()) && player.getWorld().getName().equals(((Player) sender).getWorld().getName())) {
							final double distance = location.distance(player.getLocation());
							if (distance <= range) {
								nearPlayerNames.append("§e" + player.getName() + " §7(" + Math.round(distance) + ")§e, ");
							}
						}
					}
					if (nearPlayerNames.length() < 2) {
						sender.sendMessage("§bPlayers within §e" + range + " §bblocks from you: §enone");
					} else {
						sender.sendMessage("§bPlayers within §e" + range + " §bblocks from you: " + nearPlayerNames.substring(0, nearPlayerNames.length() - 2));
					}
					if (!cooldownBypass) {
						final long cooldown = plugin.getConfig().getLong(highestPermission + ".cooldown");
						plugin.setCooldown(uuid, System.currentTimeMillis() + cooldown);
					}
				} else {
					sender.sendMessage("You can't use /near for another " + ChatColor.RED + (plugin.getCooldown(uuid) - System.currentTimeMillis()) / 1000L + " seconds" + ChatColor.WHITE + "!");
				}
			} else {
				sender.sendMessage("You don't have permission to use this command.");
			}
		} else {
			sender.sendMessage("Only players can use the command /near");
		}
		return true;
	}
}
