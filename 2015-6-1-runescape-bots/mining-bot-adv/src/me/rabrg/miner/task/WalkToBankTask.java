package me.rabrg.miner.task;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import me.rabrg.miner.MinerTaskScript;
import me.rabrg.script.task.Task;

public final class WalkToBankTask implements Task {

	private final MinerTaskScript script;

	private final Position[] mineToBankPath = new Position[] {new Position(3302,3276,0), new Position(3302,3271,0), new Position(3299,3259,0), new Position(3294,3244,0), new Position(3285,3233,0), new Position(3282,3217,0), new Position(3281,3201,0), new Position(3281,3187,0), new Position(3276,3173,0), new Position(3269,3167,0)};

	public WalkToBankTask(final MinerTaskScript script) {
		this.script = script;
	}

	@Override
	public boolean validate() {
		return script.inventory.isFull() && !script.bankArea.contains(script.myPlayer());
	}

	@Override
	public int execute() {
		script.localWalker.walkPath(mineToBankPath);
		return Script.random(5, 25);
	}

}
