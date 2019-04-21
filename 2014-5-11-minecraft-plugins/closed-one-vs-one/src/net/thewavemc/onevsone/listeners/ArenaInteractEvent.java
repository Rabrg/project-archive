package net.thewavemc.onevsone.listeners;

import net.thewavemc.onevsone.Configuration;
import net.thewavemc.onevsone.OneVsOne;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ArenaInteractEvent implements Listener {
	OneVsOne plugin;

	public ArenaInteractEvent(final OneVsOne plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteractEntityEvent(final PlayerInteractEntityEvent evt) {
		final Player player = evt.getPlayer();
		if (!this.plugin.getDuelManager().isInDuel(player) && player.getItemInHand().getType().equals(Configuration.inviteItem.getType()) && player.getItemInHand().hasItemMeta()
				&& player.getItemInHand().getItemMeta().getDisplayName().equals(Configuration.inviteItem.getItemMeta().getDisplayName())) {
			if (this.plugin.getDuelManager().isOneVsOne(player)) {
				if (evt.getRightClicked() instanceof Player) {
					final Player target = (Player) evt.getRightClicked();
					if (this.plugin.getDuelManager().isOneVsOne(target)) {
						if (!this.plugin.getDuelManager().isInDuel(target)) {
							this.plugin.getDuelManager().inviteDuel(player, target);
						} else {
							player.sendMessage(this.plugin.TAG + ChatColor.RED + target.getName() + " is already dueling!");
						}
					} else {
						player.sendMessage(this.plugin.TAG + ChatColor.RED + target.getName() + " is not playing OneVOne!");
					}
				}
			} else {
				player.sendMessage(this.plugin.TAG + ChatColor.RED + "You are not in the One Vs One arena, \"/ovo enter\" to enter One Vs One!");
			}
		}
	}
}
