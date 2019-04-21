package me.rabrg.rewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rewards extends JavaPlugin {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage("You can find the voting rewards at: http://goo.gl/BP92WJ");
		return true;
	}
}
