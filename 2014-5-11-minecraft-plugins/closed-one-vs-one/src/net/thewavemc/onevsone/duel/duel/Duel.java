package net.thewavemc.onevsone.duel.duel;

import net.thewavemc.onevsone.Configuration;
import net.thewavemc.onevsone.OneVsOne;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Duel implements Runnable {
	private int taskID;
	private final OneVsOne plugin;
	private int count = 10;
	private final Player p1;
	private final Player p2;

	public Duel(final OneVsOne plugin, final String p1, final String p2) {
		this.plugin = plugin;
		this.p1 = Bukkit.getPlayerExact(p1);
		this.p2 = Bukkit.getPlayerExact(p2);
	}

	public void setTaskID(final int taskID) {
		this.taskID = taskID;
	}

	@Override
	public void run() {
		if (!this.p1.isOnline() || !this.p2.isOnline()) {
			cancel();
		}
		this.p1.teleport(Configuration.spotOne);
		this.p2.teleport(Configuration.spotTwo);
		if (this.count == 10) {
			this.p1.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Duel starting in " + this.count / 2 + " seconds!");
			this.p2.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Duel starting in " + this.count / 2 + " seconds!");
			this.p1.playSound(this.p1.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
			this.p2.playSound(this.p1.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
		} else if (this.count >= 1) {
			if (this.count % 2 == 0) {
				this.p1.sendMessage(this.plugin.TAG + ChatColor.GREEN + this.count / 2 + "...");
				this.p2.sendMessage(this.plugin.TAG + ChatColor.GREEN + this.count / 2 + "...");
				this.p1.playSound(this.p1.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				this.p2.playSound(this.p1.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
			}
		} else {
			this.p1.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Fight!");
			this.p2.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Fight!");
			this.p1.playSound(this.p1.getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
			this.p2.playSound(this.p1.getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
			cancel();
		}
		this.count -= 1;
	}

	public Player getPlayer1() {
		return this.p1;
	}

	public Player getPlayer2() {
		return this.p2;
	}

	public void cancel() {
		Bukkit.getScheduler().cancelTask(this.taskID);
	}
}
