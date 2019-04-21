package me.rabrg.leaderboard;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RabrgLeaderboard extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getLogger().info("Enabling RabrgLeaderboard...");
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("RabrgLeaderboard has been enabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equals("leaderboard")) {
			displayLeaderboardMessage(sender);
		}
		return true;
	}

	private void displayLeaderboardMessage(final CommandSender sender) {
		sender.sendMessage("-----RabrgLeaderboard-----");
		
	}
}
