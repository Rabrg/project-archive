package me.rabrg.agni;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This plugin allows players to directly ignite entities with flint and steel.
 * @author Ryan Greene
 *
 */
public final class Plugin extends JavaPlugin implements Listener {

	/**
	 * The amount of durability removed from flint and steel after igniting an entity.
	 */
	private int durability;

	/**
	 * The amount of ticks the ignited entity is ignited for.
	 */
	private int fireTicks;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		durability = getConfig().getInt("durability", 3);
		fireTicks = getConfig().getInt("fireticks", 60);
		
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityInteractEntityEvent(final PlayerInteractEntityEvent event) {
		final Player player = event.getPlayer();
		final ItemStack item = player.getItemInHand();
		if (item.getType() == Material.FLINT_AND_STEEL && player.hasPermission("rabrg.agni.ignite")) {
			item.setDurability((short) (item.getDurability() + (short) durability));
			event.getRightClicked().setFireTicks(fireTicks);
		}
	}
}
