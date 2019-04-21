package me.rabrg.clans.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.rabrg.clans.Clan;
import me.rabrg.clans.struct.Relation;

public class ClanRelationEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	private final Clan fsender;
	private final Clan ftarget;
	private final Relation foldrel;
	private final Relation frel;

	public ClanRelationEvent(final Clan sender, final Clan target, final Relation oldrel, final Relation rel) {
		fsender = sender;
		ftarget = target;
		foldrel = oldrel;
		frel = rel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Relation getOldRelation() {
		return foldrel;
	}

	public Relation getRelation() {
		return frel;
	}

	public Clan getClan() {
		return fsender;
	}

	public Clan getTargetClan() {
		return ftarget;
	}
}
