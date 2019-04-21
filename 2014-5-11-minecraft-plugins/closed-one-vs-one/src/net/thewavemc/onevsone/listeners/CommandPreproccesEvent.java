package net.thewavemc.onevsone.listeners;

import net.thewavemc.onevsone.Configuration;
import net.thewavemc.onevsone.OneVsOne;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreproccesEvent implements Listener {
	OneVsOne plugin;

	public CommandPreproccesEvent(final OneVsOne plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerCommandPreprocessEvent(final PlayerCommandPreprocessEvent evt) {
		if (this.plugin.getDuelManager().isOneVsOne(evt.getPlayer())) {
			for (final String s : Configuration.disabledCommands) {
				if (evt.getMessage().startsWith("/" + s)) {
					evt.getPlayer().sendMessage(this.plugin.TAG + ChatColor.RED + "/" + s + " is not allowed in the One Vs One arena!");
					evt.setCancelled(true);
				}
			}
		}
	}
}
