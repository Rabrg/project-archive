package me.rabrg.hunter.creature;

import me.rabrg.hunter.HunterScript;

/**
 * Represents a single creature that can be hunted.
 * @author Ryan Greene
 *
 * @param <S> The creature states which hunting the creature can have.
 */
public abstract class Creature<S extends Creature.CreatureState> {

	/**
	 * An instance of the core of the script.
	 */
	public final HunterScript script;

	/**
	 * Constructs a new creature instance
	 * @param script An instance of the core of the script.
	 */
	public Creature(final HunterScript script) {
		this.script = script;
	}

	/**
	 * Gets the current state of the process of hunting a creature.
	 * @return The current state of the process of hunting a creature.
	 */
	public abstract S getState();

	/**
	 * Handles the specified state in hunting the creature.
	 * @param state The current state of hunting the creature.
	 * @return The sleep time till the next time the creature gets a new state.
	 * @throws InterruptedException If an interrupted exception is thrown.
	 */
	public abstract int handleState(S state) throws InterruptedException;


	/**
	 * An interface which gets implemented by an enum inside an extending creature.
	 * @author Ryan Greene
	 *
	 */
	public static interface CreatureState {
	
	}
}
