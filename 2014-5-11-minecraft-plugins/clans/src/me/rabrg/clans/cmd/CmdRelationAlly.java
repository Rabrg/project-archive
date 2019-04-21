package me.rabrg.clans.cmd;

import me.rabrg.clans.struct.Relation;

public class CmdRelationAlly extends FRelationCommand {
	public CmdRelationAlly() {
		aliases.add("ally");
		targetRelation = Relation.ALLY;
	}
}
