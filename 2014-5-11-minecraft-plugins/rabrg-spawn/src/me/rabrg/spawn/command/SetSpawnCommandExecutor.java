package me.rabrg.spawn.command;

import me.rabrg.spawn.RabrgSpawnPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class SetSpawnCommandExecutor implements CommandExecutor {
	private final RabrgSpawnPlugin plugin;

	public SetSpawnCommandExecutor(final RabrgSpawnPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final String world = args[0];
		final double x = Double.parseDouble(args[1]);
		final double y = Double.parseDouble(args[2]);
		final double z = Double.parseDouble(args[3]);
		final float yaw = Float.parseFloat(args[4]);
		final float pitch = Float.parseFloat(args[5]);
		plugin.getConfig().set("spawn-location.world", world);
		plugin.getConfig().set("spawn-location.x", Double.valueOf(x));
		plugin.getConfig().set("spawn-location.y", Double.valueOf(y));
		plugin.getConfig().set("spawn-location.z", Double.valueOf(z));
		plugin.getConfig().set("spawn-location.yaw", Float.valueOf(yaw));
		plugin.getConfig().set("spawn-location.pitch", Float.valueOf(pitch));
		plugin.saveConfig();
		sender.sendMessage(ChatColor.AQUA + "You have set the server's spawn location to: " + ChatColor.RED + "[" + world + ", " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + "]");
		return true;
	}
}
