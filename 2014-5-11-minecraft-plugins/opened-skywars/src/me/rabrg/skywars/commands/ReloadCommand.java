package me.rabrg.skywars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.controllers.ChestController;
import me.rabrg.skywars.controllers.KitController;
import me.rabrg.skywars.utilities.Messaging;

@CommandDescription("Reloads the chests, kits and the plugin.yml")
@CommandPermissions("skywars.command.reload")
public class ReloadCommand implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		ChestController.get().load();
		KitController.get().load();
		SkyWars.get().reloadConfig();
		new Messaging(SkyWars.get());

		sender.sendMessage(new Messaging.MessageFormatter().format("success.reload"));
		return true;
	}
}
