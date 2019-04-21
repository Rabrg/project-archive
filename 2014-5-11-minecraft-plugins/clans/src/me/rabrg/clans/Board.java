package me.rabrg.clans;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;

import me.rabrg.clans.integration.LWCFeatures;
import me.rabrg.clans.struct.Relation;
import me.rabrg.clans.util.AsciiCompass;
import me.rabrg.clans.zcore.util.DiscUtil;

public class Board {
	private static transient File file = new File(P.p.getDataFolder(), "board.json");
	private static transient HashMap<CLocation, String> flocationIds = new HashMap<CLocation, String>();

	// ----------------------------------------------//
	// Get and Set
	// ----------------------------------------------//
	public static String getIdAt(final CLocation flocation) {
		if (!flocationIds.containsKey(flocation)) {
			return "0";
		}

		return flocationIds.get(flocation);
	}

	public static Clan getClanAt(final CLocation flocation) {
		return Clans.i.get(getIdAt(flocation));
	}

	public static void setIdAt(final String id, final CLocation flocation) {
		clearOwnershipAt(flocation);

		if (id == "0") {
			removeAt(flocation);
		}

		flocationIds.put(flocation, id);
	}

	public static void setClanAt(final Clan clan, final CLocation flocation) {
		setIdAt(clan.getId(), flocation);
	}

	public static void removeAt(final CLocation flocation) {
		clearOwnershipAt(flocation);
		flocationIds.remove(flocation);
	}

	// not to be confused with claims, ownership referring to further
	// member-specific ownership of a claim
	public static void clearOwnershipAt(final CLocation flocation) {
		final Clan clan = getClanAt(flocation);
		if (clan != null && clan.isNormal()) {
			clan.clearClaimOwnership(flocation);
		}
	}

	public static void unclaimAll(final String clanId) {
		final Clan clan = Clans.i.get(clanId);
		if (clan != null && clan.isNormal()) {
			clan.clearAllClaimOwnership();
		}

		final Iterator<Entry<CLocation, String>> iter = flocationIds.entrySet().iterator();
		while (iter.hasNext()) {
			final Entry<CLocation, String> entry = iter.next();
			if (entry.getValue().equals(clanId)) {
				if (Conf.onUnclaimResetLwcLocks && LWCFeatures.getEnabled()) {
					LWCFeatures.clearAllChests(entry.getKey());
				}
				iter.remove();
			}
		}
	}

	// Is this coord NOT completely surrounded by coords claimed by the same
	// clan?
	// Simpler: Is there any nearby coord with a clan other than the clan here?
	public static boolean isBorderLocation(final CLocation flocation) {
		final Clan clan = getClanAt(flocation);
		final CLocation a = flocation.getRelative(1, 0);
		final CLocation b = flocation.getRelative(-1, 0);
		final CLocation c = flocation.getRelative(0, 1);
		final CLocation d = flocation.getRelative(0, -1);
		return clan != getClanAt(a) || clan != getClanAt(b) || clan != getClanAt(c) || clan != getClanAt(d);
	}

	// Is this coord connected to any coord claimed by the specified clan?
	public static boolean isConnectedLocation(final CLocation flocation, final Clan clan) {
		final CLocation a = flocation.getRelative(1, 0);
		final CLocation b = flocation.getRelative(-1, 0);
		final CLocation c = flocation.getRelative(0, 1);
		final CLocation d = flocation.getRelative(0, -1);
		return clan == getClanAt(a) || clan == getClanAt(b) || clan == getClanAt(c) || clan == getClanAt(d);
	}

	// ----------------------------------------------//
	// Cleaner. Remove orphaned foreign keys
	// ----------------------------------------------//

	public static void clean() {
		final Iterator<Entry<CLocation, String>> iter = flocationIds.entrySet().iterator();
		while (iter.hasNext()) {
			final Entry<CLocation, String> entry = iter.next();
			if (!Clans.i.exists(entry.getValue())) {
				if (Conf.onUnclaimResetLwcLocks && LWCFeatures.getEnabled()) {
					LWCFeatures.clearAllChests(entry.getKey());
				}
				P.p.log("Board cleaner removed " + entry.getValue() + " from " + entry.getKey());
				iter.remove();
			}
		}
	}

	// ----------------------------------------------//
	// Coord count
	// ----------------------------------------------//

	public static int getClanCoordCount(final String clanId) {
		int ret = 0;
		for (final String thatClanId : flocationIds.values()) {
			if (thatClanId.equals(clanId)) {
				ret += 1;
			}
		}
		return ret;
	}

	public static int getClanCoordCount(final Clan clan) {
		return getClanCoordCount(clan.getId());
	}

	public static int getClanCoordCountInWorld(final Clan clan, final String worldName) {
		final String clanId = clan.getId();
		int ret = 0;
		final Iterator<Entry<CLocation, String>> iter = flocationIds.entrySet().iterator();
		while (iter.hasNext()) {
			final Entry<CLocation, String> entry = iter.next();
			if (entry.getValue().equals(clanId) && entry.getKey().getWorldName().equals(worldName)) {
				ret += 1;
			}
		}
		return ret;
	}

	// ----------------------------------------------//
	// Map generation
	// ----------------------------------------------//

