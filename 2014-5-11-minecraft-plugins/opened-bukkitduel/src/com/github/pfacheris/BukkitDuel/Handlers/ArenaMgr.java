package com.github.pfacheris.BukkitDuel.Handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import com.github.pfacheris.BukkitDuel.BukkitDuel;
import com.github.pfacheris.BukkitDuel.Objects.Arena;

public class ArenaMgr {

	private final BukkitDuel plugin;
	private final List<Arena> arenas = new ArrayList<Arena>();

	public ArenaMgr(final BukkitDuel plugin) {
		this.plugin = plugin;
		populateArenas();
	}

	private void populateArenas() {
		Set<String> arenaNames = null;
		try {
			arenaNames = plugin.getConfig().getConfigurationSection("Arenas").getKeys(false);
		} catch (final Exception e) {
			BukkitDuel.log.info("[BukkitDuel] No arenas found, be sure to define one in game.");
		}

		if (arenaNames != null && arenaNames.size() > 0) {
			for (final String name : arenaNames) {
				final World arenaWorld = plugin.getServer().getWorld(plugin.getConfig().getString("Arenas." + name + ".world"));

				if (arenaWorld != null) {
					try {
						final int minX = plugin.getConfig().getInt("Arenas." + name + ".corner1.x");
						final int minY = plugin.getConfig().getInt("Arenas." + name + ".corner1.y");
						final int minZ = plugin.getConfig().getInt("Arenas." + name + ".corner1.z");

						final int maxX = plugin.getConfig().getInt("Arenas." + name + ".corner2.x");
						final int maxY = plugin.getConfig().getInt("Arenas." + name + ".corner2.y");
						final int maxZ = plugin.getConfig().getInt("Arenas." + name + ".corner2.z");

						double sX = plugin.getConfig().getDouble("Arenas." + name + ".spawn1.x");
						double sY = plugin.getConfig().getDouble("Arenas." + name + ".spawn1.y");
						double sZ = plugin.getConfig().getDouble("Arenas." + name + ".spawn1.z");
						final Location newSpawn1 = new Location(arenaWorld, sX, sY, sZ);

						sX = plugin.getConfig().getDouble("Arenas." + name + ".spawn2.x");
						sY = plugin.getConfig().getDouble("Arenas." + name + ".spawn2.y");
						sZ = plugin.getConfig().getDouble("Arenas." + name + ".spawn2.z");
						final Location newSpawn2 = new Location(arenaWorld, sX, sY, sZ);

						final Arena tempArena = new Arena(plugin, name, arenaWorld, minX, minY, minZ, maxX, maxY, maxZ);
						tempArena.setSpawn1(newSpawn1);
						tempArena.setSpawn2(newSpawn2);
						this.arenas.add(tempArena);
					} catch (final Exception e) {
						BukkitDuel.log.info("[BukkitDuel] Configuration could not be loaded for arena: " + name);
					}
				}
			}
		}
	}

	public List<Arena> getArenas() {
		return arenas;
	}

	public Arena getArenaByName(final String arenaName) {
		for (final Arena arena : arenas) {
			if (arena.getName().equalsIgnoreCase(arenaName)) {
				return arena;
			}
		}

		return null;
	}

	public List<Arena> getArenasByWorld(final String worldName) {
		final List<Arena> arenasByWorld = new ArrayList<Arena>();
		for (final Arena arena : arenas) {
			if (arena.getWorld().getName().equalsIgnoreCase(worldName)) {
				arenasByWorld.add(arena);
			}
		}

		return arenasByWorld;
	}

	public List<Arena> getArenasByUsed(final boolean inUse) {
		final List<Arena> arenasByUsed = new ArrayList<Arena>();
		for (final Arena arena : arenas) {
			if (arena.getInUse() == inUse) {
				arenasByUsed.add(arena);
			}
		}

		return arenasByUsed;
	}

	public void save(final String name, final World world, final int minX, final int minY, final int minZ, final int maxX, final int maxY, final int maxZ) {
		boolean nameAvailable = true;

		for (int i = 0; i < arenas.size() && nameAvailable; i++) {
			if (arenas.get(i).getName().equalsIgnoreCase(name)) {
				nameAvailable = false;
			}
		}

		if (nameAvailable) {

			final Arena newArena = new Arena(plugin, name, world, minX, minY, minZ, maxX, maxY, maxZ);
			arenas.add(newArena);

		}

	}

	public void remove(final Arena toDelete) {
		arenas.remove(toDelete);

		plugin.getConfig().set("Arenas." + toDelete.getName(), null);

		plugin.saveConfig();
	}

}
