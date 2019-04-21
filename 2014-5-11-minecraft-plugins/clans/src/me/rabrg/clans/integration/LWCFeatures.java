package me.rabrg.clans.integration;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.plugin.Plugin;

import com.griefcraft.lwc.LWC;
import com.griefcraft.lwc.LWCPlugin;
import me.rabrg.clans.CLocation;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;

public class LWCFeatures {
	private static LWC lwc;

	public static void setup() {
		final Plugin test = Bukkit.getServer().getPluginManager().getPlugin("LWC");
		if (test == null || !test.isEnabled()) {
			return;
		}

		lwc = ((LWCPlugin) test).getLWC();
		P.p.log("Successfully hooked into LWC!" + (Conf.lwcIntegration ? "" : " Integration is currently disabled, though (\"lwcIntegration\")."));
	}

	public static boolean getEnabled() {
		return Conf.lwcIntegration && lwc != null;
	}

	public static void clearOtherChests(final CLocation flocation, final Clan clan) {
		final Location location = new Location(Bukkit.getWorld(flocation.getWorldName()), flocation.getX() * 16, 5, flocation.getZ() * 16);
		if (location.getWorld() == null) {
			return; // world not loaded or something? cancel out to prevent
					// error
		}
		final Chunk chunk = location.getChunk();
		final BlockState[] blocks = chunk.getTileEntities();
		final List<Block> chests = new LinkedList<Block>();

		for (final BlockState block : blocks) {
			if (block.getType() == Material.CHEST) {
				chests.add(block.getBlock());
			}
		}

		for (int x = 0; x < chests.size(); x++) {
			if (lwc.findProtection(chests.get(x)) != null) {
				if (!clan.getCPlayers().contains(CPlayers.i.get(lwc.findProtection(chests.get(x)).getOwner()))) {
					lwc.findProtection(chests.get(x)).remove();
				}
			}
		}
	}

	public static void clearAllChests(final CLocation flocation) {
		final Location location = new Location(Bukkit.getWorld(flocation.getWorldName()), flocation.getX() * 16, 5, flocation.getZ() * 16);
		if (location.getWorld() == null) {
			return; // world not loaded or something? cancel out to prevent
					// error
		}
		final Chunk chunk = location.getChunk();
		final BlockState[] blocks = chunk.getTileEntities();
		final List<Block> chests = new LinkedList<Block>();

		for (final BlockState block : blocks) {
			if (block.getType() == Material.CHEST) {
				chests.add(block.getBlock());
			}
		}

		for (int x = 0; x < chests.size(); x++) {
			if (lwc.findProtection(chests.get(x)) != null) {
				lwc.findProtection(chests.get(x)).remove();
			}
		}
	}
}
