package me.rabrg.clans.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;

public class ClanRenameEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled;
	private final CPlayer fplayer;
	private final Clan clan;
	private final String tag;

	public ClanRenameEvent(final CPlayer sender, final String newTag) {
		fplayer = sender;
		clan = sender.getClan();
		tag = newTag;
		this.cancelled = false;
	}

	public Clan getClan() {
		return (clan);
	}

	public CPlayer getCPlayer() {
		return (fplayer);
	}

	public Player getPlayer() {
		return (fplayer.getPlayer());
	}

	public String getOldClanTag() {
		return (clan.getTag());
	}

	public String getClanTag() {
		return (tag);
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
