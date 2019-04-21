package me.rabrg.border;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import com.trc202.CombatTag.CombatTag;
import com.trc202.CombatTagApi.CombatTagApi;

public final class Border extends JavaPlugin implements Listener {

	private CombatTagApi combatApi;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		
		if(getServer().getPluginManager().getPlugin("CombatTag") != null){
			combatApi = new CombatTagApi((CombatTag) getServer().getPluginManager().getPlugin("CombatTag")); 
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMoveEvent(final PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		if (pastBorder(player.getLocation())) {
			if (player.hasMetadata("border")) {
				final long dif = player.getMetadata("border").get(0).asLong() - System.currentTimeMillis();
				if (dif < 0) {
					if (combatApi.isInCombat(player.getName())) {
						player.setHealth(0);
					} else {
						player.teleport(new Location(getServer().getWorld("world"), 177, 76, 292));
					}
				} else {
					player.sendMessage("You have " + (dif / 1000) + " seconds to go back within the map border or you'll be teleported back to spawn or killed if you're in combat.");
				}
			} else {
				player.sendMessage("You have five seconds to go back within the map border or you'll be teleported back to spawn or killed if you're in combat.");
				player.setMetadata("border", new FixedMetadataValue(this, System.currentTimeMillis() + 6500));
			}
		} else {
			if (player.hasMetadata("border")) {
				player.removeMetadata("border", this);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuitEvent(final PlayerQuitEvent event) {
		if (event.getPlayer().hasMetadata("border")) {
			event.getPlayer().teleport(event.getPlayer().getLocation().getWorld().getSpawnLocation());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlaceEvent(final BlockPlaceEvent event) {
		if (pastBorder(event.getBlock().getLocation())) {
			event.getPlayer().sendMessage("You can't build past the border.");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreakEvent(final BlockBreakEvent event) {
		if (pastBorder(event.getBlock().getLocation())) {
			event.getPlayer().sendMessage("You can't build past the border.");
			event.setCancelled(true);
		}
	}

	private boolean pastBorder(final Location location) {
		switch(location.getWorld().getName()) {
		case "world":
			if(location.getX() > 10000 || location.getX() < -10000 || location.getZ() > 10000 || location.getZ() < -10000) {
				return true;
			}
			break;
		case "world_nether":
			if(location.getX() > 1250 || location.getX() < -1250 || location.getZ() > 1250 || location.getZ() < -1250) {
				return true;
			}
			break;
		case "world_the_end":
			if(location.getX() > 2000 || location.getX() < -2000 || location.getZ() > 2000 || location.getZ() < -2000) {
				return true;
			}
			break;
		}
		return false;
	}
}
