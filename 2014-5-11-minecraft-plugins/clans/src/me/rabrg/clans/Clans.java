package me.rabrg.clans;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;

import me.rabrg.clans.util.MiscUtil;
import me.rabrg.clans.zcore.persist.EntityCollection;
import me.rabrg.clans.zcore.util.TextUtil;

public class Clans extends EntityCollection<Clan> {
	public static Clans i = new Clans();

	P p = P.p;

	private Clans() {
		super(Clan.class, new CopyOnWriteArrayList<Clan>(), new ConcurrentHashMap<String, Clan>(), new File(P.p.getDataFolder(), "clans.json"), P.p.gson);
	}

	@Override
	public Type getMapType() {
		return new TypeToken<Map<String, Clan>>() {
		}.getType();
	}

	@Override
	public boolean loadFromDisc() {
		if (!super.loadFromDisc()) {
			return false;
		}

		// Make sure the default neutral clan exists
		if (!this.exists("0")) {
			final Clan clan = this.create("0");
			clan.setTag(ChatColor.DARK_GREEN + "Wilderness");
			clan.setDescription("");
		}

		// Make sure the safe zone clan exists
		if (!this.exists("-1")) {
			final Clan clan = this.create("-1");
			clan.setTag("SafeZone");
			clan.setDescription("Free from PVP and monsters");
		} else {
			// if SafeZone has old pre-1.6.0 name, rename it to remove
			// troublesome " "
			final Clan clan = this.getSafeZone();
			if (clan.getTag().contains(" ")) {
				clan.setTag("SafeZone");
			}
		}

		// Make sure the war zone clan exists
		if (!this.exists("-2")) {
			final Clan clan = this.create("-2");
			clan.setTag("WarZone");
			clan.setDescription("Not the safest place to be");
		} else {
			// if WarZone has old pre-1.6.0 name, rename it to remove
			// troublesome " "
			final Clan clan = this.getWarZone();
			if (clan.getTag().contains(" ")) {
				clan.setTag("WarZone");
			}
		}

		// populate all clan player lists
		for (final Clan clan : i.get()) {
			clan.refreshCPlayers();
		}

		return true;
	}

	// ----------------------------------------------//
	// GET
	// ----------------------------------------------//

	@Override
	public Clan get(final String id) {
		if (!this.exists(id)) {
			p.log(Level.WARNING, "Non existing clanId " + id + " requested! Issuing cleaning!");
			Board.clean();
			CPlayers.i.clean();
		}

		return super.get(id);
	}

	public Clan getNone() {
		return this.get("0");
	}

	public Clan getSafeZone() {
		return this.get("-1");
	}

	public Clan getWarZone() {
		return this.get("-2");
	}

	// ----------------------------------------------//
	// Clan tag
	// ----------------------------------------------//

	public static ArrayList<String> validateTag(final String str) {
		final ArrayList<String> errors = new ArrayList<String>();

		if (MiscUtil.getComparisonString(str).length() < Conf.clanTagLengthMin) {
			errors.add(P.p.txt.parse("<i>The clan tag can't be shorter than <h>%s<i> chars.", Conf.clanTagLengthMin));
		}

		if (str.length() > Conf.clanTagLengthMax) {
			errors.add(P.p.txt.parse("<i>The clan tag can't be longer than <h>%s<i> chars.", Conf.clanTagLengthMax));
		}

		for (final char c : str.toCharArray()) {
			if (!MiscUtil.substanceChars.contains(String.valueOf(c))) {
				errors.add(P.p.txt.parse("<i>Clan tag must be alphanumeric. \"<h>%s<i>\" is not allowed.", c));
			}
		}

		return errors;
	}

	public Clan getByTag(final String str) {
		final String compStr = MiscUtil.getComparisonString(str);
		for (final Clan clan : this.get()) {
			if (clan.getComparisonTag().equals(compStr)) {
				return clan;
			}
		}
		return null;
	}

	public Clan getBestTagMatch(final String searchFor) {
		final Map<String, Clan> tag2clan = new HashMap<String, Clan>();

		// TODO: Slow index building
		for (final Clan clan : this.get()) {
			tag2clan.put(ChatColor.stripColor(clan.getTag()), clan);
		}

		final String tag = TextUtil.getBestStartWithCI(tag2clan.keySet(), searchFor);
		if (tag == null) {
			return null;
		}
		return tag2clan.get(tag);
	}

	public boolean isTagTaken(final String str) {
		return this.getByTag(str) != null;
	}

}
