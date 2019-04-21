package com.lol768.battlekits.listeners;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.lol768.battlekits.BattleKits;

public class SignHandler implements Listener {

	private final BattleKits plugin;

	public SignHandler(final BattleKits plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void signClick(final PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null
				&& (e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST)) {

			final Sign s = (Sign) e.getClickedBlock().getState();
			final String[] lines = s.getLines();
			if (lines.length > 1 && lines[0].equals(ChatColor.DARK_RED + "[" + ChatColor.stripColor(plugin.global.getConfig().getString("brand")) + "]")) {
				e.setCancelled(true);
				if (p.hasPermission("battlekits.sign.use")) {

					if (!plugin.kits.getConfig().contains("kits." + lines[1])) {
						if (lines[1].equals("soupFill")) {
							if (!p.hasPermission("battlekits.soupfill")) {
								plugin.PM.warn(p, "You don't have permission for this!");
								return;
							}

							boolean rez = true;
							if ((Double) plugin.checkSetting("signs.soupFillCost", p, null) != null && BattleKits.economy != null) {
								rez = plugin.buyNeutral((Double) plugin.checkSetting("signs.soupFillCost", p, null), p.getName());
							} else {
								plugin.getLogger().log(Level.INFO, "{0} {1}", new Object[] { BattleKits.economy, plugin.checkSetting("signs.soupFillCost", p, null) });
							}
							if (rez) {
								for (final ItemStack i : p.getInventory().getContents()) {
									if (i == null) {
										p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
									}
								}
							}
						} else {
							plugin.PM.warn(p, "That kit does not exist!");
						}
					} else {
						plugin.getLogger().log(Level.INFO, "Supplying kit - {0}", lines[1]);
						plugin.cbk.supplyKit(p, lines[1], (boolean) plugin.checkSetting("signs.ignore-permissions", p, false),
								(boolean) plugin.checkSetting("signs.ignore-costs", p, false), (boolean) plugin.checkSetting("signs.ignore-lives-restriction", p, false),
								(boolean) plugin.checkSetting("signs.ignore-world-restriction", p, false));
					}
				} else {
					plugin.PM.warn(p, "You don't have permission to use kit signs");
				}
			}
		}
	}

	@EventHandler
	public void signEdit(final SignChangeEvent e) {
		final String[] lines = e.getLines();
		final Player p = e.getPlayer();

		if (lines.length > 1 && lines[0].equalsIgnoreCase("[" + ChatColor.stripColor(plugin.global.getConfig().getString("brand")) + "]")) {

			if (p.hasPermission("battlekits.sign.create")) {

				if (plugin.kits.getConfig().contains("kits." + lines[1]) || lines[1].equals("soupFill")) {
					e.setLine(0, ChatColor.DARK_RED + "[" + ChatColor.stripColor(plugin.global.getConfig().getString("brand")) + "]");
					plugin.PM.notify(p, "Kit sign created successfully!");

				} else {
					e.getBlock().breakNaturally();
					plugin.PM.warn(p, "That kit does not exist!");
				}
			} else {
				plugin.PM.warn(p, "You don't have permission to create kit signs");
				e.getBlock().breakNaturally();

			}
		}
	}
}
