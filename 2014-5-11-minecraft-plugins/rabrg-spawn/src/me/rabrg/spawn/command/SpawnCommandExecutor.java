package me.rabrg.spawn.command;

import me.rabrg.spawn.RabrgSpawnPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public final class SpawnCommandExecutor implements CommandExecutor {
	private final RabrgSpawnPlugin plugin;

	public SpawnCommandExecutor(final RabrgSpawnPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (args.length != 0) {
			return false;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("I don't know how to move you!");
			return true;
		}
		((Player) sender).teleport(plugin.getSpawnLocation(), TeleportCause.COMMAND);
		return true;
	}
}
