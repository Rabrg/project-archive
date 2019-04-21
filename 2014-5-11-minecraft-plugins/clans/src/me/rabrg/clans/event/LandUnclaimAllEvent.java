package me.rabrg.clans.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;

public class LandUnclaimAllEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	private final Clan clan;
	private final CPlayer fplayer;

	public LandUnclaimAllEvent(final Clan f, final CPlayer p) {
		clan = f;
		fplayer = p;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Clan getClan() {
		return clan;
	}

	public String getClanId() {
		return clan.getId();
	}

	public String getClanTag() {
		return clan.getTag();
	}

	public CPlayer getCPlayer() {
		return fplayer;
	}

	public Player getPlayer() {
		return fplayer.getPlayer();
	}
}
