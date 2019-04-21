package me.rabrg.factionboat;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

public final class Plugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onVehicleEnterEvent(final VehicleEnterEvent event) {
		if (event.getVehicle() instanceof Boat && event.getEntered() instanceof Player) {
			final Player player = (Player) event.getEntered();
			final Faction faction = Board.getFactionAt(FPlayers.i.get((Player) event.getEntered()).getLastStoodAt());
			System.out.println(faction.getTag());
			if (!faction.getTag().contains("Wilderness") && !faction.getOnlinePlayers().contains(player)) {
				player.sendMessage("You can't enter boats in claimed territories which aren't yours.");
				event.setCancelled(true);
			}
		}
	}
	
}
