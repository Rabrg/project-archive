package me.rabrg.ogre;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(author = "Rabrg", category = Category.COMBAT, name = "Rabrg Ogre", version = 0.1)
public final class RabrgOgre extends AbstractScript {

	private double distance;

	private static final Tile CANNON_TILE = new Tile(2494, 3098);
	private static final Tile SAFESPOT_TILE = new Tile(2496, 3096);

	private long nextFire = System.currentTimeMillis();

	private boolean pickup;

	@Override
	public void onStart() {
		if (getEquipment().getItemInSlot(EquipmentSlot.WEAPON.getSlot()) != null && getEquipment().getItemInSlot(EquipmentSlot.WEAPON.getSlot()).getName().contains("bow")) {
			distance = 6;
		} else if (getEquipment().getItemInSlot(EquipmentSlot.WEAPON.getSlot()) != null && getEquipment().getItemInSlot(EquipmentSlot.WEAPON.getSlot()).getName().contains("dart")) {
			distance = 3.5;
		} else {
			distance = 0;
		}
	}

	@Override
	public int onLoop() {
		if (getDialogues().canContinue()) {
			getDialogues().clickContinue();
		} else if (pickup && getGameObjects().closest("Dwarf multicannon") != null) {
			if (getGameObjects().closest("Dwarf multicannon").interact("Pick-up")) {
				sleep(3200, 3800);
				pickup = false;
			}
		} else if (System.currentTimeMillis() >= nextFire && getGameObjects().closest("Dwarf multicannon") != null) {
				if (getGameObjects().closest("Dwarf multicannon").interact()) {
					nextFire = System.currentTimeMillis() + Calculations.random(19500, 25000);
					sleep(2800, 3200);
				}
		} else if (getInventory().contains("Cannon base")) {
			if (!getLocalPlayer().getTile().equals(CANNON_TILE)) {
				if (getWalking().walkExact(CANNON_TILE))
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return getLocalPlayer().getTile().equals(CANNON_TILE);
						}
					}, Calculations.random(3200, 3600));
			} else {
				if (getInventory().get("Cannon base").interact())
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return getGameObjects().closest("Dwarf multicannon") != null;
						}
					}, Calculations.random(7500, 10000));
				if (getGameObjects().closest("Dwarf multicannon") != null) {
					getGameObjects().closest("Dwarf multicannon").interact();
					nextFire = System.currentTimeMillis() + Calculations.random(12500, 23000);
				}
			}
		} else if (!getLocalPlayer().getTile().equals(SAFESPOT_TILE)) {
			if (getWalking().walkExact(SAFESPOT_TILE))
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getLocalPlayer().getTile().equals(SAFESPOT_TILE);
					}
				}, Calculations.random(3200, 3600));
		} else if (getLocalPlayer().getInteractingCharacter() == null) {
			final NPC ogre = getNpcs().closest(new Filter<NPC>() {
				@Override
				public boolean match(final NPC npc) {
					return npc != null && npc.getName() != null && npc.getName().equals("Ogre") && npc.distance() <= distance && (npc.isInteracting(getLocalPlayer()) || !npc.isInCombat());
				}
			});
			if (ogre != null) {
				if (ogre.interact("Attack"))
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return getLocalPlayer().getInteractingCharacter() != null;
						}
					}, Calculations.random(3200, 3600));
			}
		}
		return Calculations.random(125, 250);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().contains("cannon is out of ammo")) {
			final GameObject cannon = getGameObjects().closest("Dwarf multicannon");
			if (cannon != null) {
				if (cannon.interact()) {
					sleep(3850, 4200);
				}
			}
		} else if (message.getMessage().contains("?")) {
			if (Calculations.random(0.0, 1) > .66) {
				getKeyboard().type("ignored", true, true);
			} else if (Calculations.random(0.0, 1) > .66) {
				getKeyboard().type("stfu", true, true);
			} else {
				getKeyboard().type("fag", true, true);
			}
		} else if (message.getMessage().contains("almost decayed")) {
			pickup = true;
		}
	}
}
