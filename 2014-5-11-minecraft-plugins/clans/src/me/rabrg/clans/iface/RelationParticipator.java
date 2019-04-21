package me.rabrg.clans.iface;

import org.bukkit.ChatColor;

import me.rabrg.clans.struct.Relation;

public interface RelationParticipator {
	public String describeTo(RelationParticipator that);

	public String describeTo(RelationParticipator that, boolean ucfirst);

	public Relation getRelationTo(RelationParticipator that);

	public Relation getRelationTo(RelationParticipator that, boolean ignorePeaceful);

	public ChatColor getColorTo(RelationParticipator to);
}
