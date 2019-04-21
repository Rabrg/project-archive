package me.happypikachu.BattleTags;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class BattleTagsCommandExecutor implements CommandExecutor {
	private BattleTags plugin;
	public BattleTagsCommandExecutor(BattleTags plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("bt") || cmd.getName().equalsIgnoreCase("battletags")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (!sender.hasPermission("battletags.reload")) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command.");
					} else {
						try {
                			plugin.reloadConfig();
                			sender.sendMessage(ChatColor.GREEN + "Successfully reloaded config.");
                		} catch (Exception ex) {
                			sender.sendMessage(ChatColor.RED + "An error occurred: Failed to reload config.");
                		}
					}
					return true;
				}
			}
			PluginDescriptionFile pdFile = plugin.getDescription();	
            sender.sendMessage(ChatColor.RED + "BattleTags v" + pdFile.getVersion() + ChatColor.GRAY + " " + pdFile.getAuthors().toString());
            sender.sendMessage(ChatColor.WHITE + pdFile.getDescription());
            return true;
		}
		return false;
	}
}