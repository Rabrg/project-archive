package me.rabrg.spawn;

import me.rabrg.spawn.command.SetSpawnCommandExecutor;
import me.rabrg.spawn.command.SpawnCommandExecutor;
import me.rabrg.spawn.listener.PlayerEventListener;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class RabrgSpawnPlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		saveDefaultConfig();
		getCommand("setspawn").setExecutor(new SetSpawnCommandExecutor(this));
		getCommand("spawn").setExecutor(new SpawnCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);
		
		final Location spawnLocation = getSpawnLocation();
		getSpawnLocation().getWorld().getSpawnLocation().setWorld(spawnLocation.getWorld());
		getSpawnLocation().getWorld().getSpawnLocation().setX(spawnLocation.getX());
		getSpawnLocation().getWorld().getSpawnLocation().setY(spawnLocation.getY());
		getSpawnLocation().getWorld().getSpawnLocation().setZ(spawnLocation.getZ());
		getSpawnLocation().getWorld().getSpawnLocation().setYaw(spawnLocation.getYaw());
		getSpawnLocation().getWorld().getSpawnLocation().setPitch(spawnLocation.getPitch());
	}

	public Location getSpawnLocation() {
		return new Location(getServer().getWorld(getConfig().getString("spawn-location.world")), getConfig().getDouble("spawn-location.x"), getConfig().getDouble("spawn-location.y"),
				getConfig().getDouble("spawn-location.z"), (float) getConfig().getDouble("spawn-location.yaw"), (float) getConfig().getDouble("spawn-location.pitch"));
	}
}
