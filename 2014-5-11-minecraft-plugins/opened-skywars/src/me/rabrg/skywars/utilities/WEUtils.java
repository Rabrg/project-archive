package me.rabrg.skywars.utilities;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.build.BlockBuilder;
import me.rabrg.skywars.build.BlockBuilderEntry;
import me.rabrg.skywars.config.PluginConfig;
import me.rabrg.skywars.game.Game;

import com.google.common.collect.Lists;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;

public class WEUtils {

	public static boolean pasteSchematic(final Location origin, final CuboidClipboard schematic) {
		final EditSession editSession = new EditSession(new BukkitWorld(origin.getWorld()), Integer.MAX_VALUE);
		editSession.setFastMode(true);

		try {
			schematic.paste(editSession, new Vector(origin.getBlockX(), origin.getBlockY(), origin.getBlockZ()), PluginConfig.ignoreAir());
		} catch (final MaxChangedBlocksException ignored) {
			return false;
		}

		return true;
	}

	public static void buildSchematic(final Game game, final Location origin, final CuboidClipboard schematic) {
		Bukkit.getScheduler().runTaskAsynchronously(SkyWars.get(), new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				final Vector pasteLocation = new Vector(origin.getBlockX(), origin.getBlockY(), origin.getBlockZ());

				Vector currentPoint;
				BaseBlock currentBlock;
				final List<BlockBuilderEntry> blockQueue = Lists.newLinkedList();
				final List<BlockBuilderEntry> delayedQueue = Lists.newLinkedList();

				for (int xxx = 0; xxx < schematic.getSize().getBlockX(); ++xxx) {
					for (int yyy = 0; yyy < schematic.getSize().getBlockY(); ++yyy) {
						for (int zzz = 0; zzz < schematic.getSize().getBlockZ(); ++zzz) {
							currentPoint = new Vector(xxx, yyy, zzz);
							currentBlock = schematic.getPoint(currentPoint);

							if (currentBlock.isAir()) {
								continue;
							}

							currentPoint = currentPoint.add(pasteLocation).add(schematic.getOffset());

							switch (currentBlock.getType()) {
							// Add blocks with (possible) tile entities on the
							// end, might decrease client-side lag.
							case 8: // Water
							case 9: // Stationary Water
							case 10: // Lava
							case 11: // Stationary Lava
							case 63: // Sign post
							case 68: // Wall sign
							case 54: // Chest
							case 23: // Dispenser
							case 61: // Furnace
							case 117: // Brewing stance
							case 154: // Hopper
							case 158: // Dropper
							case 138: // Beacon
							case 52: // Mob spawner
							case 25: // Note block
							case 84: // Jukebox
							case 116: // Enchantment Table
							case 119: // End portal
							case 120: // End portal frame
							case 114: // Mob head
							case 137: // Command block
								delayedQueue.add(new BlockBuilderEntry(currentPoint, currentBlock));
								break;

							default:
								blockQueue.add(new BlockBuilderEntry(currentPoint, currentBlock));
							}
						}
					}
				}

				Collections.sort(blockQueue, new Comparator<BlockBuilderEntry>() {
					@Override
					public int compare(final BlockBuilderEntry o1, final BlockBuilderEntry o2) {
						return Integer.compare(o1.getLocation().getBlockY(), o2.getLocation().getBlockY());
					}
				});

				final int blockCount = blockQueue.size() + delayedQueue.size();
				final int ticksRequired = blockCount / PluginConfig.blocksPerTick();
				final int time = (int) (ticksRequired * (PluginConfig.buildInterval() * 50L));
				game.setTimer((int) (time / 1000L));

				final EditSession editSession = new EditSession(new BukkitWorld(origin.getWorld()), Integer.MAX_VALUE);
				editSession.setFastMode(true);

				final BlockBuilder blockBuilder = new BlockBuilder(editSession, blockQueue, delayedQueue, PluginConfig.blocksPerTick(), new BlockBuilder.BuildFinishedHandler() {
					@Override
					public void onBuildFinish() {
						game.setBuilt(true);

						if (game.isFull()) {
							game.onGameStart();
						} else {
							game.setTimer(1);
						}
					}
				});
				blockBuilder.start(40L, PluginConfig.buildInterval());
			}
		});
	}
}
