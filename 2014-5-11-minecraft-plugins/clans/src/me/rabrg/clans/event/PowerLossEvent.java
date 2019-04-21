package me.rabrg.clans.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;

public class PowerLossEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled;
	private final Clan clan;
	private final CPlayer fplayer;
	private String message;

	public PowerLossEvent(final Clan f, final CPlayer p) {
		cancelled = false;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
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
