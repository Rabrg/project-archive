package me.rabrg.clans.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Clans;

public class ClanDisbandEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled;
	private final String id;
	private final Player sender;

	public ClanDisbandEvent(final Player sender, final String clanId) {
		cancelled = false;
		this.sender = sender;
		this.id = clanId;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Clan getClan() {
		return Clans.i.get(id);
	}

	public CPlayer getCPlayer() {
		return CPlayers.i.get(sender);
	}

	public Player getPlayer() {
		return sender;
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
