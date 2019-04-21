package me.rabrg.antihack.listener;

import me.rabrg.antihack.Plugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public final class DoorAttackListener implements Listener {

	@SuppressWarnings("unused")
	private final Plugin plugin;

	public DoorAttackListener(final Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent event){
		if (event.getDamager() instanceof Player) {
			final Player player = (Player) event.getDamager();
			@SuppressWarnings("deprecation")
			final Material type = player.getTargetBlock(null, 3).getType();
			if (type == Material.WOOD_DOOR || type == Material.WOODEN_DOOR || type == Material.IRON_DOOR || type == Material.IRON_DOOR_BLOCK) {
				player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "SevereAnti" + ChatColor.RED + "Hack" + ChatColor.DARK_RED + "] " + ChatColor.YELLOW + "Careful, " + ChatColor.RED + player.getName() + ChatColor.YELLOW + ", don't attack entities through doors.");
				event.setCancelled(true);
			}
		}
	}
}
