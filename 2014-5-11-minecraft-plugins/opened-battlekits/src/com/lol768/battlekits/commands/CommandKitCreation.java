package com.lol768.battlekits.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.lol768.battlekits.BattleKits;

public class CommandKitCreation implements CommandExecutor {

	public BattleKits plugin;

	public CommandKitCreation(final BattleKits battleKits) {
		plugin = battleKits;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		sender.sendMessage("This command is not yet implemented.");
		return true;
	}
}
