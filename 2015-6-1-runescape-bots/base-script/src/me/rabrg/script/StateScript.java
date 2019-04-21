package me.rabrg.script;

import org.osbot.rs07.script.Script;

public abstract class StateScript<S extends State> extends Script {

	@Override
	public int onLoop() throws InterruptedException {
		return handleState(getState());
	}

	public abstract int handleState(final S state);

	public abstract S getState();
}
