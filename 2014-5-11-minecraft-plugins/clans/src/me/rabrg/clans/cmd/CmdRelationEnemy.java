package me.rabrg.clans.cmd;

import me.rabrg.clans.struct.Relation;

public class CmdRelationEnemy extends FRelationCommand {
	public CmdRelationEnemy() {
		aliases.add("enemy");
		targetRelation = Relation.ENEMY;
	}
}
