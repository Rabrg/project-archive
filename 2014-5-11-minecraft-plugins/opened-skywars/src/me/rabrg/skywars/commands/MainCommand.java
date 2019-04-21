package me.rabrg.skywars.commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rabrg.skywars.utilities.Messaging;

import com.google.common.collect.Maps;

public class MainCommand implements CommandExecutor {

	private final Map<String, CommandExecutor> subCommandMap = Maps.newHashMap();

	public MainCommand() {
		subCommandMap.put("reload", new ReloadCommand());
		subCommandMap.put("kit", new KitCommand());
		subCommandMap.put("setlobby", new SetLobbyCommand());
		subCommandMap.put("start", new StartCommand());
		subCommandMap.put("leave", new LeaveCommand());
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(new Messaging.MessageFormatter().format("error.player-only"));
			return true;
		}

		final Player player = (Player) sender;
		if (args.length == 0) {
			printHelp(player, label);
			return true;
		}

		final String subCommandName = args[0].toLowerCase();
		if (!subCommandMap.containsKey(subCommandName)) {
			printHelp(player, label);
			return true;
		}

		final CommandExecutor subCommand = subCommandMap.get(subCommandName);
		if (!hasPermission(player, subCommand)) {
			player.sendMessage(new Messaging.MessageFormatter().format("error.insufficient-permissions"));
			return true;
		}

		return subCommand.onCommand(sender, command, label, args);
	}

	private boolean hasPermission(final Player bukkitPlayer, final CommandExecutor cmd) {
		final CommandPermissions permissions = cmd.getClass().getAnnotation(CommandPermissions.class);
		if (permissions == null) {
			return true;
		}

		for (final String permission : permissions.value()) {
			if (bukkitPlayer.hasPermission(permission)) {
				return true;
			}
		}

		return false;
	}

	private void printHelp(final Player bukkitPlayer, final String label) {
		bukkitPlayer.sendMessage(new Messaging.MessageFormatter().withPrefix().format("cmd.available-commands"));

		for (final Map.Entry<String, CommandExecutor> commandEntry : subCommandMap.entrySet()) {
			if (hasPermission(bukkitPlayer, commandEntry.getValue())) {
				String description = "No description available.";

				final CommandDescription cmdDescription = commandEntry.getValue().getClass().getAnnotation(CommandDescription.class);
				if (cmdDescription != null) {
					description = cmdDescription.value();
				}

				bukkitPlayer.sendMessage("\2477/" + label + " " + commandEntry.getKey() + " \247f-\247e " + description);
			}
		}
	}
}