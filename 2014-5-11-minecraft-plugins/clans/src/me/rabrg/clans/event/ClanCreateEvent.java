package me.rabrg.clans.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clans;

public class ClanCreateEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	private final String clanTag;
	private final Player sender;
	private boolean cancelled;

	public ClanCreateEvent(final Player sender, final String tag) {
		this.clanTag = tag;
		this.sender = sender;
		this.cancelled = false;
	}

	public CPlayer getCPlayer() {
		return CPlayers.i.get(sender);
	}

	public String getClanId() {
		return Clans.i.getNextId();
	}

	public String getClanTag() {
		return clanTag;
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
		this.cancelled = c;
	}
}