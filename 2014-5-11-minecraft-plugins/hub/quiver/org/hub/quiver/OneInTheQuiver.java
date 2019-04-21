package org.hub.quiver;

import java.io.IOException;

import net.minecraft.server.v1_7_R1.Block;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public final class OneInTheQuiver extends JavaPlugin {

	private  WorldGuardPlugin worldGuard;
	private WorldEditPlugin worldEdit;

	@Override
	public void onEnable() {
		try {
			worldGuard = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");
			worldEdit = worldGuard.getWorldEdit();
		} catch (CommandException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equals("quiver")) {
			final String commandName = args[0];
			switch (commandName) {
			case "set":
				if (sender instanceof Player) {
					final Player player = (Player) sender;
					final Selection selection = worldEdit.getSelection(player);
					final String selectionName = args[1];
					getConfig().set(selectionName + ".selection", selection);
					try {
						getConfig().save("quiver-config.yml");
					} catch (IOException e) {
						e.printStackTrace();
					}
					player.sendMessage("Set " + selectionName + " selection.");
				} else {
					sender.sendMessage("You must me a player to use this command.");
				}
				break;
			default:
				sender.sendMessage("Unknown quiver command name.");
			}
		} else {
			sender.sendMessage("Invalid quiver command label.");
		}
		return true;
	}
}
