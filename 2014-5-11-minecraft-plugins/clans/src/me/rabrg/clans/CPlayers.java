package me.rabrg.clans;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;

import me.rabrg.clans.zcore.persist.PlayerEntityCollection;

public class CPlayers extends PlayerEntityCollection<CPlayer> {
	public static CPlayers i = new CPlayers();

	P p = P.p;

	private CPlayers() {
		super(CPlayer.class, new CopyOnWriteArrayList<CPlayer>(), new ConcurrentSkipListMap<String, CPlayer>(String.CASE_INSENSITIVE_ORDER), new File(
				P.p.getDataFolder(), "players.json"), P.p.gson);

		this.setCreative(true);
	}

	@Override
	public Type getMapType() {
		return new TypeToken<Map<String, CPlayer>>() {
		}.getType();
	}

	public void clean() {
		for (final CPlayer fplayer : this.get()) {
			if (!Clans.i.exists(fplayer.getClanId())) {
				p.log("Reset clan data (invalid clan) for player " + fplayer.getName());
				fplayer.resetClanData(false);
			}
		}
	}
}
