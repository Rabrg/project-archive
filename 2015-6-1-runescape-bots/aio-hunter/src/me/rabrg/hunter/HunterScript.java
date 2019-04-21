package me.rabrg.hunter;

import me.rabrg.hunter.creature.Creature;
import me.rabrg.hunter.creature.impl.BirdCreature;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

/**
 * An all-in-one script from training the hunter skill in RuneScape.
 * @author Ryan Greene
 *
 */
@ScriptManifest(name = "AIO Hunter", author = "Rabrg", version = 0.1, info = "", logo = "")
public final class HunterScript extends Script {

	/**
	 * The current creature being hunted.
	 */
	@SuppressWarnings("rawtypes")
	private Creature creature;

	@Override
	public void onStart() {
		creature = new BirdCreature(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int onLoop() throws InterruptedException {
		return creature.handleState(creature.getState());
	}
}
