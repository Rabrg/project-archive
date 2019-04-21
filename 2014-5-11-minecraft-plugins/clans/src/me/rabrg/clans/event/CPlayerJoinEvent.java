package me.rabrg.clans.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;

public class CPlayerJoinEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	CPlayer fplayer;
	Clan clan;
	PlayerJoinReason reason;
	boolean cancelled = false;

	public enum PlayerJoinReason {
		CREATE, LEADER, COMMAND
	}

	public CPlayerJoinEvent(final CPlayer fp, final Clan f, final PlayerJoinReason r) {
		fplayer = fp;
		clan = f;
		reason = r;
	}

	public CPlayer getCPlayer() {
		return fplayer;
	}

	public Clan getClan() {
		return clan;
	}

	public PlayerJoinReason getReason() {
		return reason;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(final boolean c) {
		cancelled = c;
	}
}