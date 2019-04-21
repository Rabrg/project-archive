package me.rabrg.clans.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;

public class CPlayerLeaveEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private final PlayerLeaveReason reason;
	CPlayer CPlayer;
	Clan Clan;
	boolean cancelled = false;

	public enum PlayerLeaveReason {
		KICKED, DISBAND, RESET, JOINOTHER, LEAVE
	}

	public CPlayerLeaveEvent(final CPlayer p, final Clan c, final PlayerLeaveReason r) {
		CPlayer = p;
		Clan = c;
		reason = r;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public PlayerLeaveReason getReason() {
		return reason;
	}

	public CPlayer getCPlayer() {
		return CPlayer;
	}

	public Clan getClan() {
		return Clan;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(final boolean c) {
		if (reason == PlayerLeaveReason.DISBAND || reason == PlayerLeaveReason.RESET) {
			cancelled = false;
			return;
		}
		cancelled = c;
	}
}