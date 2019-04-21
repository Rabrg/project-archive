package me.rabrg.mercury.command;

import me.rabrg.mercury.Plugin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class EmailCommandExecutor implements CommandExecutor {

	private final Plugin plugin;

	public EmailCommandExecutor(final Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (sender.hasPermission("rabrg.mercury.email")) {
			if (args.length > 2) {
				try {
					final String recipient = args[0];
					final String message = StringUtils.join(args, ' ', 1, args.length);
					plugin.getMessageTransporter().send(recipient, null, message);
				} catch (final Exception e) {
					sender.sendMessage("An exception was thrown while attempting to execute the command.");
					e.printStackTrace();
				}
			}
		}
		return true;
	}

}
