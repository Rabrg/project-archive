package me.rabrg.skywars.controllers;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.utilities.LogUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.schematic.SchematicFormat;

public class SchematicController {

	private static SchematicController instance;
	private final Random random = new Random();
	private final Map<String, CuboidClipboard> schematicMap = Maps.newHashMap();
	private final Map<CuboidClipboard, Map<Integer, Vector>> spawnCache = Maps.newHashMap();
	private final Map<CuboidClipboard, List<Vector>> chestCache = Maps.newHashMap();

	public SchematicController() {
		final File dataDirectory = SkyWars.get().getDataFolder();
		final File schematicsDirectory = new File(dataDirectory, "schematics");

		if (!schematicsDirectory.exists() && !schematicsDirectory.mkdirs()) {
			return;
		}

		final File[] schematics = schematicsDirectory.listFiles();
		if (schematics == null) {
			return;
		}

		for (final File schematic : schematics) {
			if (!schematic.getName().endsWith(".schematic")) {
				continue;
			}

			if (!schematic.isFile()) {
				LogUtils.log(Level.INFO, getClass(), "Could not load schematic %s: Not a file", schematic.getName());
				continue;
			}

			final SchematicFormat schematicFormat = SchematicFormat.getFormat(schematic);
			if (schematicFormat == null) {
				LogUtils.log(Level.INFO, getClass(), "Could not load schematic %s: Unable to determine schematic format", schematic.getName());
				continue;
			}

			try {
				registerSchematic(schematic.getName().replace(".schematic", ""), schematicFormat.load(schematic));
			} catch (final Exception e) {
				LogUtils.log(Level.INFO, getClass(), "Could not load schematic %s: %s", schematic.getName(), e.getMessage());
			}
		}

		LogUtils.log(Level.INFO, getClass(), "Registered %d schematics ...", schematicMap.size());
	}

	public void registerSchematic(final String name, final CuboidClipboard schematic) {
		Bukkit.getScheduler().runTaskAsynchronously(SkyWars.get(), new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				Vector currentPoint;
				int currentBlock;
				int spawnId = 0;

				for (int y = 0; y < schematic.getSize().getBlockY(); ++y) {
					for (int x = 0; x < schematic.getSize().getBlockX(); ++x) {
						for (int z = 0; z < schematic.getSize().getBlockZ(); ++z) {
							currentPoint = new Vector(x, y, z);
							currentBlock = schematic.getPoint(currentPoint).getType();

							if (currentBlock == 0) {
								continue;
							}

							if (currentBlock == Material.SIGN_POST.getId() || currentBlock == Material.BEACON.getId()) {
								cacheSpawn(schematic, spawnId++, currentPoint);
								schematic.setBlock(currentPoint, new BaseBlock(0));

							} else if (currentBlock == Material.CHEST.getId()) {
								cacheChest(schematic, currentPoint);
							}

						}
					}
				}

				schematicMap.put(name, schematic);
			}
		});
	}

	public CuboidClipboard getRandom() {
		final List<CuboidClipboard> schematics = Lists.newArrayList(schematicMap.values());
		return schematics.get(random.nextInt(schematics.size()));
	}

	public String getName(final CuboidClipboard cuboidClipboard) {
		for (final Map.Entry<String, CuboidClipboard> entry : schematicMap.entrySet()) {
			if (entry.getValue().equals(cuboidClipboard)) {
				return entry.getKey();
			}
		}

		return null;
	}

	public void cacheSpawn(final CuboidClipboard schematic, final int position, final Vector location) {
		Map<Integer, Vector> spawnPlaces;

		if (spawnCache.containsKey(schematic)) {
			spawnPlaces = spawnCache.get(schematic);

		} else {
			spawnPlaces = Maps.newHashMap();
		}

		spawnPlaces.put(position, location);
		spawnCache.put(schematic, spawnPlaces);
	}

	public Map<Integer, Vector> getCachedSpawns(final CuboidClipboard schematic) {
		return spawnCache.get(schematic);
	}

	private void cacheChest(final CuboidClipboard schematic, final Vector location) {
		List<Vector> chestList;

		if (chestCache.containsKey(schematic)) {
			chestList = chestCache.get(schematic);
		} else {
			chestList = Lists.newArrayList();
		}

		chestList.add(location);
		chestCache.put(schematic, chestList);
	}

	public Collection<Vector> getCachedChests(final CuboidClipboard schematic) {
		return chestCache.get(schematic);
	}

	public int size() {
		return schematicMap.size();
	}

	public void remove(final String schematic) {
		schematicMap.remove(schematic);
	}

	public static SchematicController get() {
		if (instance == null) {
			instance = new SchematicController();
		}

		return instance;
	}
}
