package net.severepvp.lootold;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SevereLoot extends JavaPlugin implements Listener {

	public HashMap<UUID, String> items = new HashMap<UUID, String>();

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		getCommand("severeloot").setPermissionMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("general.noPermMessage")));
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void allowPickup(final String player) {
		for (final Map.Entry<UUID, String> keys : this.items.entrySet()) {
			if (keys.getValue().equals(player)) {
				this.items.remove(keys.getKey());
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent event) {
		final Player player = event.getEntity();
		if ((!event.getDrops().isEmpty()) && (player.getKiller() != null)) {
			final Player killer = player.getKiller();
			for (final ItemStack item : event.getDrops()) {
				final Item entity = player.getWorld().dropItemNaturally(player.getLocation(), item);
				entity.setPickupDelay(0);
				this.items.put(entity.getUniqueId(), killer.getName());
			}
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			event.getDrops().clear();
			killer.sendMessage(ChatColor.translateAlternateColorCodes('&',
					getConfig().getString("general.cantPickupMessage").replace("<victim>", player.getName())));
			new PickupTask(this, killer, player.getName()).runTaskLater(this, 20L * getConfig().getLong("settings.delayTime"));
		}
	}

	@EventHandler
	public void onPlayerPickupEvent(final PlayerPickupItemEvent event) {
		if (this.items.containsKey(event.getItem().getUniqueId())) {
			if (!this.items.get(event.getItem().getUniqueId()).equals(event.getPlayer().getName())) {
				event.setCancelled(true);
			} else {
				this.items.remove(event.getItem().getUniqueId());
			}
		}
	}

	@EventHandler
	public void onItemDespawn(final ItemDespawnEvent event) {
		if (this.items.containsKey(event.getEntity().getUniqueId())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event) {
		allowPickup(event.getPlayer().getName());
	}

	public void sendHelpMessage(final CommandSender sender) {
		sender.sendMessage("§6§lSEVERELOOT§f | §7/severeloot");
		sender.sendMessage("§6Oo-----------------------oOo-----------------------oO");
		sender.sendMessage("§2/severeloot help §f- §aGet command help");
		sender.sendMessage("§2/severeloot reload §f- §aReload the plugin");
		sender.sendMessage("§6Oo-----------------------oOo-----------------------oO");
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("severeloot")) {
			if (args.length == 0) {
				sendHelpMessage(sender);
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("severeloot.reload")) {
					reloadConfig();
					getCommand("severeloot").setPermissionMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("general.noPermMessage")));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("general.reloadMessage")));
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("general.noPermMessage")));
				}
			} else {
				sendHelpMessage(sender);
			}
		}
		return true;
	}
}
