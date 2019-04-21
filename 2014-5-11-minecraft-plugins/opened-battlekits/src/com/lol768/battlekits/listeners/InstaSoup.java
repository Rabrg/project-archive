package com.lol768.battlekits.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.lol768.battlekits.BattleKits;

public class InstaSoup implements Listener {

	public BattleKits plugin;

	public InstaSoup(final BattleKits p) {
		this.plugin = p;
	}

	@EventHandler
	public void onInteractEvent(final PlayerInteractEvent event) {
		final Player p = event.getPlayer();

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.getItemInHand().getType() == Material.MUSHROOM_SOUP) {
				if ((boolean) plugin.checkSetting("settings.instant-soup-drink", p, false)) {
					if (plugin.checkSetting("instant-soup-drink.replenish-type", p, "hunger").equals("hunger")) {
						final ItemStack bowl = new ItemStack(Material.BOWL, 1);

						if (p.getFoodLevel() == 20) {// If food is full, stop
														// the code.
							event.setCancelled(true);
							p.sendMessage(ChatColor.RED + "Your food is already full!");
							return;
						}

						if (p.getFoodLevel() + 6 <= 20) { // Only add some
															// hunger back on
							event.setCancelled(true);
							p.getInventory().setItemInHand(bowl);
							p.setFoodLevel(p.getFoodLevel() + 6);
						}

						if (p.getFoodLevel() + 6 > 20) { // Hunger close to max,
															// so refill it
							event.setCancelled(true);
							p.getInventory().setItemInHand(bowl);
							p.setFoodLevel(20);
						}
					}

					if (plugin.checkSetting("instant-soup-drink.replenish-type", p, "hunger").equals("health")) {
						final ItemStack bowl = new ItemStack(Material.BOWL, 1);

						if (p.getHealth() == p.getMaxHealth()) {// If health is
																// full, stop
																// the code.
							event.setCancelled(true);
							p.sendMessage(ChatColor.RED + "You are already full health!");
							return;
						}

						if (p.getHealth() + 6 <= 20) { // Only add some health
														// back on
							event.setCancelled(true);
							p.getInventory().setItemInHand(bowl);
							p.setHealth(p.getHealth() + 6);
						}

						if (p.getHealth() + 6 > 20) { // Health close to max, so
														// refill it
							event.setCancelled(true);
							p.getInventory().setItemInHand(bowl);
							p.setHealth(p.getMaxHealth());
						}
					}
				}
			}
		}
	}
}
