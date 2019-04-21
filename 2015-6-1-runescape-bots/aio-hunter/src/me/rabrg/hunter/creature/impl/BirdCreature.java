package me.rabrg.hunter.creature.impl;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.Area;

import me.rabrg.hunter.HunterScript;
import me.rabrg.hunter.creature.Creature;

/**
 * A creature representing all birds in the RuneScape world.
 * @author Ryan Greene
 *
 */
public final class BirdCreature extends Creature<BirdCreature.BirdCreatureState> {

	private static final String BIRD_SNARE_NAME = "Bird snare";
	private static final String BIRD_SNARE_ITEM_LAY_ACTION_NAME = "Lay";
	private static final String BIRD_SNARE_OBJECT_LOOT_ACTION_NAME = "Check";
	private static final String BIRD_SNARE_OBJECT_FIX_ACTION_NAME = "Dismantle";
	private static final String BIRD_SNARE_OBJECT_INVESTIGATE_ACTION_NAME = "Investigate";

	private static final String BONES_ITEM_NAME = "Bones";
	private static final String BONES_ITEM_BURY_ACTION_NAME = "Bury";
	private static final String RAW_BIRD_MEAT_NAME_ITEM_NAME = "Raw bird meat";

	private static final int MAXIMUM_AWAY_DISTANCE = 5;

	private final Position originalPosition;
	private final Area huntingArea;

	private boolean hasBirdSnareDown;
	private RS2Object currentBirdSnare;
	private GroundItem fallenBirdSnare;

	public BirdCreature(final HunterScript script) {
		super(script);
		originalPosition = script.myPlayer().getPosition();
		huntingArea = new Area(originalPosition.getX(), originalPosition.getY(), originalPosition.getX() - MAXIMUM_AWAY_DISTANCE, originalPosition.getY());
	}

	@Override
	public BirdCreatureState getState() {
		if ((fallenBirdSnare = script.groundItems.closest(huntingArea, BIRD_SNARE_NAME)) != null) {
			script.log("pick up bird snare");
			return BirdCreatureState.PICK_UP_BIRD_SNARE;
		} else if (!huntingArea.contains(script.myPlayer()) && !hasBirdSnareDown) {
			script.log("walk back");
			return BirdCreatureState.WALK_BACK;
		} else if (!hasBirdSnareDown) {
			script.log("place bird snare");
			return BirdCreatureState.PLACE_BIRD_SNARE;
		} else if ((currentBirdSnare = script.objects.closest(huntingArea, BIRD_SNARE_NAME)) != null && currentBirdSnare.hasAction(BIRD_SNARE_OBJECT_LOOT_ACTION_NAME)) {
			script.log("loot bird snare");
			return BirdCreatureState.LOOT_BIRD_SNARE;
		} else if (currentBirdSnare != null && currentBirdSnare.getActions()[0] != null && currentBirdSnare.hasAction(BIRD_SNARE_OBJECT_FIX_ACTION_NAME) && !currentBirdSnare.hasAction(BIRD_SNARE_OBJECT_INVESTIGATE_ACTION_NAME)) {
			script.log("fix bird snare");
			return BirdCreatureState.FIX_BIRD_SNARE;
		} else if (script.inventory.contains(BONES_ITEM_NAME) || script.inventory.contains(RAW_BIRD_MEAT_NAME_ITEM_NAME)) {
			script.log("drop loot");
			return BirdCreatureState.DROP_LOOT;
		} else {
			script.log("wait");
			return BirdCreatureState.WAIT;
		}
	}

	@Override
	public int handleState(final BirdCreatureState state) throws InterruptedException {
		switch(state) {
		case WALK_BACK:
			script.localWalker.walk(originalPosition);
			script.localWalker.waitUntilIdle();
			break;
		case PLACE_BIRD_SNARE:
			script.inventory.interact(BIRD_SNARE_ITEM_LAY_ACTION_NAME, BIRD_SNARE_NAME);
			hasBirdSnareDown = true;
			return Script.random(3250, 3750);
		case LOOT_BIRD_SNARE:
			hasBirdSnareDown = false;
			currentBirdSnare.interact(BIRD_SNARE_OBJECT_LOOT_ACTION_NAME);
			return Script.random(2250, 2750);
		case FIX_BIRD_SNARE:
			hasBirdSnareDown = false;
			currentBirdSnare.interact(BIRD_SNARE_OBJECT_FIX_ACTION_NAME);
			return Script.random(2250, 2750);
		case DROP_LOOT:
			script.inventory.dropAll(RAW_BIRD_MEAT_NAME_ITEM_NAME);
			script.inventory.interact(BONES_ITEM_BURY_ACTION_NAME, BONES_ITEM_NAME);
			break;
		case PICK_UP_BIRD_SNARE:
			fallenBirdSnare.interact("Take");
			return Script.random(3250, 3750);
		case WAIT:
			break;
		}
		return Script.random(225, 475);
	}

	public static enum BirdCreatureState implements Creature.CreatureState {
		WALK_BACK,
		PLACE_BIRD_SNARE,
		LOOT_BIRD_SNARE,
		FIX_BIRD_SNARE,
		DROP_LOOT,
		PICK_UP_BIRD_SNARE,
		WAIT
	}

}
