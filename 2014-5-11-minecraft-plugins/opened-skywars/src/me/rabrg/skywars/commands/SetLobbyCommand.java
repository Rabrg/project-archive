package me.rabrg.skywars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rabrg.skywars.config.PluginConfig;
import me.rabrg.skywars.utilities.Messaging;

@CommandDescription("Set the lobby")
@CommandPermissions("skywars.command.setlobby")
public class SetLobbyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		PluginConfig.setLobbySpawn(((Player) sender).getLocation());
		sender.sendMessage(new Messaging.MessageFormatter().format("success.lobby-set"));
		return true;
	}
}
