package net.severepvp.lootold;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PickupTask extends BukkitRunnable {
	public SevereLoot plugin;
	public String playerName;
	public String victim;
	public Player player;

	public PickupTask(final SevereLoot plugin, final Player player, final String victim) {
		this.plugin = plugin;
		this.playerName = player.getName();
		this.victim = victim;
		this.player = player;
	}

	@Override
	public void run() {
		this.plugin.allowPickup(this.playerName);
		if (this.player != null) {
			this.player.sendMessage(ChatColor.translateAlternateColorCodes('&',
					this.plugin.getConfig().getString("general.canPickupMessage").replace("<victim>", this.victim)));
		}
	}
}
