package me.rabrg.clans.cmd;

import me.rabrg.clans.struct.Relation;

public class CmdRelationNeutral extends FRelationCommand {
	public CmdRelationNeutral() {
		aliases.add("neutral");
		targetRelation = Relation.NEUTRAL;
	}
}