	/**
	 * The map is relative to a coord and a clan north is in the direction of
	 * decreasing x east is in the direction of decreasing z
	 */
	public static ArrayList<String> getMap(final Clan clan, final CLocation flocation, final double inDegrees) {
		final ArrayList<String> ret = new ArrayList<String>();
		final Clan clanLoc = getClanAt(flocation);
		ret.add(P.p.txt.titleize("(" + flocation.getCoordString() + ") " + clanLoc.getTag(clan)));

		final int halfWidth = Conf.mapWidth / 2;
		final int halfHeight = Conf.mapHeight / 2;
		final CLocation topLeft = flocation.getRelative(-halfWidth, -halfHeight);
		final int width = halfWidth * 2 + 1;
		int height = halfHeight * 2 + 1;

		if (Conf.showMapClanKey) {
			height--;
		}

		final Map<String, Character> fList = new HashMap<String, Character>();
		int chrIdx = 0;

		// For each row
		for (int dz = 0; dz < height; dz++) {
			// Draw and add that row
			String row = "";
			for (int dx = 0; dx < width; dx++) {
				if (dx == halfWidth && dz == halfHeight) {
					row += ChatColor.AQUA + "+";
				} else {
					final CLocation flocationHere = topLeft.getRelative(dx, dz);
					final Clan clanHere = getClanAt(flocationHere);
					final Relation relation = clan.getRelationTo(clanHere);
					if (clanHere.isNone()) {
						row += ChatColor.GRAY + "-";
					} else if (clanHere.isSafeZone()) {
						row += Conf.colorPeaceful + "+";
					} else if (clanHere.isWarZone()) {
						row += ChatColor.DARK_RED + "+";
					} else if (clanHere == clan || clanHere == clanLoc || relation.isAtLeast(Relation.ALLY)
							|| (Conf.showNeutralClansOnMap && relation.equals(Relation.NEUTRAL))
							|| (Conf.showEnemyClansOnMap && relation.equals(Relation.ENEMY))) {
						if (!fList.containsKey(clanHere.getTag())) {
							fList.put(clanHere.getTag(), Conf.mapKeyChrs[chrIdx++]);
						}
						final char tag = fList.get(clanHere.getTag());
						row += clanHere.getColorTo(clan) + "" + tag;
					} else {
						row += ChatColor.GRAY + "-";
					}
				}
			}
			ret.add(row);
		}

		// Get the compass
		final ArrayList<String> asciiCompass = AsciiCompass.getAsciiCompass(inDegrees, ChatColor.RED, P.p.txt.parse("<a>"));

		// Add the compass
		ret.set(1, asciiCompass.get(0) + ret.get(1).substring(3 * 3));
		ret.set(2, asciiCompass.get(1) + ret.get(2).substring(3 * 3));
		ret.set(3, asciiCompass.get(2) + ret.get(3).substring(3 * 3));

		// Add the clan key
		if (Conf.showMapClanKey) {
			String fRow = "";
			for (final String key : fList.keySet()) {
				fRow += String.format("%s%s: %s ", ChatColor.GRAY, fList.get(key), key);
			}
			ret.add(fRow);
		}

		return ret;
	}

	// -------------------------------------------- //
	// Persistance
	// -------------------------------------------- //

	public static Map<String, Map<String, String>> dumpAsSaveFormat() {
		final Map<String, Map<String, String>> worldCoordIds = new HashMap<String, Map<String, String>>();

		String worldName, coords;
		String id;

		for (final Entry<CLocation, String> entry : flocationIds.entrySet()) {
			worldName = entry.getKey().getWorldName();
			coords = entry.getKey().getCoordString();
			id = entry.getValue();
			if (!worldCoordIds.containsKey(worldName)) {
				worldCoordIds.put(worldName, new TreeMap<String, String>());
			}

			worldCoordIds.get(worldName).put(coords, id);
		}

		return worldCoordIds;
	}

	public static void loadFromSaveFormat(final Map<String, Map<String, String>> worldCoordIds) {
		flocationIds.clear();

		String worldName;
		String[] coords;
		int x, z;
		String clanId;

		for (final Entry<String, Map<String, String>> entry : worldCoordIds.entrySet()) {
			worldName = entry.getKey();
			for (final Entry<String, String> entry2 : entry.getValue().entrySet()) {
				coords = entry2.getKey().trim().split("[,\\s]+");
				x = Integer.parseInt(coords[0]);
				z = Integer.parseInt(coords[1]);
				clanId = entry2.getValue();
				flocationIds.put(new CLocation(worldName, x, z), clanId);
			}
		}
	}

	public static boolean save() {
		// Clans.log("Saving board to disk");

		try {
			DiscUtil.write(file, P.p.gson.toJson(dumpAsSaveFormat()));
		} catch (final Exception e) {
			e.printStackTrace();
			P.p.log("Failed to save the board to disk.");
			return false;
		}

		return true;
	}

	public static boolean load() {
		P.p.log("Loading board from disk");

		if (!file.exists()) {
			P.p.log("No board to load from disk. Creating new file.");
			save();
			return true;
		}

		try {
			final Type type = new TypeToken<Map<String, Map<String, String>>>() {
			}.getType();
			final Map<String, Map<String, String>> worldCoordIds = P.p.gson.fromJson(DiscUtil.read(file), type);
			loadFromSaveFormat(worldCoordIds);
		} catch (final Exception e) {
			e.printStackTrace();
			P.p.log("Failed to load the board from disk.");
			return false;
		}

		return true;
	}
}
