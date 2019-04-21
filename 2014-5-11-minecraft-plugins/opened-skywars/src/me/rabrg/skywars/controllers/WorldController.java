package me.rabrg.skywars.controllers;

import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import me.rabrg.skywars.config.PluginConfig;
import me.rabrg.skywars.game.Game;
import me.rabrg.skywars.utilities.LogUtils;
import me.rabrg.skywars.utilities.WEUtils;

import com.google.common.collect.Lists;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;

public class WorldController {

	private static final int PASTE_HEIGHT = 75;
	private static WorldController worldController;
	private World islandWorld;
	private final Queue<int[]> freeIslands = Lists.newLinkedList();
	private int nextId;

	public WorldController() {
		generateIslandCoordinates();
		islandWorld = createWorld();
	}

	private void generateIslandCoordinates() {
		for (int xxx = 0; xxx < PluginConfig.getIslandsPerWorld(); xxx++) {
			for (int zzz = 0; zzz < PluginConfig.getIslandsPerWorld(); zzz++) {
				final int[] coordinates = new int[] { xxx, zzz };

				if (!freeIslands.contains(coordinates)) {
					freeIslands.add(coordinates);
				}
			}
		}
	}

	public void unload(final Game game) {
		final int[] islandCoordinates = game.getIslandCoordinates();
		final int islandX = islandCoordinates[0];
		final int islandZ = islandCoordinates[1];
		final int islandSize = PluginConfig.getIslandSize();

		final int minX = islandX * islandSize >> 4;
			final int minZ = islandZ * islandSize >> 4;
			final int maxX = islandX * islandSize + islandSize >> 4;
			final int maxZ = islandZ * islandSize + islandSize >> 4;

			for (int xxx = minX; xxx < maxX; xxx++) {
				for (int zzz = minZ; zzz < maxZ; zzz++) {
					final Chunk chunk = game.getWorld().getChunkAt(xxx, zzz);

					if (chunk.isLoaded()) {
						for (final Entity entity : chunk.getEntities()) {
							if (!(entity instanceof Player)) {
								entity.remove();
							}
						}
						chunk.unload(false);
					}
				}
			}
	}

	public World create(final Game game, final CuboidClipboard schematic) {
		if (freeIslands.size() == 0) {
			LogUtils.log(Level.INFO, getClass(), "No more free islands left. Generating new world.");

			generateIslandCoordinates();
			islandWorld = createWorld();
		}

		final int[] islandCoordinates = freeIslands.poll();
		game.setIslandCoordinates(islandCoordinates);

		final int islandX = islandCoordinates[0];
		final int islandZ = islandCoordinates[1];
		final int islandSize = PluginConfig.getIslandSize();

		final int midX = islandX * islandSize + islandSize / 2;
		final int midZ = islandZ * islandSize + islandSize / 2;

		if (PluginConfig.buildSchematic()) {
			WEUtils.buildSchematic(game, new Location(islandWorld, midX, PASTE_HEIGHT, midZ), schematic);
		} else {
			WEUtils.pasteSchematic(new Location(islandWorld, midX, PASTE_HEIGHT, midZ), schematic);
		}

		final Map<Integer, Vector> spawns = SchematicController.get().getCachedSpawns(schematic);
		final Vector isleLocation = new Vector(midX, PASTE_HEIGHT, midZ);

		for (final Map.Entry<Integer, Vector> entry : spawns.entrySet()) {
			final Vector spawn = entry.getValue().add(isleLocation).add(schematic.getOffset());
			final Location location = new Location(islandWorld, spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());

			game.addSpawn(entry.getKey(), location);

			if (PluginConfig.buildSchematic() || PluginConfig.buildCages()) {
				createSpawnHousing(location);
			}
		}

		final Collection<Vector> chests = SchematicController.get().getCachedChests(schematic);

		if (chests != null) {
			for (final Vector location : chests) {
				final Vector spawn = location.add(isleLocation).add(schematic.getOffset());
				final Location chest = new Location(islandWorld, spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());

				game.addChest(chest);
			}
		}

		return islandWorld;
	}

	private void createSpawnHousing(final Location location) {
		final World world = location.getWorld();

		final int x = location.getBlockX();
		final int y = location.getBlockY();
		final int z = location.getBlockZ();

		world.getBlockAt(x, y - 1, z).setType(Material.GLASS);
		world.getBlockAt(x, y + 3, z).setType(Material.GLASS);

		world.getBlockAt(x + 1, y, z).setType(Material.GLASS);
		world.getBlockAt(x + 1, y + 1, z).setType(Material.GLASS);
		world.getBlockAt(x + 1, y + 2, z).setType(Material.GLASS);

		world.getBlockAt(x - 1, y, z).setType(Material.GLASS);
		world.getBlockAt(x - 1, y + 1, z).setType(Material.GLASS);
		world.getBlockAt(x - 1, y + 2, z).setType(Material.GLASS);

		world.getBlockAt(x, y, z + 1).setType(Material.GLASS);
		world.getBlockAt(x, y + 1, z + 1).setType(Material.GLASS);
		world.getBlockAt(x, y + 2, z + 1).setType(Material.GLASS);

		world.getBlockAt(x, y, z - 1).setType(Material.GLASS);
		world.getBlockAt(x, y + 1, z - 1).setType(Material.GLASS);
		world.getBlockAt(x, y + 2, z - 1).setType(Material.GLASS);
	}

	private World createWorld() {
		final WorldCreator worldCreator = new WorldCreator("island-" + getNextId());
		worldCreator.environment(World.Environment.NORMAL);
		worldCreator.generateStructures(false);
		worldCreator.generator(new ChunkGenerator() {
			@Override
			public byte[] generate(final World world, final Random random, final int x, final int z) {
				return new byte[32768];
			}

			@Override
			public Location getFixedSpawnLocation(final World world, final Random random) {
				if (!world.isChunkLoaded(0, 0)) {
					world.loadChunk(0, 0);
				}

				return new Location(world, 0.0D, 64.0D, 0.0D);
			}
		});

		final World world = worldCreator.createWorld();
		world.setDifficulty(Difficulty.NORMAL);
		world.setSpawnFlags(false, false);
		world.setPVP(true);
		world.setStorm(false);
		world.setThundering(false);
		world.setWeatherDuration(Integer.MAX_VALUE);
		world.setAutoSave(false);
		world.setKeepSpawnInMemory(false);
		world.setTicksPerAnimalSpawns(0);
		world.setTicksPerMonsterSpawns(0);

		world.setGameRuleValue("doMobSpawning", "false");
		world.setGameRuleValue("mobGriefing", "false");
		world.setGameRuleValue("doFireTick", "false");

		return world;
	}

	private int getNextId() {
		final int id = nextId++;

		if (nextId == Integer.MAX_VALUE) {
			nextId = 0;
		}

		return id;
	}

	public static WorldController get() {
		if (worldController == null) {
			worldController = new WorldController();
		}

		return worldController;
	}

}
