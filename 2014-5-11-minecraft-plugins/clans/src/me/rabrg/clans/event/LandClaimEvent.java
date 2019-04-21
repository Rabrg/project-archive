package me.rabrg.clans.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.rabrg.clans.CLocation;
import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;

public class LandClaimEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled;
	private final CLocation location;
	private final Clan clan;
	private final CPlayer fplayer;

	public LandClaimEvent(final CLocation loc, final Clan f, final CPlayer p) {
		cancelled = false;
		location = loc;
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

	public CLocation getLocation() {
		return this.location;
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

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(final boolean c) {
		this.cancelled = c;
	}
}
