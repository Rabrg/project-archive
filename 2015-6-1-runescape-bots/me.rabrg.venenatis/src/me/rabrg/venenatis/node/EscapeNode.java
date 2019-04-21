package me.rabrg.venenatis.node;

import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.utilities.impl.Condition;

import me.rabrg.venenatis.Venenatis;

public final class EscapeNode extends Node {

	public EscapeNode(final Venenatis script) {
		super(script);
	}

	@Override
	public boolean validate() {
		return script.getCombat().isInWild() && script.getPlayers().all(script.ATTACKABLE_PLAYER_FILTER).size() > 0;
	}

	@Override
	public void execute() {
		if (System.currentTimeMillis() - script.lastCombat > 10000) {
			script.getWorldHopper().quickHop(script.getWorlds().getRandomWorld(new Filter<World>() {
				@Override
				public boolean match(final World world) {
					return world.isMembers() && !world.isPVP() && world.getID() != 353 && world.getID() != 361 && world.getID() != 366 && world.getID() != 373;
				}
			}).getID());
		} else if ((script.getTabs().isOpen(Tab.EQUIPMENT) || script.getTabs().open(Tab.EQUIPMENT)) && script.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()).interact("Edgeville")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !script.getCombat().isInWild();
				}
			}, random(3600, 4200));
		}
	}

	@Override
	public String toString() {
		return "Escape";
	}

}
