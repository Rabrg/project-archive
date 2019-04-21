package me.rabrg.clans.integration;

import static com.sk89q.worldguard.bukkit.BukkitUtil.toVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.rabrg.clans.P;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/*
 *  Worldguard Region Checking
 *  Author: Spathizilla
 */

public class Worldguard {
	private static WorldGuardPlugin wg;
	private static boolean enabled = false;

	public static void init(final Plugin plugin) {
		final Plugin wgplug = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
		if (wgplug == null || !(wgplug instanceof WorldGuardPlugin)) {
			enabled = false;
			wg = null;
			P.p.log("Could not hook to WorldGuard. WorldGuard checks are disabled.");
		} else {
			wg = (WorldGuardPlugin) wgplug;
			enabled = true;
			P.p.log("Successfully hooked to WorldGuard.");
		}
	}

	public static boolean isEnabled() {
		return enabled;
	}

	// PVP Flag check
	// Returns:
	// True: PVP is allowed
	// False: PVP is disallowed
	public static boolean isPVP(final Player player) {
		if (!enabled) {
			// No WG hooks so we'll always bypass this check.
			return true;
		}

		final Location loc = player.getLocation();
		final World world = loc.getWorld();
		final Vector pt = toVector(loc);

		final RegionManager regionManager = wg.getRegionManager(world);
		final ApplicableRegionSet set = regionManager.getApplicableRegions(pt);
		return set.allows(DefaultFlag.PVP);
	}

	// Check if player can build at location by worldguards rules.
	// Returns:
	// True: Player can build in the region.
	// False: Player can not build in the region.
	public static boolean playerCanBuild(final Player player, final Location loc) {
		if (!enabled) {
			// No WG hooks so we'll always bypass this check.
			return false;
		}

		final World world = loc.getWorld();
		final Vector pt = toVector(loc);

		if (wg.getRegionManager(world).getApplicableRegions(pt).size() > 0) {
			return wg.canBuild(player, loc);
		}
		return false;
	}

	// Check for Regions in chunk the chunk
	// Returns:
	// True: Regions found within chunk
	// False: No regions found within chunk
	public static boolean checkForRegionsInChunk(final Location loc) {
		if (!enabled) {
			// No WG hooks so we'll always bypass this check.
			return false;
		}

		final World world = loc.getWorld();
		final Chunk chunk = world.getChunkAt(loc);
		final int minChunkX = chunk.getX() << 4;
		final int minChunkZ = chunk.getZ() << 4;
		final int maxChunkX = minChunkX + 15;
		final int maxChunkZ = minChunkZ + 15;

		final int worldHeight = world.getMaxHeight(); // Allow for heights other
														// than default

		final BlockVector minChunk = new BlockVector(minChunkX, 0, minChunkZ);
		final BlockVector maxChunk = new BlockVector(maxChunkX, worldHeight, maxChunkZ);

		final RegionManager regionManager = wg.getRegionManager(world);
		ProtectedCuboidRegion region = new ProtectedCuboidRegion("wgclanoverlapcheck", minChunk, maxChunk);
		final Map<String, ProtectedRegion> allregions = regionManager.getRegions();
		List<ProtectedRegion> allregionslist = new ArrayList<ProtectedRegion>(allregions.values());
		List<ProtectedRegion> overlaps;
		boolean foundregions = false;

		try {
			overlaps = region.getIntersectingRegions(allregionslist);
			if (overlaps == null || overlaps.isEmpty()) {
				foundregions = false;
			} else {
				foundregions = true;
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		region = null;
		allregionslist = null;
		overlaps = null;

		return foundregions;
	}
}