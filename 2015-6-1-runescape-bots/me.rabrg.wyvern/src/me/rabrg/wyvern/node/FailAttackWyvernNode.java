package me.rabrg.wyvern.node;

import static me.rabrg.wyvern.WyvernScript.*;

import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.util.Filter;
import org.tbot.wrappers.NPC;

public final class FailAttackWyvernNode extends Node {

	@Override
	public boolean validate() {
		return WYVERN_AREA.contains(Players.getLocal()) && (Players.getLocal().getInteractingEntity() == null || !Players.getLocal().getInteractingEntity().isHealthBarVisible()) && Npcs.getNearest(new Filter<NPC>() {
			@Override	
            public boolean accept(final NPC npc) {
                return npc != null && npc.getName() != null && npc.getName().equals("Skeletal Wyvern") && (Players.getLocal().isHealthBarVisible() && npc.isInteractingWithLocalPlayer()) || (!npc.isHealthBarVisible() || npc.isInteractingWithLocalPlayer());
            }
        }) == null && (System.currentTimeMillis() - lastKill) > 10000;
	}

	@Override
	public int execute() {
		// TODO walk to tile
		return Random.nextInt(125, 275);
	}

	@Override
	public int priority() {
		return 6;
	}

	@Override
	public String getName() {
		return "Attacking wyvern";
	}

}
