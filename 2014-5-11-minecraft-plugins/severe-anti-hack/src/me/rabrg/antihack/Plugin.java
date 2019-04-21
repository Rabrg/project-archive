package me.rabrg.antihack;

import me.rabrg.antihack.listener.DoorAttackListener;
import me.rabrg.antihack.listener.SpawnTeleportListener;
import me.rabrg.antihack.listener.SprintListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

	private int banCount;

	@Override
	public void onEnable() {
		banCount = getConfig().getInt("bancount", 25);
		
		Bukkit.getPluginManager().registerEvents(new DoorAttackListener(this), this);
		Bukkit.getPluginManager().registerEvents(new SpawnTeleportListener(this), this);
		Bukkit.getPluginManager().registerEvents(new SprintListener(this), this);
	}

	@Override
	public void onDisable() {
		saveConfig();
	}

	public void takeAction(final Player player, final String reason, final String info) {
		if (!player.hasPermission("rabrg.antihack.bypass")) {
			getConfig().set("bancount", ++banCount);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + player.getName() + " " + getBanLength(reason) + " automated hack  reason: " + reason + " length: " + getBanLength(reason) + " info: " + info);
			getServer().broadcastMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "SevereAnti" + ChatColor.RED + "Hack" + ChatColor.DARK_RED + "] " + ChatColor.RED + player.getName() + ChatColor.YELLOW + " has been SNIPED by Rabrg. reason: " + reason + " length: " + getBanLength(reason) + " info: " + info);
			getServer().broadcastMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "SevereAnti" + ChatColor.RED + "Hack" + ChatColor.DARK_RED + "] " + ChatColor.YELLOW + "Has sniped: "  + ChatColor.RED +  banCount + ChatColor.YELLOW + " players.");
		}
	}

	private String getBanLength(final String reason) {
		switch(reason) {
		case "stp":
			return "3d";
		case "hunger":
		case "consume":
			return "1h";
		case "phase":
			return "3d";
		default:
			return "1h";
		}
	}
}
